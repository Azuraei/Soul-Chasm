package soulchasm.main.Mobs.Passive;

import necesse.engine.Screen;
import necesse.engine.sound.SoundEffect;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.MobWorldPosition;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.SoulChasm;

import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class sphereeffectmob extends Mob {
    public MobWorldPosition target;
    private float alphaBall;
    public static GameTexture texture_ball;
    private final int heightOffset = 32 * 2;

    public sphereeffectmob() {
        super(100);
        this.setFriction(1.0F);
        this.isSummoned = true;
        this.shouldSave = true;
        this.isStatic = true;
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-14, -12, 28, 24);
        this.selectBox = new Rectangle(-18, -45, 36, 48);
        this.setKnockbackModifier(0.0F);
    }

    public void init() {
        super.init();
        this.dir = 0;
        this.alphaBall = 0.7F;
    }

    public boolean canPushMob(Mob other) {
        return false;
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    private void spinningParticle(Level level, int x, int y, float distance, Color color, float speedMultiplier, float height){
        AtomicReference<Float> currentAngle = new AtomicReference<>(GameRandom.globalRandom.nextFloat() * 360.0F);
        int lifeTimeMod = (int) (1/speedMultiplier * 5000);
        level.entityManager.addParticle(x + GameMath.sin(currentAngle.get()) * distance, y + GameMath.cos(currentAngle.get()) * distance * 0.80F, Particle.GType.IMPORTANT_COSMETIC).lifeTime(lifeTimeMod).minDrawLight(60).color(color).height(32F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
            float lifeMod = GameMath.limit(1 - (float) timeAlive/lifeTime, 0.0F, 0.999F);
            float angle = currentAngle.accumulateAndGet(delta * 100.0F / 250.0F * speedMultiplier * (1-lifeMod), Float::sum);
            float distY = distance * 0.80F * lifeMod;
            pos.x = x + -GameMath.sin(angle) * distance * lifeMod;
            pos.y = y - heightOffset - height + GameMath.cos(angle) * distY * 0.80F + (float) timeAlive/lifeTime * 16;
        }).sizeFades(14, 20).fadesAlpha(1.0F, 0.95F);
    }
    //
    private void spawnBallParticles(Level level, int x, int y){
        if (GameRandom.globalRandom.getChance(0.45F)) {
            spinningParticle(level, x, y, 50F, new Color(0x0081DC), 1.0F, 16);
            spinningParticle(level, x, y, 350F, new Color(0x65B9FF), 0.20F, 8);
        }
        if (GameRandom.globalRandom.getChance(0.08F)) {
            int posX = x + GameRandom.globalRandom.getIntBetween(-8, 8);
            int posY = y - heightOffset;
            boolean mirror = GameRandom.globalRandom.nextBoolean();
            level.entityManager.addParticle((float)posX, (float)(posY + 16), Particle.GType.COSMETIC).sprite(SoulChasm.particleWispSection).fadesAlpha(0.6F, 0.6F).size((options, lifeTime, timeAlive, lifePercent) -> {
            }).height(30.0F).movesConstant(GameRandom.globalRandom.getFloatBetween(0.1F, 0.5F) * GameRandom.globalRandom.getOneOf(1.0F, -1.0F), GameRandom.globalRandom.getFloatBetween(-3.0F, -5.0F)).sizeFades(10, 20).modify((options, lifeTime, timeAlive, lifePercent) -> {
                options.mirror(mirror, false);
            }).lifeTime(7500);
        }
    }

    public void clientTick() {
        super.clientTick();
        spawnBallParticles(this.getLevel(), (int) x, (int) y);
    }

    public boolean canInteract(Mob mob) {
        return false;
    }

    public void onFollowingAnotherLevel(PlayerMob player) {
    }

    protected void playDeathSound() {
        Screen.playSound(GameResources.fadedeath3, SoundEffect.effect(this));
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x, y);
        int drawX = camera.getDrawX(x) + 8;
        int drawY = camera.getDrawY(y);
        long time = level.getWorldEntity().getTime();
        float rotation = GameUtils.getTimeRotation(time, 4);
        TextureDrawOptions optionsBall = texture_ball.initDraw().light(light.minLevelCopy(100)).alpha(this.alphaBall).rotate(rotation * (float)(this.dx < 0.0F ? -1 : 1), texture_ball.getWidth() / 2, texture_ball.getHeight() / 2).pos(drawX - 32, drawY - 64 - heightOffset);
        topList.add((tm) -> optionsBall.draw());
    }
}
