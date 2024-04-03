package soulchasm.main.Items.Armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;

import static necesse.inventory.item.armorItem.ArmorItem.HairDrawMode.OVER_HAIR;


public class soularmorcrown extends SetHelmetArmorItem {
    public soularmorcrown() {
        super(16, DamageTypeRegistry.SUMMON,1500, "soularmorcrown", "soularmorchestplate", "soularmorboots", "soularmorcrownsetbonus");
        this.rarity = Rarity.EPIC;
        this.hairDrawOptions = OVER_HAIR;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(
                new ModifierValue(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.15F),
                new ModifierValue(BuffModifiers.MAX_SUMMONS, 2)
        );
    }
}
