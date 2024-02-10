package soulchasm.main.Projectiles;

import necesse.engine.Screen;
import necesse.engine.sound.SoundEffect;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.ParticleOption;
import necesse.entity.particle.fireworks.FireworksExplosion;
import necesse.entity.particle.fireworks.FireworksPath;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
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



public class soularrowprojectile extends Projectile {
    public static FireworksExplosion piercerPopExplosion;
    public boolean showEffects = true;

    public soularrowprojectile() {
    }

    public soularrowprojectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(damage);
        this.knockback = knockback;
    }

    public void init() {
        super.init();
        this.height = 18.0F;
        this.piercing = 0;
        this.setWidth(6.0F, false);
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) {
        if(showEffects){
            spawnEffects(mob);
        }
    }

    public void spawnEffects(Mob mob){
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
    }


    public void applyDamage(Mob mob, float x, float y) {
    }

    public Color getParticleColor() {
        return new Color(54, 195, 255);
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(21, 130, 255), 10.0F, 250, this.getHeight());
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y);
            final TextureDrawOptions options = this.texture.initDraw().light(light.minLevelCopy(Math.min(light.getLevel() + 100.0F, 150.0F))).rotate(this.getAngle(), this.texture.getWidth() / 2, 0).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
        }
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
