package soulchasm.main.misc;

import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.attackHandler.GreatswordAttackHandler;
import necesse.entity.mobs.attackHandler.GreatswordChargeLevel;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.inventory.InventoryItem;
import necesse.inventory.enchants.ToolItemModifiers;
import necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem.GreatswordToolItem;
import soulchasm.main.projectiles.WeaponProjectiles.SoulScytheProjectile;
import soulchasm.main.projectiles.WeaponProjectiles.SoulScytheSmallProjectile;

import java.awt.geom.Point2D;

public class SoulScytheAttackHandler extends GreatswordAttackHandler {
    public SoulScytheAttackHandler(ItemAttackerMob attackerMob, ItemAttackSlot slot, InventoryItem item, GreatswordToolItem toolItem, int seed, int startX, int startY, GreatswordChargeLevel... chargeLevels) {
        super(attackerMob, slot, item, toolItem, seed, startX, startY, chargeLevels);
    }

    public void onEndAttack(boolean bySelf) {
        super.onEndAttack(bySelf);
        Point2D.Float dir = GameMath.getAngleDir(this.currentAngle);
        if (this.currentChargeLevel >= 1) {
            this.applyEffect();
        }
        if (this.currentChargeLevel == 2) {
            this.launchProjectile(dir, attackerMob.buffManager.hasBuff("meleesoulsealbuff"));
        }
    }

    private void launchProjectile(Point2D.Float dir, boolean buffMode) {
        float rangeMod = 4.5F;
        float velocity = 80.0F;
        float finalVelocity = (float)Math.round(this.toolItem.getEnchantment(this.item).applyModifierLimited(ToolItemModifiers.VELOCITY, ToolItemModifiers.VELOCITY.defaultBuffManagerValue) * velocity * (Float)this.attackerMob.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY));
        Projectile projectile = new SoulScytheProjectile(this.attackerMob.getLevel(),this.attackerMob, this.attackerMob.x, this.attackerMob.y, this.attackerMob.x + dir.x * 100.0F, this.attackerMob.y + dir.y * 100.0F, finalVelocity, (int)((float)this.toolItem.getAttackRange(this.item) * rangeMod), this.toolItem.getAttackDamage(this.item).modDamage(0.7F), 5);
        GameRandom random = new GameRandom(this.seed);
        projectile.setModifier(new ResilienceOnHitProjectileModifier(toolItem.getResilienceGain(item)));
        projectile.resetUniqueID(random);
        this.attackerMob.addAndSendAttackerProjectile(projectile, 60);
        if(buffMode){
            launchSmallerProjectiles(dir);
        }
    }
    private void launchSmallerProjectiles(Point2D.Float dir){
        for(int i = -1; i<=1; i++){
            if(i!=0){
                float rangeMod = 2.5F;
                float velocity = 60.0F;
                GameRandom random = new GameRandom(this.seed);
                Projectile projectile2 = new SoulScytheSmallProjectile(this.attackerMob.getLevel(),this.attackerMob, this.attackerMob.x, this.attackerMob.y, this.attackerMob.x + dir.x * 100.0F, this.attackerMob.y + dir.y * 100.0F, velocity, (int)((float)this.toolItem.getAttackRange(this.item) * rangeMod), this.toolItem.getAttackDamage(this.item).modDamage(0.3F), 5);
                projectile2.setAngle(projectile2.getAngleToTarget(this.attackerMob.x + dir.x * 100.0F, this.attackerMob.y + dir.y * 100.0F) + 45.0F * i);
                projectile2.speed = velocity;
                projectile2.resetUniqueID(random);
                this.attackerMob.addAndSendAttackerProjectile(projectile2, 40);
            }
        }
    }

    private void applyEffect() {
        attackerMob.buffManager.addBuff(new ActiveBuff("soulscythebuff", attackerMob, 1.5F, null), false);
    }
}
