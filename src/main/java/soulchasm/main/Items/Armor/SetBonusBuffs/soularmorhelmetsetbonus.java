package soulchasm.main.Items.Armor.SetBonusBuffs;

import necesse.engine.modifiers.ModifierTooltip;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTooltips.ListGameTooltips;

public class soularmorhelmetsetbonus extends soularmorsetbonus {
    public soularmorhelmetsetbonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.3F);
        buff.setModifier(BuffModifiers.STAMINA_REGEN, 0.3F);
    }

    public ListGameTooltips getTooltip(ActiveBuff ab) {
        ListGameTooltips tooltips = new ListGameTooltips();
        ModifierTooltip tooltip1 = BuffModifiers.MAX_RESILIENCE.getTooltip(ab.getModifier(BuffModifiers.MAX_RESILIENCE), BuffModifiers.MAX_RESILIENCE.defaultBuffValue);
        ModifierTooltip tooltip2 = BuffModifiers.STAMINA_REGEN.getTooltip(ab.getModifier(BuffModifiers.STAMINA_REGEN), BuffModifiers.STAMINA_REGEN.defaultBuffValue);
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
