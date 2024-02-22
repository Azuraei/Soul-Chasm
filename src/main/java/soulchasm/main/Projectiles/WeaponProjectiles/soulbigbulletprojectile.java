package soulchasm.main.Projectiles.WeaponProjectiles;

import necesse.engine.Screen;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.particle.ParticleOption;
import necesse.entity.particle.fireworks.FireworksExplosion;
import necesse.entity.particle.fireworks.FireworksPath;
import necesse.entity.projectile.bulletProjectile.BulletProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameSprite;
import necesse.level.maps.LevelObjectHit;

import java.awt.*;

public class soulbigbulletprojectile extends BulletProjectile {
    public static FireworksExplosion piercerPopExplosion;

    public soulbigbulletprojectile() {
        this.height = 18.0F;
    }

    public soulbigbulletprojectile(float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, Mob owner) {
        this();
        this.setLevel(owner.getLevel());
        this.applyData(x, y, targetX, targetY, speed, distance, damage, knockback, owner);
    }

    public void init() {
        super.init();
        this.givesLight = true;
        this.trailOffset = 0.0F;
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) {
        float targetX;
        float targetY;
        if (mob != null) {
            targetX = mob.x;
            targetY = mob.y;
        } else {
            targetX = x;
            targetY = y;
        }

        int range = 100;
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

    public Trail getTrail() {
        Trail trail = new Trail(this, this.getLevel(), new Color(0, 116, 176), 30.0F, 150, this.getHeight());
        trail.sprite = new GameSprite(GameResources.chains, 7, 0, 32);
        return trail;
    }

    protected Color getWallHitColor() {
        return new Color(2, 177, 246);
    }

    static {
        piercerPopExplosion = new FireworksExplosion(FireworksExplosion.popPath);
        piercerPopExplosion.particles = 4;
        piercerPopExplosion.lifetime = 200;
        piercerPopExplosion.minSize = 8;
        piercerPopExplosion.maxSize = 15;
        piercerPopExplosion.trailChance = 0.0F;
        piercerPopExplosion.popChance = 0.0F;
        piercerPopExplosion.colorGetter = (particle, progress, random) -> ParticleOption.randomizeColor(230.0F, 0.8F, 0.7F, 20.0F, 0.1F, 0.1F);
        piercerPopExplosion.explosionSound = null;
    }
}
