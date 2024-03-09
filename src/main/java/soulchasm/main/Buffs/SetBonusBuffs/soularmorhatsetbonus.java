package soulchasm.main.Buffs.SetBonusBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

public class soularmorhatsetbonus extends soularmorsetbonus {
    public IntUpgradeValue upgradeMultiplier = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1.0F, 2);
    public soularmorhatsetbonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MAX_MANA, 2.0F * this.upgradeMultiplier.getValue(this.getUpgradeTier(buff)));
        buff.setModifier(BuffModifiers.COMBAT_MANA_REGEN, 1.0F * this.upgradeMultiplier.getValue(this.getUpgradeTier(buff)));
        buff.setModifier(BuffModifiers.MANA_USAGE, -0.2F);
    }

    public ListGameTooltips getTooltip(ActiveBuff ab) {
        ListGameTooltips tooltips = new ListGameTooltips();
        this.addAllModifierTips(tooltips, ab, false);
        tooltips.add(super.getTooltip(ab));
        return tooltips;
    }
}
