package soulchasm.main.Items.Armor.SetBonusBuffs;

import necesse.engine.modifiers.ModifierTooltip;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTooltips.ListGameTooltips;

public class soularmorhatsetbonus extends soularmorsetbonus {
    public soularmorhatsetbonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MAX_MANA, 2.0F);
        buff.setModifier(BuffModifiers.COMBAT_MANA_REGEN, 1.0F);
        buff.setModifier(BuffModifiers.MANA_USAGE, -0.2F);
    }

    public ListGameTooltips getTooltip(ActiveBuff ab) {
        ListGameTooltips tooltips = new ListGameTooltips();
        ModifierTooltip tooltip1 = BuffModifiers.MAX_MANA.getTooltip(ab.getModifier(BuffModifiers.MAX_MANA), BuffModifiers.MAX_MANA.defaultBuffValue);
        ModifierTooltip tooltip2 = BuffModifiers.COMBAT_MANA_REGEN.getTooltip(ab.getModifier(BuffModifiers.COMBAT_MANA_REGEN), BuffModifiers.COMBAT_MANA_REGEN.defaultBuffValue);
        ModifierTooltip tooltip3 = BuffModifiers.MANA_USAGE.getTooltip(ab.getModifier(BuffModifiers.MANA_USAGE), BuffModifiers.MANA_USAGE.defaultBuffValue);
        if (tooltip1 != null) {
            tooltips.add(tooltip1.message);
        }
        if (tooltip2 != null) {
            tooltips.add(tooltip2.message);
        }
        if (tooltip3 != null) {
            tooltips.add(tooltip3.message);
        }
        tooltips.add(super.getTooltip(ab));
        return tooltips;
    }
}
