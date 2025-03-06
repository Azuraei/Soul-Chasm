package soulchasm.main.Buffs.ToolBuffs;

import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ParticleOption;
import necesse.entity.particle.fireworks.FireworksExplosion;
import necesse.entity.particle.fireworks.FireworksPath;
import necesse.gfx.GameResources;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class SoulDeathMarkStackBuff extends Buff {
    public static FireworksExplosion piercerPopExplosion;
    public SoulDeathMarkStackBuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        this.canCancel = false;
        this.isVisible =  false;
    }

    public boolean overridesStackDuration() {
        return true;
    }

    public int getStackSize(ActiveBuff buff) {
        return 3;
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event) {
        super.onWasHit(buff, event);
        if(!event.wasPrevented && buff.owner.getLevel().isClient() && buff.owner.buffManager.getStacks(buff.buff)==3){
            showEffect(buff);
        }
    }

    private void updateBuff(ActiveBuff buff) {
        int currentStacks = buff.owner.buffManager.getStacks(buff.buff);
        boolean stackCheck = currentStacks==3;
        buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, stackCheck ? 1.3F : 1F);
        buff.setModifier(BuffModifiers.ARMOR, stackCheck ? -0.25F : 1F);
        buff.setModifier(BuffModifiers.POISON_DAMAGE_FLAT, stackCheck ? 50.0F : 0F);
        buff.setModifier(BuffModifiers.SLOW, stackCheck ? 0.5F : 0F);
        buff.forceManagerUpdate();
    }

    public void serverTick(ActiveBuff buff) {
        this.updateBuff(buff);
    }

    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        Color color1 = new Color(0x002B60);
        Color color2 = new Color(0x0057B6);
        Color color3 = new Color(0x3393FF);
        Color currentColor;
        if (buff.owner.isVisible()) {
            Mob owner = buff.owner;
            AtomicReference<Float> currentAngle = new AtomicReference<>(GameRandom.globalRandom.nextFloat() * 360.0F);
            float distance;
            switch (buff.owner.buffManager.getStacks(buff.buff)){
                case 2:
                    currentColor = color2;
                    distance = 30F;
                    break;
                case 3:
                    currentColor = color3;
                    distance = 20.0F;
                    break;
                default:
                    currentColor = color1;
                    distance = 40F;
            }
            owner.getLevel().entityManager.addParticle(owner.x + GameMath.sin(currentAngle.get()) * distance, owner.y + GameMath.cos(currentAngle.get()) * distance * 0.75F, Particle.GType.CRITICAL).color(currentColor).height(0.5F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
                float angle = currentAngle.accumulateAndGet(delta * 150.0F / 250.0F, Float::sum);
                float distY = distance * 0.75F;
                pos.x = owner.x + GameMath.sin(angle) * distance;
                pos.y = owner.y + GameMath.cos(angle) * distY * 0.75F;
            }).lifeTime(1000).sizeFades(16, 24);
        }
        this.updateBuff(buff);
    }

    public void showEffect(ActiveBuff buff){
        FireworksExplosion explosion = new FireworksExplosion(FireworksPath.sphere((float) GameRandom.globalRandom.getIntBetween(50, 60)));
        explosion.colorGetter = (particle, progress, random) -> ParticleOption.randomizeColor(211, 1.0F, 0.6F, 10.0F, 0.1F, 0.1F);
        explosion.trailChance = 0.5F;
        explosion.particles = 50;
        explosion.lifetime = 400;
        explosion.popOptions = piercerPopExplosion;
        explosion.particleLightHue = 211.0F;
        explosion.explosionSound = (pos, height, random) -> SoundManager.playSound(GameResources.fireworkExplosion, SoundEffect.effect(pos.x, pos.y).pitch(random.getOneOf(0.95F, 1.0F, 1.05F)).volume(0.2F).falloffDistance(1500));
        explosion.spawnExplosion(buff.owner.getLevel(), buff.owner.x, buff.owner.y, 10, GameRandom.globalRandom);
    }

    static {
        piercerPopExplosion = new FireworksExplosion(FireworksExplosion.popPath);
        piercerPopExplosion.particles = 3;
        piercerPopExplosion.lifetime = 200;
        piercerPopExplosion.minSize = 6;
        piercerPopExplosion.maxSize = 10;
        piercerPopExplosion.trailChance = 0.0F;
        piercerPopExplosion.popChance = 0.0F;
        piercerPopExplosion.colorGetter = (particle, progress, random) -> ParticleOption.randomizeColor(211, 1.0F, 0.6F, 10.0F, 0.1F, 0.1F);
        piercerPopExplosion.explosionSound = null;
    }
}
