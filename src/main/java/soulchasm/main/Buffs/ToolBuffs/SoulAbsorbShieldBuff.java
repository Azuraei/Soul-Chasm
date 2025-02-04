package soulchasm.main.Buffs.ToolBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class SoulAbsorbShieldBuff extends Buff {
    public SoulAbsorbShieldBuff() {
        this.canCancel = false;
        this.isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.ATTACK_SPEED, 0.2F);
        buff.setModifier(BuffModifiers.RESILIENCE_GAIN, 1.0F);
        buff.setModifier(BuffModifiers.RESILIENCE_REGEN, 0.5F);
    }
}