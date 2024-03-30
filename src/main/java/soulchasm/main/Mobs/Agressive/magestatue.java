package soulchasm.main.Mobs.Agressive;

import necesse.engine.Screen;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.StationaryPlayerShooterAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.objectEntity.interfaces.OEVicinityBuff;
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
import soulchasm.SoulChasm;
import soulchasm.main.Projectiles.soulhomingprojectile;

import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

import static soulchasm.SoulChasm.spinspawnvisual;

public class magestatue extends HostileMob implements OEVicinityBuff {
    public static LootTable lootTable;
    public static GameDamage damage;
    public static GameTexture texture;
    private int randomSprite = 0;

    public magestatue() {
        super(900);
        this.setSpeed(0.0F);
        this.setFriction(3.0F);
        this.setKnockbackModifier(0.0F);
        this.setArmor(70);
        this.collision = new Rectangle(-24, -16, 48, 32);
        this.hitBox = new Rectangle(-24, -16, 48, 32);
        this.selectBox = new Rectangle(-24, -64, 48, 64 + 16);
    }

    public void init() {
        super.init();
        StationaryPlayerShooterAI<magestatue> mobAI = new StationaryPlayerShooterAI<magestatue>(400) {
            public void shootTarget(magestatue mob, Mob target) {
                for(int i = -1; i<=1; i++){
                    soulhomingprojectile projectile = new soulhomingprojectile(mob.getLevel(), mob, mob.x, mob.y, target.x, target.y, 50.0F, 800, new GameDamage(65.0F), 30);
                    projectile.turnSpeed = projectile.turnSpeed * 0.55F;
                    projectile.clearTargetPosWhenAligned = true;
                    projectile.setAngle(projectile.getAngle() + 25 * i);
                    projectile.moveDist(25);
                    magestatue.this.attack((int) (mob.x + projectile.dx * 100.0F), (int) (mob.y + projectile.dy * 100.0F), true);
                    magestatue.this.getLevel().entityManager.projectiles.add(projectile);
                }
            }
        };
        this.ai = new BehaviourTreeAI<>(this, mobAI);
        this.randomSprite = GameRandom.globalRandom.nextInt(4);
    }

    public Buff[] getBuffs() {
        return new Buff[]{BuffRegistry.HARDENED};
    }

    public int getBuffRange() {
        return 350;
    }

    public boolean shouldBuffPlayers() {
        return false;
    }

    public boolean shouldBuffMobs() {
        return true;
    }

    public Predicate<Mob> buffMobsFilter() {
        return (m) -> !m.isBoss() && m.isHostile && !m.isSummoned;
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

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for (int i = 0; i < 8; i++) {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), meleestatue.texture, GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 20f, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void spawnParticles(int x, int y, Level level){
        if (GameRandom.globalRandom.getChance(0.080F)) {
            int posX = x * 32 + 16 + GameRandom.globalRandom.getIntBetween(-16, 16);
            int posY = y * 32 - 16 + GameRandom.globalRandom.nextInt(48);
            boolean mirror = GameRandom.globalRandom.nextBoolean();
            level.entityManager.addParticle((float)posX, (float)(posY + 15), Particle.GType.COSMETIC).sprite(SoulChasm.particleWispSection).fadesAlpha(0.6F, 0.6F).size((options, lifeTime, timeAlive, lifePercent) -> {
            }).height(20.0F).movesConstant(GameRandom.globalRandom.getFloatBetween(0.5F, 1.5F) * GameRandom.globalRandom.getOneOf(1.0F, -1.5F), GameRandom.globalRandom.getFloatBetween(2.0F, 3.0F) * GameRandom.globalRandom.getOneOf(0.1F, -1.5F)).sizeFades(10, 15).modify((options, lifeTime, timeAlive, lifePercent) -> {
                options.mirror(mirror, false);
            }).lifeTime(6000).givesLight(30);
        }
    }

    @Override
    public void applyBuffs(Mob mob) {
        Buff[] var2 = this.getBuffs();
        for (Buff buff : var2) {
            if (buff != null) {
                ActiveBuff ab = new ActiveBuff(buff, mob, 100, this);
                mob.buffManager.addBuff(ab, false);
            }
        }
    }

    public void tickVicinityBuff(Mob mob) {
        Level level = mob.getLevel();
        int posX = (int) mob.x;
        int posY = (int) mob.y;
        this.tickVicinityBuff(level, posX, posY);
    }

    public void clientTick() {
        super.clientTick();
        this.tickVicinityBuff(this);
        spawnParticles(this.getTileX(), this.getTileY(), this.getLevel());
    }

    public void serverTick() {
        super.serverTick();
        this.tickVicinityBuff(this);
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 60;
        DrawOptions body = texture.initDraw().sprite(this.randomSprite, 0, 64, 80).light(light.minLevelCopy(40)).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                body.draw();
            }
        });
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections) {
        super.showAttack(x, y, seed, showAllDirections);
        if (this.getLevel().isClient()) {
            Screen.playSound(GameResources.magicbolt4, SoundEffect.effect(this).pitch(1.2F).volume(0.6F));
            float height = 64.0F + 32;
            this.getLevel().entityManager.addParticle(this.x, this.y + 14 + 64, Particle.GType.IMPORTANT_COSMETIC).sprite(spinspawnvisual).givesLight(230.0F, 0.3F).fadesAlphaTime(150, 250).lifeTime(800).height(height).size((options, lifeTime, timeAlive, lifePercent) -> options.size(20, 20));
        }
    }

    static {
        lootTable = new LootTable();
    }

}
