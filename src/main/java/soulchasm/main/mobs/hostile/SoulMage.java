package soulchasm.main.mobs.hostile;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerChaserWandererAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.lootTable.LootTable;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.main.projectiles.SoulDiscProjectile;

import java.awt.*;
import java.util.List;

public class SoulMage extends HostileMob {
    public static LootTable lootTable = new LootTable();
    public static HumanTexture texture;

    public SoulMage() {
        super(450);
        this.attackCooldown = 1000;
        this.attackAnimTime = 200;
        this.setSpeed(55.0F);
        this.setFriction(3.0F);
        this.setArmor(20);
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-14, -12, 28, 24);
        this.selectBox = new Rectangle(-14, -41, 28, 48);
    }

    public void init() {
        super.init();
        PlayerChaserWandererAI<SoulMage> playerChaserAI = new PlayerChaserWandererAI<SoulMage>(null, 540, 320, 40000, true, false) {
            public boolean attackTarget(SoulMage mob, Mob target) {
                if (mob.canAttack() && !mob.isAccelerating() && !mob.hasCurrentMovement()) {
                    mob.attack(target.getX(), target.getY(), false);
                    SoulDiscProjectile projectile =  new SoulDiscProjectile(this.mob().getLevel(), mob.x, mob.y, target.x, target.y, 160, 500, new GameDamage(60.0F), mob);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    return true;
                } else {
                    return false;
                }
            }
        };
        this.ai = new BehaviourTreeAI<>(this, playerChaserAI);
    }

    public boolean isLavaImmune() {
        return true;
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for(int i = 0; i < 4; ++i) {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture.body, GameRandom.globalRandom.nextInt(5), 8, 32, this.x, this.y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        Point sprite = this.getAnimSprite(x, y, this.getDir());
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, texture)).sprite(sprite).dir(this.getDir()).light(light);
        float animProgress = this.getAttackAnimProgress();
        if (this.isAttacking) {
            ItemAttackDrawOptions attackOptions = ItemAttackDrawOptions.start(this.getDir()).itemSprite(texture.body, 0, 9, 32).itemRotatePoint(3, 3).itemEnd().armSprite(texture.body, 0, 8, 32).swingRotation(animProgress).light(light);
            humanDrawOptions.attackAnim(attackOptions, animProgress);
        }

        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public int getRockSpeed() {
        return 20;
    }
}
