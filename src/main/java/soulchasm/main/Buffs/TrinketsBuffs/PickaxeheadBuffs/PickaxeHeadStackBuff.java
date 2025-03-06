package soulchasm.main.Buffs.TrinketsBuffs.PickaxeheadBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class PickaxeHeadStackBuff extends Buff {

    public PickaxeHeadStackBuff() {
        this.canCancel = false;
        this.isVisible = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MINING_SPEED, 0.03F);
    }

    @Override
    public boolean overridesStackDuration() {
        return true;
    }

    @Override
    public int getStackSize(ActiveBuff activeBuff) {
        return 50;
    }

}
