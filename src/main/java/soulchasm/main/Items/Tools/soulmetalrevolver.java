package soulchasm.main.Items.Tools;

import necesse.engine.Screen;
import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.bulletItem.BulletItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import necesse.level.maps.Level;
import soulchasm.main.Projectiles.WeaponProjectiles.soulrevolverprojectile;

import java.awt.*;

public class soulmetalrevolver extends GunProjectileToolItem {
    public soulmetalrevolver() {
        super(NORMAL_AMMO_TYPES, 1500);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(700);
        this.attackDamage.setBaseValue(160.0F).setUpgradedValue(1.0F, 115.0F);
        this.attackRange.setBaseValue(1200);
        this.velocity.setBaseValue(950);
        this.knockback.setBaseValue(75);
        this.attackXOffset = 16;
        this.attackYOffset = 16;
        this.addGlobalIngredient("bulletuser");
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulmetalrevolvertip"));
        return tooltips;
    }

    @Override
    protected void fireProjectiles(Level level, int x, int y, PlayerMob player, InventoryItem item, int seed, BulletItem bullet, boolean consumeAmmo, PacketReader contentReader) {
        int range;
        Projectile projectile;
        if (this.controlledRange) {
            Point newTarget = this.controlledRangePosition(new GameRandom(seed + 10), player, x, y, item, this.controlledMinRange, this.controlledInaccuracy);
            x = newTarget.x;
            y = newTarget.y;
            range = (int)player.getDistance((float)x, (float)y);
        } else {
            range = this.getAttackRange(item);
        }

        if(bullet != ItemRegistry.getItem("simplebullet")){
            projectile = this.getProjectile(item, bullet, player.x, player.y, (float)x, (float)y, range, player);
        }else {
            projectile = new soulrevolverprojectile(player.x, player.y, x, y, this.getFlatVelocity(item), range, this.getAttackDamage(item), this.getKnockback(item, player), player);
        }

        projectile.dropItem = consumeAmmo;
        projectile.getUniqueID(new GameRandom(seed));
        level.entityManager.projectiles.addHidden(projectile);
        if (this.moveDist != 0) {
            projectile.moveDist(this.moveDist);
        }

        if (level.isServer()) {
            level.getServer().network.sendToClientsAtExcept(new PacketSpawnProjectile(projectile), player.getServerClient(), player.getServerClient());
        }

    }

    public void playFireSound(AttackAnimMob mob) {
        Screen.playSound(GameResources.sniperrifle.setVolumeModifier((float)0.5), SoundEffect.effect(mob));
    }
}
