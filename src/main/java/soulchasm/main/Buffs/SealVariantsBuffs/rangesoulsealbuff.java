package soulchasm.main.Buffs.SealVariantsBuffs;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.ItemCooldown;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.boomerangToolItem.BoomerangToolItem;
import necesse.inventory.item.toolItem.spearToolItem.SpearToolItem;
import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;
import necesse.level.maps.Level;
import soulchasm.main.Projectiles.*;

public class rangesoulsealbuff extends TrinketBuff {
    private int currentcharge;
    public int chargesToExplosiveBullet;
    public rangesoulsealbuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        this.currentcharge=0;
        this.chargesToExplosiveBullet=4;
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack) {
        super.onItemAttacked(buff, targetX, targetY, player, attackHeight, item, slot, animAttack);
        Level level = buff.owner.getLevel();
        if (level.isServer()) {
            if (item.item instanceof BowProjectileToolItem) {
                GameDamage finalDamage = ((BowProjectileToolItem) item.item).getAttackDamage(item).modFinalMultiplier(0.6F);
                float velocity = 200.0F * player.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                soularrowprojectile projectile = new soularrowprojectile(level, player, player.x, player.y, (float) targetX, (float) targetY, velocity, 800, finalDamage, 20);
                projectile.moveDist(10);
                level.entityManager.projectiles.add(projectile);
            }else if (item.item instanceof GunProjectileToolItem) {
                if(currentcharge>=chargesToExplosiveBullet){
                    currentcharge=0;
                    float velocity = ((GunProjectileToolItem) item.item).velocity * 2.0F;
                    GameDamage finalDamage = ((GunProjectileToolItem) item.item).getAttackDamage(item).modFinalMultiplier(1.5F);
                    soulbigbulletprojectile projectile = new soulbigbulletprojectile(player.x, player.y, (float) targetX, (float) targetY, velocity, 1200, finalDamage, 20, player);
                    level.entityManager.projectiles.add(projectile);
                }else {
                    currentcharge++;
                }
            }

        }
    }
}


