package soulchasm.main.Items.Armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorItem;
import necesse.inventory.item.armorItem.ArmorModifiers;


public class soularmorboots extends ArmorItem {
    public soularmorboots(int armorValue, int enchantCost, String textureName) {
        super(ArmorType.FEET, armorValue, enchantCost, textureName);
    }

    public soularmorboots() {
        super(ArmorType.FEET, 22, 1500, "soularmorboots");
        this.rarity = Rarity.EPIC;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SPEED, 0.25F));
    }

}
