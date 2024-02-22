package soulchasm.main.Projectiles.WeaponProjectiles;

import necesse.engine.Screen;
import necesse.engine.sound.SoundEffect;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ParticleOption;
import necesse.entity.particle.ProjectileHitStuckParticle;
import necesse.entity.particle.fireworks.FireworksExplosion;
import necesse.entity.particle.fireworks.FireworksPath;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class soulspearprojectile extends Projectile {
    public static FireworksExplosion piercerPopExplosion;
    public soulspearprojectile() {
    }

    public void init() {
        super.init();
        this.height = 18.0F;
        this.setWidth(10.0F);
        this.trailOffset = -50.0F;
        this.heightBasedOnDistance = true;
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(19, 127, 241), 10.0F, 250, 18.0F);
    }

    public void addDrawables(java.util.List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - 2;
            int drawY = camera.getDrawY(this.y) - 2;
            final TextureDrawOptions options = soulspearprojectile.this.texture.initDraw().light(light).rotate(this.getAngle() + 45.0F, 2, 2).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle() + 45.0F, 2, 2);
        }
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) {
        super.doHitLogic(mob, object, x, y);
        float targetX;
        float targetY;
        if (mob != null) {
            targetX = mob.x;
            targetY = mob.y;
        } else {
            targetX = x;
            targetY = y;
        }

        int range = 80;
        if (!this.getLevel().isServer()) {
            FireworksExplosion explosion = new FireworksExplosion(FireworksPath.sphere((float) GameRandom.globalRandom.getIntBetween(range - 10, range)));
            explosion.colorGetter = (particle, progress, random) -> ParticleOption.randomizeColor(240.0F, 0.5F, 0.4F, 10.0F, 0.1F, 0.1F);
            explosion.trailChance = 0.5F;
            explosion.particles = 50;
            explosion.lifetime = 400;
            explosion.popOptions = piercerPopExplosion;
            explosion.particleLightHue = 230.0F;
            explosion.explosionSound = (pos, height, random) -> {
                Screen.playSound(GameResources.fireworkExplosion, SoundEffect.effect(pos.x, pos.y).pitch((Float)random.getOneOf(new Float[]{0.95F, 1.0F, 1.05F})).volume(0.2F).falloffDistance(1500));
            };
            explosion.spawnExplosion(this.getLevel(), targetX, targetY, this.getHeight(), GameRandom.globalRandom);
        }

        if (!this.getLevel().isClient()) {
            Rectangle targetBox = new Rectangle((int)targetX - range, (int)targetY - range, range * 2, range * 2);
            this.streamTargets(this.getOwner(), targetBox).filter((m) -> this.canHit(m) && m.getDistance(targetX, targetY) <= (float)range).forEach((m) -> {
                m.isServerHit(this.getDamage(), m.x - x, m.y - y, (float)this.knockback, this);
            });
        }

        if (this.getLevel().isClient() && this.traveledDistance < (float)this.distance) {
            final float height = this.getHeight();
            this.getLevel().entityManager.addParticle(new ProjectileHitStuckParticle(mob, this, x, y, mob == null ? 10.0F : 40.0F, 2000L) {
                public void addDrawables(Mob target, float x, float y, float angle, List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
                    GameLight light = level.getLightLevel(this);
                    int drawX = camera.getDrawX(x) - 2;
                    int drawY = camera.getDrawY(y - height) - 2;
                    float alpha = 1.0F;
                    long lifeCycleTime = this.getLifeCycleTime();
                    int fadeTime = 1000;
                    if (lifeCycleTime >= this.lifeTime - (long)fadeTime) {
                        alpha = Math.abs((float)(lifeCycleTime - (this.lifeTime - (long)fadeTime)) / (float)fadeTime - 1.0F);
                    }

                    int cut = target == null ? 10 : 0;
                    final TextureDrawOptions options = soulspearprojectile.this.texture.initDraw().section(cut, soulspearprojectile.this.texture.getWidth(), cut, soulspearprojectile.this.texture.getHeight()).light(light).rotate(getAngle() + 45.0F, 2, 2).alpha(alpha).pos(drawX, drawY);
                    EntityDrawable drawable = new EntityDrawable(this) {
                        public void draw(TickManager tickManager) {
                            options.draw();
                        }
                    };
                    if (target != null) {
                        topList.add(drawable);
                    } else {
                        list.add(drawable);
                    }

                }
            }, Particle.GType.IMPORTANT_COSMETIC);
        }

    }

    protected void playHitSound(float x, float y) {
        Screen.playSound(GameResources.tap, SoundEffect.effect(x, y).volume(0.5F).pitch(0.8F));
    }

    static {
        piercerPopExplosion = new FireworksExplosion(FireworksExplosion.popPath);
        piercerPopExplosion.particles = 1;
        piercerPopExplosion.lifetime = 200;
        piercerPopExplosion.minSize = 6;
        piercerPopExplosion.maxSize = 10;
        piercerPopExplosion.trailChance = 0.0F;
        piercerPopExplosion.popChance = 0.0F;
        piercerPopExplosion.colorGetter = (particle, progress, random) -> ParticleOption.randomizeColor(240.0F, 0.8F, 0.7F, 20.0F, 0.1F, 0.1F);
        piercerPopExplosion.explosionSound = null;
    }
}
