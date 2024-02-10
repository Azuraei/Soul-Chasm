package soulchasm.main.Buffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;


public class phantomfeatherbuff extends TrinketBuff {
    public phantomfeatherbuff() {
        this.canCancel = false;
        this.isVisible = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.DASH_STACKS, 4);
        buff.setModifier(BuffModifiers.MAX_HEALTH, -0.25F);

    }
}
