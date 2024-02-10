package soulchasm.main.Buffs;

import necesse.engine.Screen;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
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

public class souldeathmarkstackbuff extends Buff {
    public static FireworksExplosion piercerPopExplosion;
    private int currentStacksPrivate;
    public souldeathmarkstackbuff() {
        this.isImportant = true;
    }

    @Override
    public boolean overridesStackDuration() {
        return true;
    }

    @Override
    public int getStackSize() {
        return 4;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 1F);
        this.canCancel = false;
        this.isVisible =  false;
    }

    @Override
    public void onWasHit(ActiveBuff buff, MobWasHitEvent event) {
        super.onWasHit(buff, event);
        if(currentStacksPrivate + 1 >= 4 && event.attacker!=null && event.attacker.getAttackOwner().isPlayer){
            event.target.buffManager.addBuff(new ActiveBuff("souldeathmarkstackbuff", event.target, 10F, event.attacker), event.target.getLevel().isServer());
        }
    }

    private void updateBuff(ActiveBuff buff) {
        int currentStacks;
        currentStacks = buff.owner.buffManager.getStacks(buff.buff);
        buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, currentStacks == 3 ? (1.5F) : 1F);
        currentStacksPrivate = currentStacks;
        if(buff.owner.buffManager.getStacks(buff.buff)>=4){
            showEffect(buff);
            buff.owner.buffManager.removeBuff("souldeathmarkstackbuff", false);
        }
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
            owner.getLevel().entityManager.addParticle(owner.x + GameMath.sin(currentAngle.get()) * distance, owner.y + GameMath.cos((Float)currentAngle.get()) * distance * 0.75F, Particle.GType.CRITICAL).color(currentColor).height(0.5F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
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
        explosion.explosionSound = (pos, height, random) -> {
            Screen.playSound(GameResources.fireworkExplosion, SoundEffect.effect(pos.x, pos.y).pitch((Float)random.getOneOf(new Float[]{0.95F, 1.0F, 1.05F})).volume(0.2F).falloffDistance(1500));
        };
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
