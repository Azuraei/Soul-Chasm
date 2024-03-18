package soulchasm.main.Buffs.ToolBuffs.SoulMetalBowBuffs;

import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class soulbowbuff extends Buff {

    public soulbowbuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        this.canCancel = false;
        this.isImportant = true;
        this.isVisible = true;
        this.isPassive = false;
        this.shouldSave = true;
    }
    private void removeSelf(ActiveBuff buff){
        if(buff.getGndData().getInt("stacks")>=3){
            buff.remove();
        }
    }

    @Override
    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        removeSelf(buff);
    }

    @Override
    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        removeSelf(buff);
        Color color = new Color(51, 147, 255);
        if (buff.owner.isVisible()) {
            Mob owner = buff.owner;
            AtomicReference<Float> currentAngle = new AtomicReference<>(GameRandom.globalRandom.nextFloat() * 360.0F);
            float distance = 20.0F;
            owner.getLevel().entityManager.addParticle(owner.x + GameMath.sin(currentAngle.get()) * distance, owner.y + GameMath.cos((Float)currentAngle.get()) * distance * 0.75F, Particle.GType.COSMETIC).color(color).height(0.5F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
                float angle = currentAngle.accumulateAndGet(delta * 100.0F / 125.0F, Float::sum);
                float distY = distance * 0.75F;
                pos.x = owner.x + GameMath.sin(angle) * distance;
                pos.y = owner.y + GameMath.cos(angle) * distY * 0.75F;
            }).lifeTime(1000).sizeFades(10, 15);
        }
    }
}