package soulchasm.main.Buffs.PickaxeheadBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;

public class pickaxeheadstackbuff extends Buff {

    public pickaxeheadstackbuff() {
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
    public int getStackSize() {
        return 50;
    }

}
