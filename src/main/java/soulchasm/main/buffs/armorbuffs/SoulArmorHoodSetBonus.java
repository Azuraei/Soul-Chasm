package soulchasm.main.buffs.armorbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;

import java.util.LinkedList;

public class SoulArmorHoodSetBonus extends SoulArmorSetBonus {
    public FloatUpgradeValue critChance = (new FloatUpgradeValue()).setBaseValue(0.2F).setUpgradedValue(1.0F, 0.2F);
    public SoulArmorHoodSetBonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        super.init(buff, eventSubscriber);
        buff.setModifier(BuffModifiers.RANGED_CRIT_CHANCE, this.critChance.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.PROJECTILE_VELOCITY, 0.3F);
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues) {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}
