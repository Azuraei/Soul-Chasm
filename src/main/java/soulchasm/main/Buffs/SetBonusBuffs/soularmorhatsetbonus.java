package soulchasm.main.Buffs.SetBonusBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

import java.util.LinkedList;

public class soularmorhatsetbonus extends soularmorsetbonus {
    public IntUpgradeValue upgradeMultiplier = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1.0F, 2);
    public soularmorhatsetbonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MAX_MANA, 2.0F * this.upgradeMultiplier.getValue(this.getUpgradeTier(buff)));
        buff.setModifier(BuffModifiers.COMBAT_MANA_REGEN, 1.0F * this.upgradeMultiplier.getValue(this.getUpgradeTier(buff)));
        buff.setModifier(BuffModifiers.MANA_USAGE, -0.2F);
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues) {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}
