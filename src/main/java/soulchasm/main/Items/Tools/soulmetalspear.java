package soulchasm.main.Items.Tools;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
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
        this.enchantCost = 1200;
        this.animSpeed = 320;
        this.attackDamage = new GameDamage(DamageTypeRegistry.NORMAL, 65.0F);
        this.velocity = 250;
        this.rarity = Rarity.EPIC;
        this.setItemCategory("equipment", "weapons", "meleeweapons");
        this.setItemCategory(ItemCategory.equipmentManager, "weapons", "meleeweapons");
        this.stackSize = 1;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "soulmetalspeartip"));
        return tooltips;
    }

    public GameSprite getAttackSprite(InventoryItem item, PlayerMob player) {
        return null;
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        Projectile projectile = ProjectileRegistry.getProjectile("soulspearprojectile", level, player.x, player.y, (float)x, (float)y, (float)this.getThrowingVelocity(item, player), 1000, this.getDamage(item), this.getKnockback(item, player), player);
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
