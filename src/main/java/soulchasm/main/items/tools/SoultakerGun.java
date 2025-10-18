package soulchasm.main.items.tools;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.bulletItem.BulletItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import necesse.inventory.lootTable.presets.IncursionGunWeaponsLootTable;
import necesse.level.maps.Level;
import soulchasm.main.projectiles.weaponprojectiles.SoulRevolverProjectile;

import java.awt.*;

public class SoultakerGun extends GunProjectileToolItem {
    public SoultakerGun() {
        super(NORMAL_AMMO_TYPES, 1400, IncursionGunWeaponsLootTable.incursionGunWeapons);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(700);
        this.attackDamage.setBaseValue(135.0F).setUpgradedValue(1.0F, 135.0F);
        this.attackRange.setBaseValue(1200);
        this.velocity.setBaseValue(950);
        this.knockback.setBaseValue(75);
        this.attackXOffset = 16;
        this.attackYOffset = 16;
        this.addGlobalIngredient("bulletuser");
        this.canBeUsedForRaids = true;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulmetalrevolvertip"), 500);
        return tooltips;
    }

    @Override
    protected void fireProjectiles(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item, int seed, BulletItem bullet, boolean dropItem, GNDItemMap mapContent) {
        int range;
        if (this.controlledRange) {
            Point newTarget = this.controlledRangePosition(new GameRandom(seed + 10), attackerMob, x, y, item, this.controlledMinRange, this.controlledInaccuracy);
            x = newTarget.x;
            y = newTarget.y;
            range = (int)attackerMob.getDistance((float)x, (float)y);
        } else {
            range = this.getAttackRange(item);
        }

        Projectile projectile;
        if(bullet != ItemRegistry.getItem("simplebullet")){
            projectile = this.getProjectile(item, bullet, attackerMob.x, attackerMob.y, (float)x, (float)y, range, attackerMob);
        } else {
            projectile = new SoulRevolverProjectile(attackerMob.x, attackerMob.y, x, y, this.getFlatVelocity(item), range, this.getAttackDamage(item), this.getKnockback(item, attackerMob), attackerMob);
        }
        projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
        projectile.dropItem = dropItem;
        projectile.getUniqueID(new GameRandom(seed));
        attackerMob.addAndSendAttackerProjectile(projectile, this.moveDist);
    }
    @Override
    protected void playAttackSound(Mob source) {
        SoundManager.playSound(GameResources.sniperrifle.setVolumeModifier((float)0.4), SoundEffect.effect(source));
    }
}
