package soulchasm.main.buffs.trinketbuffs.SoulSealBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.SummonedCountBuff;

public class SoulSealFollowerBuff extends SummonedCountBuff {
    public SoulSealFollowerBuff() {
        this.canCancel = true;
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        super.init(buff, eventSubscriber);
    }
}
