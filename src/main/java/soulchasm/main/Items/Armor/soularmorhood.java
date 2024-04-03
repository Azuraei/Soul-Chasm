package soulchasm.main.Items.Armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;


public class soularmorhood extends SetHelmetArmorItem {
    public soularmorhood() {
        super(24, DamageTypeRegistry.RANGED,1500, "soularmorhood", "soularmorchestplate", "soularmorboots", "soularmorhoodsetbonus");
        this.rarity = Rarity.EPIC;
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
