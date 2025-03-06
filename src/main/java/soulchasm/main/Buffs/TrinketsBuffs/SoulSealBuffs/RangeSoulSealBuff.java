package soulchasm.main.Buffs.TrinketsBuffs.SoulSealBuffs;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import necesse.level.maps.Level;
import soulchasm.main.Projectiles.SealProjectiles.SoulArrowProjectile;
import soulchasm.main.Projectiles.WeaponProjectiles.SoulBigBulletProjectile;

public class RangeSoulSealBuff extends TrinketBuff {
    public RangeSoulSealBuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.getGndData().getInt("currentcharge", 0);
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack) {
        super.onItemAttacked(buff, targetX, targetY, attackerMob, attackHeight, item, slot, animAttack);
        Level level = buff.owner.getLevel();
        int currentcharge = buff.getGndData().getInt("currentcharge");
        int chargesToExplosiveBullet = 4;
        if (level.isServer()) {
            if (item.item instanceof BowProjectileToolItem) {
                GameDamage finalDamage = ((BowProjectileToolItem) item.item).getAttackDamage(item).modFinalMultiplier(0.6F);
                float velocity = ((BowProjectileToolItem) item.item).getProjectileVelocity(item, attackerMob);
                SoulArrowProjectile projectile = new SoulArrowProjectile(level, attackerMob, attackerMob.x, attackerMob.y, (float) targetX, (float) targetY, velocity, 800, finalDamage, 20);
                projectile.moveDist(10);
                level.entityManager.projectiles.add(projectile);
            }else if (item.item instanceof GunProjectileToolItem) {
                if(currentcharge>=chargesToExplosiveBullet){
                    buff.getGndData().setInt("currentcharge", 0);
                    float velocity = ((GunProjectileToolItem) item.item).getProjectileVelocity(item, attackerMob) * 2.0F;
                    GameDamage finalDamage = ((GunProjectileToolItem) item.item).getAttackDamage(item).modFinalMultiplier(1.5F);
                    SoulBigBulletProjectile projectile = new SoulBigBulletProjectile(attackerMob.x, attackerMob.y, (float) targetX, (float) targetY, velocity, 1200, finalDamage, 20, attackerMob);
                    level.entityManager.projectiles.add(projectile);
                }else {
                    buff.getGndData().setInt("currentcharge", currentcharge + 1);
                }
            }
        }
    }
}


