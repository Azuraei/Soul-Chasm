package soulchasm.main.Items.Armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;


public class soularmorhelmet extends SetHelmetArmorItem {
    public String headArmorBackTextureName;
    public GameTexture headArmorBackTexture;


    public soularmorhelmet() {
        super(30, DamageTypeRegistry.MELEE,1500, "soularmorhelmet", "soularmorchestplate", "soularmorboots", "soularmorhelmetsetbonus");
        this.rarity = Rarity.EPIC;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(
                new ModifierValue(BuffModifiers.MELEE_CRIT_DAMAGE, 0.15F),
                new ModifierValue(BuffModifiers.MELEE_ATTACK_SPEED, 0.2F)
        );
    }

    public soularmorhelmet headArmorBackTexture(String headArmorBackTextureName) {
        this.headArmorBackTextureName = headArmorBackTextureName;
        return this;
    }

    protected void loadArmorTexture() {
        super.loadArmorTexture();
        if (this.headArmorBackTextureName != null) {
            this.headArmorBackTexture = GameTexture.fromFile("player/armor/" + this.headArmorBackTextureName);
        }

    }
}
