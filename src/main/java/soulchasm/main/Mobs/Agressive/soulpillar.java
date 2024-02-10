package soulchasm.main.Mobs.Agressive;

import necesse.engine.Screen;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.StationaryPlayerShooterAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.mobs.hostile.bosses.EvilsPortalMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.SoulChasm;
import soulchasm.main.Projectiles.soulhomingprojectile;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class soulpillar extends HostileMob {
    public static LootTable lootTable;
    public static GameDamage damage;
    public static GameTexture texture;
    public static GameTexture glow;

    public soulpillar() {
        super(800);
        this.setSpeed(0.0F);
        this.setFriction(3.0F);
        this.setKnockbackModifier(0.0F);
        this.setArmor(60);
        this.collision = new Rectangle(-16, -48, 36, 32);
        this.hitBox = new Rectangle(-16, -48, 36, 64);
        this.selectBox = new Rectangle(-16, -48, 36, 64);
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new StationaryPlayerShooterAI<soulpillar>(620) {
            public void shootTarget(soulpillar mob, Mob target) {
                for(int i = -1; i<=1; i++){
                    soulhomingprojectile projectile = new soulhomingprojectile(mob.getLevel(), mob, mob.x, mob.y, target.x, target.y, 60.0F, 712, new GameDamage(50.0F), 50);
                    projectile.turnSpeed = projectile.turnSpeed * 0.8F;
                    projectile.clearTargetPosWhenAligned = true;
                    projectile.setAngle(projectile.getAngle() + 25 * i);
                    soulpillar.this.attack((int) (mob.x + projectile.dx * 100.0F), (int) (mob.y + projectile.dy * 100.0F), true);
                    soulpillar.this.getLevel().entityManager.projectiles.add(projectile);
                }
            }
        });
    }

    @Override
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
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), possesedstatue.texture, GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 20f, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
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
            }).lifeTime(6000).givesLight(60);
        }
    }

    @Override
    public void clientTick() {
        super.clientTick();
        spawnParticles(this.getTileX(), this.getTileY(), this.getLevel());
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 48;
        DrawOptions body = texture.initDraw().sprite(1, 0, 64).light(light).pos(drawX, drawY);
        int minLight = 80;
        DrawOptions eyes = glow.initDraw().sprite(0, 0, 64).alpha(0.9F).light(light.minLevelCopy((float)minLight)).pos(drawX, drawY);
        this.addShadowDrawables(topList, x, y, light, camera);
        topList.add((tm) -> {
            body.draw();
            eyes.draw();
        });
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera) {
        GameTexture shadowTexture = MobRegistry.Textures.human_big_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2 + 6;
        return shadowTexture.initDraw().sprite(0, 0, res).light(light).pos(drawX, drawY);
    }


    public void showAttack(int x, int y, int seed, boolean showAllDirections) {
        super.showAttack(x, y, seed, showAllDirections);
        if (this.getLevel().isClient()) {
            Screen.playSound(GameResources.magicbolt4, SoundEffect.effect(this).pitch(1.2F).volume(0.6F));
        }

    }

    static {
        lootTable = new LootTable();
    }

}
