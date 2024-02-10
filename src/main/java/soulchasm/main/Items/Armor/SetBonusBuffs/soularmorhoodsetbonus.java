package soulchasm.main.Items.Armor.SetBonusBuffs;

import necesse.engine.modifiers.ModifierTooltip;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTooltips.ListGameTooltips;

public class soularmorhoodsetbonus extends soularmorsetbonus {
    public soularmorhoodsetbonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.RANGED_CRIT_CHANCE, 0.15F);
        buff.setModifier(BuffModifiers.PROJECTILE_VELOCITY, 0.30F);
    }

    public ListGameTooltips getTooltip(ActiveBuff ab) {
        ListGameTooltips tooltips = new ListGameTooltips();
        ModifierTooltip tooltip1 = BuffModifiers.RANGED_CRIT_CHANCE.getTooltip(ab.getModifier(BuffModifiers.RANGED_CRIT_CHANCE), BuffModifiers.RANGED_CRIT_CHANCE.defaultBuffValue);
        ModifierTooltip tooltip2 = BuffModifiers.PROJECTILE_VELOCITY.getTooltip(ab.getModifier(BuffModifiers.PROJECTILE_VELOCITY), BuffModifiers.PROJECTILE_VELOCITY.defaultBuffValue);
        if (tooltip1 != null) {
            tooltips.add(tooltip1.message);
        }
        if (tooltip2 != null) {
            tooltips.add(tooltip2.message);
        }
        tooltips.add(super.getTooltip(ab));
        return tooltips;
    }
}
