package soulchasm.main.Items.Tools;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.ThrowToolItem;
import necesse.level.maps.Level;

public class soulmetalspear extends ThrowToolItem {
    public soulmetalspear() {
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(320);
        this.attackDamage.setBaseValue(130.0F).setUpgradedValue(1.0F, 130.0F);
        this.velocity.setBaseValue(250).setUpgradedValue(1.0F, 250);
        this.knockback.setBaseValue(75);
        this.resilienceGain.setBaseValue(1.0F);
        this.enchantCost = 1400;
        this.attackXOffset = 8;
        this.attackYOffset = 8;
        this.setItemCategory("equipment", "weapons", "meleeweapons");
        this.setItemCategory(ItemCategory.equipmentManager, "weapons", "meleeweapons");
        this.stackSize = 1;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulmetalspeartip"));
        return tooltips;
    }

    public GameSprite getAttackSprite(InventoryItem item, PlayerMob player) {
        return null;
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        Projectile projectile = ProjectileRegistry.getProjectile("soulspearprojectile", level, player.x, player.y, (float)x, (float)y, (float)this.getThrowingVelocity(item, player), 1000, this.getAttackDamage(item), this.getKnockback(item, player), player);
        projectile.resetUniqueID(new GameRandom(seed));
        projectile.moveDist(30.0);
        level.entityManager.projectiles.addHidden(projectile);
        if (level.isServer()) {
            level.getServer().network.sendToClientsAtExcept(new PacketSpawnProjectile(projectile), player.getServerClient(), player.getServerClient());
        }
        return item;
    }

    public boolean isEnchantable(InventoryItem item) {
        return true;
    }
}
