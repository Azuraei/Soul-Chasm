package soulchasm.main.buffs.armorbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.item.ItemStatTip;

import java.util.LinkedList;

public class SoulArmorHatSetBonus extends SoulArmorSetBonus {
    public SoulArmorHatSetBonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        super.init(buff, eventSubscriber);
        buff.setModifier(BuffModifiers.MAX_MANA, 2.0F);
        buff.setModifier(BuffModifiers.COMBAT_MANA_REGEN, 1.0F);
        buff.setModifier(BuffModifiers.MANA_USAGE, -0.2F);
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues) {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}
