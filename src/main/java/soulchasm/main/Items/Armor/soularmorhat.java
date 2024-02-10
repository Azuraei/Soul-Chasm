package soulchasm.main.Items.Armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;


public class soularmorhat extends SetHelmetArmorItem {
    public String headArmorBackTextureName;
    public GameTexture headArmorBackTexture;


    public soularmorhat() {
        super(24, 1500, "soularmorhat", "soularmorchestplate", "soularmorboots", "soularmorhatsetbonus");
        this.rarity = Rarity.EPIC;

    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(
                new ModifierValue(BuffModifiers.MAGIC_CRIT_DAMAGE, 0.15F),
                new ModifierValue(BuffModifiers.MAGIC_ATTACK_SPEED, 0.20F),
                new ModifierValue(BuffModifiers.COMBAT_MANA_REGEN, 1.5F)
        );
    }

    public soularmorhat headArmorBackTexture(String headArmorBackTextureName) {
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
