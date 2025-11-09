package soulchasm.main.buffs.armorbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

import java.util.LinkedList;

public class SoulArmorCrownSetBonus extends SoulArmorSetBonus {
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(3).setUpgradedValue(1.0F, 3);
    public SoulArmorCrownSetBonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        super.init(buff, eventSubscriber);
        buff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, 0.2F);
        buff.setModifier(BuffModifiers.MAX_SUMMONS, this.maxSummons.getValue(buff.getUpgradeTier()));
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues) {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}
