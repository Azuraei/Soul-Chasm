package soulchasm.main.Mobs.Passive;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.client.Client;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.LevelMap;
import necesse.level.maps.light.GameLight;
import soulchasm.SoulChasm;

import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SphereEffectMob extends Mob {
    public MobWorldPosition target;
    public static GameTexture texture_ball;
    private final int heightOffset = 32 * 2;

    public SphereEffectMob() {
        super(10000);
        this.isSummoned = true;
        this.shouldSave = true;
        this.isStatic = true;
        this.setKnockbackModifier(0.0F);
    }

    public void init() {
        super.init();
        this.setDir(0);
    }

    public boolean canPushMob(Mob other) {
        return false;
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    public boolean canInteract(Mob mob) {
        return false;
    }

    public void onFollowingAnotherLevel(PlayerMob player) {}

    public boolean isVisibleOnMap(Client client, LevelMap map) {
        return false;
    }

    public boolean isHealthBarVisible() {
        return false;
    }

    public boolean countDamageDealt() {
        return false;
    }

    public boolean canTakeDamage() {
        return false;
    }

    @Override
    public void remove(float knockbackX, float knockbackY, Attacker attacker, boolean isDeath) {
    }

    private void spinningParticle(Level level, int x, int y, float distance, Color color, float speedMultiplier, float height){
        AtomicReference<Float> currentAngle = new AtomicReference<>(GameRandom.globalRandom.nextFloat() * 360.0F);
        int lifeTimeMod = (int) (1/speedMultiplier * 5000);
        level.entityManager.addParticle(x + GameMath.sin(currentAngle.get()) * distance, y + GameMath.cos(currentAngle.get()) * distance * 0.80F, Particle.GType.IMPORTANT_COSMETIC).lifeTime(lifeTimeMod).minDrawLight(75).color(color).height(32F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
            float lifeMod = GameMath.limit(1 - (float) timeAlive/lifeTime, 0.0F, 0.999F);
            float angle = currentAngle.accumulateAndGet(delta * 100.0F / 250.0F * speedMultiplier * (1-lifeMod), Float::sum);
            float distY = distance * 0.80F * lifeMod;
            pos.x = x + -GameMath.sin(angle) * distance * lifeMod;
            pos.y = y - heightOffset - height + GameMath.cos(angle) * distY * 0.80F + (float) timeAlive/lifeTime * 16;
        }).sizeFades(14, 20).fadesAlpha(1.0F, 0.95F);
    }

    private void spawnBallParticles(Level level, int x, int y){
        if (GameRandom.globalRandom.getChance(0.45F)) {
            Color color1 = new Color(101, 185, 255);
            Color color2 = new Color(0, 129, 220);
            Color color3 = new Color(0, 79, 131);
            spinningParticle(level, x, y, 50F, color1, 1.0F, 16);
            spinningParticle(level, x, y, 350F, GameRandom.globalRandom.getOneOf(color1, color2, color3), 0.20F, 8);
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
        this.getLevel().lightManager.refreshParticleLightFloat(this.x, this.y, new Color(54, 194, 255), 0.6F, 80);
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x, y);
        long time = level.getWorldEntity().getTime();
        int bobbing = (int)(GameUtils.getBobbing(time, 1000) * 5.0F);
        float rotation = GameUtils.getTimeRotation(time, 4);
        int drawX = camera.getDrawX(x) + 8;
        int drawY = camera.getDrawY(y) + bobbing;
        TextureDrawOptions optionsBall = texture_ball.initDraw().light(light.minLevelCopy(100)).alpha(0.7F).rotate(rotation * (float)(this.dx < 0.0F ? -1 : 1), texture_ball.getWidth() / 2, texture_ball.getHeight() / 2).pos(drawX - 32, drawY - 64 - heightOffset);
        topList.add((tm) -> optionsBall.draw());
    }
}
