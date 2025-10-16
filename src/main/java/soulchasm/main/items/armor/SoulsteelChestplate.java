package soulchasm.main.items.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.ChestArmorItem;
import necesse.inventory.lootTable.presets.IncursionHeadArmorLootTable;


public class SoulsteelChestplate extends ChestArmorItem {

    public SoulsteelChestplate() {
        super(36, 1500, Rarity.EPIC, "soularmorchestplate", "soularmorarms", IncursionHeadArmorLootTable.incursionHeadArmor);
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.ALL_DAMAGE, 0.1F));
    }
}
