package soulchasm.main.Items.Armor.SetBonusBuffs;

import necesse.engine.modifiers.ModifierTooltip;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTooltips.ListGameTooltips;

public class soularmorcrownsetbonus extends soularmorsetbonus {
    public soularmorcrownsetbonus() {

    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, 0.3F);
        buff.setModifier(BuffModifiers.MAX_SUMMONS, 1);
    }

    public ListGameTooltips getTooltip(ActiveBuff ab) {
        ListGameTooltips tooltips = new ListGameTooltips();
        ModifierTooltip tooltip1 = BuffModifiers.SUMMON_ATTACK_SPEED.getTooltip(ab.getModifier(BuffModifiers.SUMMON_ATTACK_SPEED), BuffModifiers.SUMMON_ATTACK_SPEED.defaultBuffValue);
        ModifierTooltip tooltip2 = BuffModifiers.MAX_SUMMONS.getTooltip(ab.getModifier(BuffModifiers.MAX_SUMMONS), BuffModifiers.MAX_SUMMONS.defaultBuffValue);
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
