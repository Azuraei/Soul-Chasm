package soulchasm.main.Buffs.SetBonusBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class souldischargesicknessdebuff extends Buff {

    public souldischargesicknessdebuff() {
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
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 1.2F);
        buff.setModifier(BuffModifiers.STAMINA_USAGE, 0.1F);
        buff.setModifier(BuffModifiers.ALL_DAMAGE, -0.05F);
        buff.setModifier(BuffModifiers.ATTACK_SPEED, -0.05F);
    }
}
