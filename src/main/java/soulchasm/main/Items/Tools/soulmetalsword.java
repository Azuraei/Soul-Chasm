package soulchasm.main.Items.Tools;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.toolItemEvent.ToolItemEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.level.maps.Level;
import soulchasm.main.Projectiles.WeaponProjectiles.spiritswordprojectile;

import java.awt.geom.Point2D;

public class soulmetalsword extends SwordToolItem {
    public soulmetalsword() {
        super(1400);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(350);
        this.attackDamage.setBaseValue(85.0F).setUpgradedValue(1.0F, 85.0F);
        this.attackRange.setBaseValue(90);
        this.knockback.setBaseValue(75);
        this.resilienceGain.setBaseValue(2.0F);
        this.attackXOffset = 8;
        this.attackYOffset = 8;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulmetalswordtip"));
        return tooltips;
    }

    public void hitMob(InventoryItem item, ToolItemEvent event, Level level, Mob target, Mob attacker) {
        super.hitMob(item, event, level, target, attacker);
        for(int i = 0; i<3; i++){
            GameRandom random = new GameRandom();
            int randomAngle = random.getIntBetween(0, 360);
            Point2D.Float dir = GameMath.getAngleDir(randomAngle);
            spiritswordprojectile projectile = new spiritswordprojectile(attacker.getLevel(), target.x + dir.x * 120, target.y +  + dir.y * 120, target.x, target.y, 250 * random.getFloatBetween(0.7F, 1), 300, this.getAttackDamage(item).modDamage(0.33F), 10, attacker);
            projectile.resetUniqueID(random);
            attacker.getLevel().entityManager.projectiles.add(projectile);
            projectile.moveDist(10.0);
        }

    }
}
