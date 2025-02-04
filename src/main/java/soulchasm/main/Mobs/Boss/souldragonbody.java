package soulchasm.main.Mobs.Boss;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.gameLoop.tickManager.TicksPerSecond;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.hostile.bosses.BossWormMobBody;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Stream;


public class souldragonbody extends BossWormMobBody<souldragonhead, souldragonbody> {
    public int shadowSprite = 0;
    public int spriteY;
    public boolean spawnsParticles;
    public TicksPerSecond particleSpawner = TicksPerSecond.ticksPerSecond(20);
    public static GameTexture texture;

    public souldragonbody() {
        super(1000);
        this.isSummoned = true;
        this.collision = new Rectangle(-18, -15, 36, 30);
        this.hitBox = new Rectangle(-25, -20, 50, 40);
        this.selectBox = new Rectangle(-32, -80, 64, 84);
    }

    public GameDamage getCollisionDamage(Mob target) {
        return souldragonhead.souldragonCollisionDamage;
    }

    public boolean canCollisionHit(Mob target) {
        return super.canCollisionHit(target);
    }

    public void clientTick() {
        super.clientTick();
        if (this.spawnsParticles && this.isVisible()) {
            this.particleSpawner.gameTick();
            while(this.particleSpawner.shouldTick()) {
                this.getLevel().entityManager.addParticle(this.x + GameRandom.globalRandom.floatGaussian() * 15.0F, this.y + GameRandom.globalRandom.floatGaussian() * 10.0F + 5.0F, Particle.GType.COSMETIC).movesConstant(GameRandom.globalRandom.floatGaussian() * 6.0F, GameRandom.globalRandom.floatGaussian() * 3.0F).sizeFades(15, 25).color(new Color(16, 114, 255)).heightMoves(this.height + 10.0F, this.height + GameRandom.globalRandom.getFloatBetween(30.0F, 40.0F)).lifeTime(350);
            }
        }

    }

    public int getFlyingHeight() {
        return 20;
    }

    public Stream<ModifierValue<?>> getDefaultModifiers() {
        return Stream.of((new ModifierValue(BuffModifiers.SLOW, 0.0F)).max(0.0F), (new ModifierValue(BuffModifiers.POISON_DAMAGE_FLAT, 0.0F)).max(0.0F), (new ModifierValue(BuffModifiers.FIRE_DAMAGE_FLAT, 0.0F)).max(0.0F), (new ModifierValue(BuffModifiers.FROST_DAMAGE_FLAT, 0.0F)).max(0.0F), (new ModifierValue(BuffModifiers.FIRE_DAMAGE, 0.0F)).max(0.0F));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        if (this.isVisible()) {
            for(int i = 0; i < 4; ++i) {
                this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, 2, GameRandom.globalRandom.nextInt(6), 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
            }

        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        if (this.isVisible()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - 32;
            int drawY = camera.getDrawY(y);
            if (this.next != null) {
                Point2D.Float dir = new Point2D.Float(this.next.x - (float)x, this.next.y - ((souldragonbody)this.next).height - ((float)y - this.height));
                float angle = GameMath.fixAngle(GameMath.getAngle(dir));
                MobDrawable drawOptions = WormMobHead.getAngledDrawable(new GameSprite(texture, 0, this.spriteY, 64), null, light, (int)this.height, angle, drawX, drawY, 96);
                topList.add(drawOptions);
            }

            this.addShadowDrawables(tileList, x, y, light, camera);
        }
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera) {
        GameTexture shadowTexture = MobRegistry.Textures.swampGuardian_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        drawY += this.getBobbing(x, y);
        return shadowTexture.initDraw().sprite(this.shadowSprite, 0, res).light(light).pos(drawX, drawY);
    }
}
