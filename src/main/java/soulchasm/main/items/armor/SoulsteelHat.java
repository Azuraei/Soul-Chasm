package soulchasm.main.items.armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.IncursionArmorSetsLootTable;
import necesse.inventory.lootTable.presets.IncursionHeadArmorLootTable;
import necesse.level.maps.incursion.IncursionData;


public class SoulsteelHat extends SetHelmetArmorItem {
    public FloatUpgradeValue manaRegen = (new FloatUpgradeValue()).setBaseValue(1.5F).setUpgradedValue(1.0F, 1.5F);
    public SoulsteelHat() {
        super(24, DamageTypeRegistry.MAGIC,1900, IncursionHeadArmorLootTable.incursionHeadArmor, IncursionArmorSetsLootTable.incursionArmorSets, Rarity.EPIC, "soularmorhat", "soularmorchestplate", "soularmorboots", "soularmorhatsetbonus");
        this.hairDrawOptions = HairDrawMode.OVER_HAIR;
        this.facialFeatureDrawOptions = FacialFeatureDrawMode.OVER_FACIAL_FEATURE;
        this.hairMaskTextureName = "magehat_voidhat_hairmask";
        this.canBeUsedForRaids = true;
        this.minRaidTier = 1;
        this.maxRaidTier = IncursionData.ITEM_TIER_UPGRADE_CAP;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(
                new ModifierValue(BuffModifiers.MAGIC_CRIT_DAMAGE, 0.15F),
                new ModifierValue(BuffModifiers.MAGIC_ATTACK_SPEED, 0.2F),
                new ModifierValue(BuffModifiers.COMBAT_MANA_REGEN, this.manaRegen.getValue(this.getUpgradeTier(item)))
        );
    }
}
