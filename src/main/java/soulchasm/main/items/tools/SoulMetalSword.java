package soulchasm.main.items.tools;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.inventory.lootTable.presets.IncursionCloseRangeWeaponsLootTable;
import necesse.level.maps.Level;
import soulchasm.main.projectiles.WeaponProjectiles.SpiritSwordProjectile;

import java.awt.geom.Point2D;

public class SoulMetalSword extends SwordToolItem {
    public SoulMetalSword() {
        super(1400, IncursionCloseRangeWeaponsLootTable.incursionCloseRangeWeapons);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(350);
        this.attackDamage.setBaseValue(85.0F).setUpgradedValue(1.0F, 85.0F);
        this.attackRange.setBaseValue(90);
        this.knockback.setBaseValue(75);
        this.resilienceGain.setBaseValue(0.6F);
        this.attackXOffset = 8;
        this.attackYOffset = 8;
        this.canBeUsedForRaids = true;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulmetalswordtip"));
        return tooltips;
    }

    public void hitMob(InventoryItem item, ToolItemMobAbilityEvent event, Level level, Mob target, Mob attacker) {
        super.hitMob(item, event, level, target, attacker);
        for(int i = 0; i<3; i++){
            GameRandom random = new GameRandom();
            int randomAngle = random.getIntBetween(0, 360);
            Point2D.Float dir = GameMath.getAngleDir(randomAngle);
            SpiritSwordProjectile projectile = new SpiritSwordProjectile(attacker.getLevel(), target.x + dir.x * 120, target.y +  + dir.y * 120, target.x, target.y, 250 * random.getFloatBetween(0.7F, 1), 300, this.getAttackDamage(item).modDamage(0.33F), 10, attacker);
            projectile.resetUniqueID(random);
            projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
            attacker.getLevel().entityManager.projectiles.add(projectile);
            projectile.moveDist(10.0);
        }

    }
}
