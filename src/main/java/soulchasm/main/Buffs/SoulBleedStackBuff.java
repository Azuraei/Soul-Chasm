package soulchasm.main.Buffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;

import java.awt.*;

public class SoulBleedStackBuff extends Buff {
    public SoulBleedStackBuff() {
    }

    @Override
    public boolean overridesStackDuration() {
        return true;
    }

    @Override
    public int getStackSize() {
        return 8;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.POISON_DAMAGE_FLAT, 8.0F);
        buff.setModifier(BuffModifiers.ARMOR, -0.5F);
        this.canCancel = false;
    }

    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        Mob owner = buff.owner;
        if (owner.isVisible() && GameRandom.globalRandom.nextInt(2) == 0) {
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.bubbleParticle.sprite(0, 0, 12)).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(new Color(23, 159, 241)).height(16.0F);
        }
    }
}
