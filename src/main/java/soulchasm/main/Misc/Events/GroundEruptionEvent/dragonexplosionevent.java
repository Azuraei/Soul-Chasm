package soulchasm.main.Misc.Events.GroundEruptionEvent;

import necesse.engine.Screen;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.levelEvent.explosionEvent.ExplosionEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import soulchasm.SoulChasm;

import java.awt.geom.Point2D;

public class dragonexplosionevent extends ExplosionEvent implements Attacker {
    protected ParticleTypeSwitcher explosionTypeSwitcher;

    public dragonexplosionevent() {
        this(0.0F, 0.0F, 100, new GameDamage(100.0F), false, 0, null);
    }

    public dragonexplosionevent(float x, float y, int range, GameDamage damage, boolean destructive, int toolTier, Mob owner) {
        super(x, y, range, damage, destructive, toolTier, owner);
        this.explosionTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC, Particle.GType.CRITICAL);
        this.hitsOwner = false;
    }

    protected GameDamage getTotalObjectDamage(float targetDistance) {
        return super.getTotalObjectDamage(targetDistance).modDamage(10.0F);
    }

    protected void playExplosionEffects() {
        Screen.playSound(GameResources.explosionHeavy, SoundEffect.effect(this.x, this.y).volume(2.0F).pitch(1.5F));
        this.level.getClient().startCameraShake(this.x, this.y, 200, 40, 2.0F, 2.0F, true);
    }

    public float getParticleCount(float currentRange, float lastRange) {
        return super.getParticleCount(currentRange, lastRange) * 1.5F;
    }

    protected float getDistanceMod(float targetDistance) {
        return 1.0F;
    }

    public void spawnExplosionParticle(float x, float y, float dirX, float dirY, int lifeTime, float range) {
        if (GameRandom.globalRandom.getChance(0.5F)) {
            this.level.entityManager.addParticle(x + 4.0F, y - 10.0F, this.explosionTypeSwitcher.next()).sprite(SoulChasm.particleFlamethrowerSection.sprite(GameRandom.globalRandom.nextInt(6), 0, 20)).sizeFades(25, 40).movesConstant(dirX * 0.8F, dirY * 0.8F).height(10.0F).givesLight(190.0F, 0.5F).onProgress(0.4F, (p) -> {
                Point2D.Float norm = GameMath.normalize(dirX, dirY);
                this.level.entityManager.addParticle(p.x + norm.x * 20.0F, p.y + norm.y * 20.0F, Particle.GType.IMPORTANT_COSMETIC).movesConstant(dirX, dirY).smokeColor().heightMoves(10.0F, 40.0F).lifeTime(lifeTime);
            }).lifeTime(lifeTime);
        }

    }
}