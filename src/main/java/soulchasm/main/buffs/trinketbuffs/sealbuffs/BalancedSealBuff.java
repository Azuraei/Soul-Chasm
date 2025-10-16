package soulchasm.main.buffs.trinketbuffs.sealbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;

public class BalancedSealBuff extends TrinketBuff {

    public BalancedSealBuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.10F);
    }
}


