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
import necesse.level.maps.incursion.IncursionData;


public class SoulsteelHood extends SetHelmetArmorItem {
    public SoulsteelHood() {
        super(26, DamageTypeRegistry.RANGED,1900, IncursionHeadArmorLootTable.incursionHeadArmor, IncursionArmorSetsLootTable.incursionArmorSets, Rarity.EPIC, "soularmorhood", "soularmorchestplate", "soularmorboots", "soularmorhoodsetbonus");
        this.hairDrawOptions = HairDrawMode.NO_HEAD;
        this.canBeUsedForRaids = true;
        this.minRaidTier = 1;
        this.maxRaidTier = IncursionData.ITEM_TIER_UPGRADE_CAP;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(
                new ModifierValue(BuffModifiers.RANGED_CRIT_DAMAGE, 0.15F),
                new ModifierValue(BuffModifiers.RANGED_ATTACK_SPEED, 0.2F),
                new ModifierValue(BuffModifiers.ARROW_USAGE, -0.2F),
                new ModifierValue(BuffModifiers.BULLET_USAGE, -0.2F)
        );
    }
}
