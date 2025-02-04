package soulchasm.main.Buffs.ArmorBuffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;

import java.awt.*;

public class SoulDischargeSicknessDebuff extends Buff {

    public SoulDischargeSicknessDebuff() {
        this.canCancel = false;
        this.isVisible = true;
        this.shouldSave = false;
        this.isImportant =  true;
    }

    public int getStackSize() {
        return 4;
    }

    public boolean overridesStackDuration() {
        return true;
    }

    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        Mob owner = buff.owner;
        if (buff.owner.isVisible()) {
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0D), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0D), Particle.GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(new Color(32, 53, 77)).givesLight(0.0F, 0.5F).height(16.0F);
        }
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 1.2F);
        buff.setModifier(BuffModifiers.STAMINA_USAGE, 0.1F);
        buff.setModifier(BuffModifiers.ALL_DAMAGE, -0.05F);
        buff.setModifier(BuffModifiers.ATTACK_SPEED, -0.05F);
    }
}
