package soulchasm.main.buffs.armorbuffs;

import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class SoulDischargeBuff extends Buff {

    public SoulDischargeBuff() {
        this.canCancel = false;
        this.isVisible = true;
        this.shouldSave = false;
        this.isImportant =  true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.SPEED, 0.6F);
        buff.setModifier(BuffModifiers.ATTACK_SPEED, 0.5F);
        buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.5F);
    }

    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        if (buff.owner.isVisible()) {
            Color color = new Color(51, 147, 255);
            Mob owner = buff.owner;
            AtomicReference<Float> currentAngle = new AtomicReference<>(GameRandom.globalRandom.nextFloat() * 360.0F);
            float distance = 20.0F;
            owner.getLevel().entityManager.addParticle(owner.x + GameMath.sin(currentAngle.get()) * distance, owner.y + GameMath.cos(currentAngle.get()) * distance * 0.75F, Particle.GType.CRITICAL).color(color).height(0.5F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
                float angle = currentAngle.accumulateAndGet(delta * 150.0F / 250.0F, Float::sum);
                float distY = distance * 0.75F;
                pos.x = owner.x + GameMath.sin(angle) * distance;
                pos.y = owner.y + GameMath.cos(angle) * distY * 0.75F;
            }).lifeTime(1000).sizeFades(14, 18);
        }
    }
}
