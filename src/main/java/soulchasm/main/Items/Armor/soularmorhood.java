package soulchasm.main.Items.Armor;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;


public class soularmorhood extends SetHelmetArmorItem {
    public String headArmorBackTextureName;
    public GameTexture headArmorBackTexture;


    public soularmorhood() {
        super(24, 1500, "soularmorhood", "soularmorchestplate", "soularmorboots", "soularmorhoodsetbonus");
        this.rarity = Rarity.EPIC;

    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob) {
        return new ArmorModifiers(
                new ModifierValue(BuffModifiers.RANGED_CRIT_DAMAGE, 0.15F),
                new ModifierValue(BuffModifiers.RANGED_ATTACK_SPEED, 0.20F),
                new ModifierValue(BuffModifiers.ARROW_USAGE, -0.4F),
                new ModifierValue(BuffModifiers.BULLET_USAGE, -0.4F)
        );
    }

    public soularmorhood headArmorBackTexture(String headArmorBackTextureName) {
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
