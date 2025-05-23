package soulchasm.main.Items.Armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;


public class SoulArmorBoots extends BootsArmorItem {
    public SoulArmorBoots() {
        super(22, 1500, Rarity.EPIC, "soularmorboots");
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SPEED, 0.25F));
    }
}
