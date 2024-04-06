package soulchasm.main.Buffs.SetBonusBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class souldischargebuff extends Buff {

    public souldischargebuff() {
        this.canCancel = false;
        this.isVisible = true;
        this.shouldSave = false;
        this.isImportant =  true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.SPEED, 1.0F);
        buff.setModifier(BuffModifiers.ATTACK_SPEED, 0.4F);
        buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.4F);
        buff.getGndData().setBoolean("tryDebuffApply", false);
    }
}
