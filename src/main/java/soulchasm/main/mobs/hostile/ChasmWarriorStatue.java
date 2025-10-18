package soulchasm.main.mobs.hostile;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.EmptyAINode;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionPlayerChaserAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.main.misc.levelevents.MeleeGhostSpawnEvent;
import soulchasm.main.mobs.boss.ChasmDragon;
import soulchasm.main.projectiles.SoulHomingProjectile;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class ChasmWarriorStatue extends HostileMob {
    public static LootTable lootTable = new LootTable();
    public static GameTexture texture;

    public ChasmWarriorStatue() {
        super(1500);
        this.setSpeed(35.0F);
        this.setFriction(6.0F);
        this.setArmor(100);
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-14, -12, 28, 24);
        this.selectBox = new Rectangle(-14, -55, 28, 48);
        this.isHostile = false;
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new EmptyAINode<ChasmWarriorStatue>() {});
        this.isHostile = false;
    }

    protected void changeAI() {
        CollisionPlayerChaserAI<ChasmWarriorStatue> chasmwarriorai = new CollisionPlayerChaserAI(1200, new GameDamage(80.0F), 25);
        this.ai = new BehaviourTreeAI<>(this, chasmwarriorai);
        this.isHostile = true;
    }

    protected void homingShots(Attacker attacker) {
        GameRandom random = new GameRandom();
        for(int i = 0; i<3; i++){
            float angle = random.getIntBetween(0, 360);
            SoulHomingProjectile projectile = new SoulHomingProjectile(this.getLevel(), this, this.x, this.y, attacker.getAttackOwner().x, attacker.getAttackOwner().y, 40, 600, new GameDamage(40.0F), 20);
            projectile.setAngle(projectile.getAngle() + angle * i);
            projectile.turnSpeed = 0.015F;
            this.getLevel().entityManager.projectiles.add(projectile);
        }
    }

    public boolean isLavaImmune() {
        return true;
    }
    public boolean canBePushed(Mob other) {
        return false;
    }
    public LootTable getLootTable() {
        return lootTable;
    }

    public MobWasHitEvent isHit(MobWasHitEvent event, Attacker attacker) {
        if(!event.wasPrevented && attacker != null && !this.isHostile){
            changeAI();
            SoundManager.playSound(GameResources.fadedeath3, SoundEffect.effect(this).pitch(0.5F).volume(0.6F));
        }
        if (this.isHostile) {
            homingShots(attacker);
        }
        return super.isHit(event, attacker);
    }

    private void spawnSoul(){
        MeleeGhostSpawnEvent event = new MeleeGhostSpawnEvent((int) this.x, (int)this.y, 2000, this);
        this.getLevel().entityManager.events.add(event);
    }

    public void serverTick() {
        super.serverTick();
    }

    protected void onDeath(Attacker attacker, HashSet<Attacker> attackers) {
        spawnSoul();
        super.onDeath(attacker, attackers);
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(this.getTileX(), this.getTileY());
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 55;
        Point sprite = this.getAnimSprite(x, y, this.getDir());
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(this.getTileX(), this.getTileY()).getMobSinkingAmount(this);
        DrawOptions drawOptions = texture.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for (int i = 0; i < 16; i++) {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), texture, GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 20f, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public int getRockSpeed() {
        return 20;
    }
}
