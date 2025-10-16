package soulchasm.main.buffs.armorbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.item.ItemStatTip;

import java.util.LinkedList;

public class SoulArmorHelmetSetBonus extends SoulArmorSetBonus {
    public SoulArmorHelmetSetBonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        super.init(buff, eventSubscriber);
        buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.2F);
        buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 50);
        buff.setModifier(BuffModifiers.STAMINA_REGEN, 0.3F);
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues) {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}
