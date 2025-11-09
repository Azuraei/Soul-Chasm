package soulchasm.main.items.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.BootsArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.IncursionFeetArmorLootTable;


public class SoulsteelBoots extends BootsArmorItem {
    public FloatUpgradeValue speed = (new FloatUpgradeValue()).setBaseValue(0.25F).setUpgradedValue(1.0F, 0.25F);
    public SoulsteelBoots() {
        super(22, 2000, Rarity.EPIC, "soularmorboots", IncursionFeetArmorLootTable.incursionFeetArmor);
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SPEED, this.speed.getValue(this.getUpgradeTier(item))), new ModifierValue(BuffModifiers.STAMINA_REGEN, 0.25F));
    }
}
