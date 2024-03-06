package soulchasm.main.Items.Armor.SetBonusBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;

public class soularmorhelmetsetbonus extends soularmorsetbonus {
    public IntUpgradeValue upgradeMultiplier = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1.0F, 2);
    public soularmorhelmetsetbonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.2F * this.upgradeMultiplier.getValue(this.getUpgradeTier(buff)));
        buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 50);
        buff.setModifier(BuffModifiers.STAMINA_REGEN, 0.3F);
    }

    public ListGameTooltips getTooltip(ActiveBuff ab) {
        ListGameTooltips tooltips = new ListGameTooltips();
        this.addAllModifierTips(tooltips, ab, false);
        tooltips.add(super.getTooltip(ab));
        return tooltips;
    }
}
