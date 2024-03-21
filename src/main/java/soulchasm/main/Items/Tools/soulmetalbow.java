package soulchasm.main.Items.Tools;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.projectile.Projectile;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.arrowItem.ArrowItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.level.maps.Level;
import soulchasm.main.Projectiles.SealProjectiles.soularrowprojectile;

import java.util.Random;

public class soulmetalbow extends BowProjectileToolItem implements ItemInteractAction {
    public soulmetalbow() {
        super(1400);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(250);
        this.attackDamage.setBaseValue(50.0F).setUpgradedValue(1.0F, 50.0F);
        this.attackRange.setBaseValue(600);
        this.velocity.setBaseValue(250).setUpgradedValue(1.0F, 250);
        this.attackXOffset = 12;
        this.attackYOffset = 28;
    }

    public boolean canLevelInteract(Level level, int x, int y, PlayerMob player, InventoryItem item) {
        boolean hasBuffs = player.buffManager.hasBuff("soulbowbuff") || player.buffManager.hasBuff("soulbowcooldownbuff") ;
        return !hasBuffs;
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int seed, PacketReader contentReader) {
        float duration = 5;
        player.buffManager.addBuff(new ActiveBuff("soulbowbuff", player, duration, null), false);
        player.buffManager.addBuff(new ActiveBuff("soulbowcooldownbuff", player, duration*4, null), false);
        return item;
    }

    public float getItemCooldownPercent(InventoryItem item, PlayerMob perspective) {
        return perspective.buffManager.getBuffDurationLeftSeconds(BuffRegistry.getBuff("soulbowcooldownbuff")) / 8.0F;
    }
    @Override
    public Projectile getProjectile(Level level, int x, int y, Mob owner, InventoryItem item, int seed, ArrowItem arrow, boolean consumeAmmo, PacketReader contentReader) {
        boolean altFire = owner.buffManager.hasBuff("soulbowbuff");
        float velocity = arrow.modVelocity((float)this.getProjectileVelocity(item, owner));
        int range = arrow.modRange(this.getAttackRange(item));
        GameDamage damage = arrow.modDamage(this.getAttackDamage(item));
        int knockback = arrow.modKnockback(this.getKnockback(item, owner));
        float resilienceGain = this.getResilienceGain(item);
        if (altFire) {
            int randomPosX = GameRandom.getIntBetween(new Random(), -20, 20);
            int randomPosY = GameRandom.getIntBetween(new Random(), -20, 20);
            return new soularrowprojectile(level, owner, owner.x + randomPosX, owner.y + randomPosY, (float) x + randomPosX * 1.5F, (float) y + randomPosY * 1.5F, velocity, range, damage, knockback);
        }  else {
            return this.getProjectile(level, x, y, owner, item, seed, arrow, consumeAmmo, velocity, range, damage, knockback, resilienceGain, contentReader);
        }
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulmetalbowtip"),400);
        return tooltips;
    }
}
