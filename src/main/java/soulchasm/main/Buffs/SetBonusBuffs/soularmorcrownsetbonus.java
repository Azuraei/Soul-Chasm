package soulchasm.main.Buffs.SetBonusBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

public class soularmorcrownsetbonus extends soularmorsetbonus {
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(2).setUpgradedValue(1.0F, 3);
    public soularmorcrownsetbonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, 0.3F);
        buff.setModifier(BuffModifiers.MAX_SUMMONS, this.maxSummons.getValue(this.getUpgradeTier(buff)));
    }

    public ListGameTooltips getTooltip(ActiveBuff ab) {
        ListGameTooltips tooltips = new ListGameTooltips();
        this.addAllModifierTips(tooltips, ab, false);
        tooltips.add(super.getTooltip(ab));
        return tooltips;
    }
}
