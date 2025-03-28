package soulchasm.main.Items.Armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;


public class SoulArmorChestplate extends ChestArmorItem {

    public SoulArmorChestplate() {
        super(36, 1500, Rarity.EPIC, "soularmorchestplate", "soularmorarms");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.ALL_DAMAGE, 0.1F));
    }
}
