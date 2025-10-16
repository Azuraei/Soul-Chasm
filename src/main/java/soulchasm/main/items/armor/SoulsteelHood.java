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


public class SoulsteelHood extends SetHelmetArmorItem {
    public SoulsteelHood() {
        super(24, DamageTypeRegistry.RANGED,1500, IncursionHeadArmorLootTable.incursionHeadArmor, IncursionArmorSetsLootTable.incursionArmorSets, Rarity.EPIC, "soularmorhood", "soularmorchestplate", "soularmorboots", "soularmorhoodsetbonus");
        this.hairDrawOptions = HairDrawMode.NO_HEAD;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(
                new ModifierValue(BuffModifiers.RANGED_CRIT_DAMAGE, 0.15F),
                new ModifierValue(BuffModifiers.RANGED_ATTACK_SPEED, 0.20F),
                new ModifierValue(BuffModifiers.ARROW_USAGE, -0.2F),
                new ModifierValue(BuffModifiers.BULLET_USAGE, -0.2F)
        );
    }
}
