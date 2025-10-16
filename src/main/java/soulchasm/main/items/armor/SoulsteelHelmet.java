package soulchasm.main.items.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.lootTable.presets.IncursionArmorSetsLootTable;
import necesse.inventory.lootTable.presets.IncursionHeadArmorLootTable;

public class SoulsteelHelmet extends SetHelmetArmorItem {
    public SoulsteelHelmet() {
        super(30, DamageTypeRegistry.MELEE, 1500, IncursionHeadArmorLootTable.incursionHeadArmor, IncursionArmorSetsLootTable.incursionArmorSets, Rarity.EPIC, "soularmorhelmet", "soularmorchestplate", "soularmorboots", "soularmorhelmetsetbonus");
        this.hairDrawOptions = HairDrawMode.NO_HEAD;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(
                new ModifierValue(BuffModifiers.MELEE_CRIT_DAMAGE, 0.15F),
                new ModifierValue(BuffModifiers.MELEE_ATTACK_SPEED, 0.2F)
        );
    }
}
