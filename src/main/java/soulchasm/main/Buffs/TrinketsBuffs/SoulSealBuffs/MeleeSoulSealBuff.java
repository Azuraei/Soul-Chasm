package soulchasm.main.Buffs.TrinketsBuffs.SoulSealBuffs;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.boomerangToolItem.BoomerangToolItem;
import necesse.inventory.item.toolItem.spearToolItem.SpearToolItem;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.level.maps.Level;
import soulchasm.main.Items.Tools.SoulScythe;
import soulchasm.main.Projectiles.SealProjectiles.SoulBoomerangProjectile;
import soulchasm.main.Projectiles.SealProjectiles.SoulPointyWaveProjectile;
import soulchasm.main.Projectiles.SealProjectiles.SoulWaveProjectile;

public class MeleeSoulSealBuff extends TrinketBuff {

    public MeleeSoulSealBuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {

    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack) {
        super.onItemAttacked(buff, targetX, targetY, attackerMob, attackHeight, item, slot, animAttack);
        Level level = buff.owner.getLevel();
        if (level.isServer()) {
            if (item.item instanceof SwordToolItem) {
                if(!(item.item instanceof SoulScythe)){
                    GameDamage finalDamage = ((SwordToolItem) item.item).getAttackDamage(item).modDamage(0.6F);
                    float velocity = 150.0F * attackerMob.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                    SoulWaveProjectile projectile = new SoulWaveProjectile(level, attackerMob.x, attackerMob.y, (float) targetX, (float) targetY, velocity, 800, finalDamage, attackerMob);
                    attackerMob.addAndSendAttackerProjectile(projectile);
                }
            } else if (item.item instanceof SpearToolItem) {
                GameDamage finalDamage = ((SpearToolItem) item.item).getAttackDamage(item).modDamage(0.6F);
                float velocity = 300.0F * attackerMob.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                SoulPointyWaveProjectile projectile = new SoulPointyWaveProjectile(level, attackerMob.x, attackerMob.y, (float) targetX, (float) targetY, velocity, 800, finalDamage, attackerMob);
                attackerMob.addAndSendAttackerProjectile(projectile);
            } else if (item.item instanceof BoomerangToolItem) {
                GameDamage finalDamage = ((BoomerangToolItem) item.item).getAttackDamage(item).modDamage(0.3F);
                float velocity = ((BoomerangToolItem) item.item).getFlatVelocity(item) * attackerMob.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                for(int i = -1; i <= 1; i++){
                    if(i!=0){
                        SoulBoomerangProjectile projectile = new SoulBoomerangProjectile(level, attackerMob.x, attackerMob.y, (float) targetX, (float) targetY, velocity, 650, finalDamage, attackerMob);
                        float angle = projectile.getAngleToTarget((float) targetX, (float) targetY);
                        projectile.setAngle(angle + 10 * i);
                        attackerMob.addAndSendAttackerProjectile(projectile);
                    }
                }

            }
        }
    }
}


