package soulchasm.main.buffs.trinketbuffs.SoulSealBuffs;

import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.level.maps.Level;
import soulchasm.main.projectiles.SealProjectiles.SoulMissileProjectile;

import java.awt.geom.Point2D;

public class MagicSoulSealBuff extends TrinketBuff {

    public MagicSoulSealBuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, GNDItemMap attackMap) {
        super.onItemAttacked(buff, targetX, targetY, attackerMob, attackHeight, item, slot, animAttack, attackMap);
        Level level = buff.owner.getLevel();
        if (level.isServer()) {
            if (item.item instanceof MagicProjectileToolItem) {
                if(!attackerMob.isManaExhausted) {
                    float velocity = 200.0F * attackerMob.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                    for(int i = 0; i < 3; i++){
                        GameRandom random = new GameRandom();
                        GameDamage finalDamage = ((MagicProjectileToolItem) item.item).getAttackDamage(item).modFinalMultiplier(0.6F);
                        Point2D.Float dir = GameMath.normalize(attackerMob.x - (float) targetX, attackerMob.y - (float) targetY);
                        int offsetDistance = random.getIntBetween(50, 100);
                        Point2D.Float offset = new Point2D.Float(dir.x * (float) offsetDistance, dir.y * (float) offsetDistance);
                        offset = GameMath.getPerpendicularPoint(offset, (float) random.getIntBetween(-50, 50), dir);
                        SoulMissileProjectile projectile = new SoulMissileProjectile(level, attackerMob.x + offset.x, attackerMob.y + offset.y, (float) targetX, (float) targetY, velocity, 800, finalDamage.modDamage(0.33F), 25, attackerMob);
                        level.entityManager.projectiles.add(projectile);
                    }
                }
            }
        }
    }
}


