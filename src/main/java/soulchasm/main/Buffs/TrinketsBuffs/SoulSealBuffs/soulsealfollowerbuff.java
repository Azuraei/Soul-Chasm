package soulchasm.main.Buffs.TrinketsBuffs.SoulSealBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.SummonedCountBuff;

public class soulsealfollowerbuff extends SummonedCountBuff {
    public soulsealfollowerbuff() {
        this.canCancel = true;
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        super.init(buff, eventSubscriber);
    }
}
