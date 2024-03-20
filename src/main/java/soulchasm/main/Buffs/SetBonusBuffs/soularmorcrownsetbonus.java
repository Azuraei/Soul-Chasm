package soulchasm.main.Buffs.SetBonusBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

import java.util.LinkedList;

public class soularmorcrownsetbonus extends soularmorsetbonus {
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(2).setUpgradedValue(1.0F, 3);
    public soularmorcrownsetbonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, 0.3F);
        buff.setModifier(BuffModifiers.MAX_SUMMONS, this.maxSummons.getValue(this.getUpgradeTier(buff)));
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues) {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}
