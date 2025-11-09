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

import static necesse.inventory.item.armorItem.ArmorItem.HairDrawMode.OVER_HAIR;


public class SoulsteelCrown extends SetHelmetArmorItem {
    public SoulsteelCrown() {
        super(22, DamageTypeRegistry.SUMMON,1500, IncursionHeadArmorLootTable.incursionHeadArmor, IncursionArmorSetsLootTable.incursionArmorSets, Rarity.EPIC, "soularmorcrown", "soularmorchestplate", "soularmorboots", "soularmorcrownsetbonus");
        this.hairDrawOptions = OVER_HAIR;
        this.canBeUsedForRaids = true;
        this.minRaidTier = 1;
        this.maxRaidTier = IncursionData.ITEM_TIER_UPGRADE_CAP;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(
                new ModifierValue(BuffModifiers.SUMMON_CRIT_CHANCE, 0.15F),
                new ModifierValue(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.15F),
                new ModifierValue(BuffModifiers.MAX_SUMMONS, 2)
        );
    }
}
