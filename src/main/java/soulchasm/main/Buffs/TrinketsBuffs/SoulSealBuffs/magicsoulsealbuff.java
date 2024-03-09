package soulchasm.main.Buffs.TrinketsBuffs.SoulSealBuffs;

import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.level.maps.Level;
import soulchasm.main.Projectiles.SealProjectiles.soulmissileprojectile;

import java.awt.geom.Point2D;

public class magicsoulsealbuff extends TrinketBuff {

    public magicsoulsealbuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack) {
        super.onItemAttacked(buff, targetX, targetY, player, attackHeight, item, slot, animAttack);
        Level level = buff.owner.getLevel();
        if (level.isServer()) {
            if (item.item instanceof MagicProjectileToolItem) {
                if(!player.isManaExhausted) {
                    float velocity = 200.0F * player.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                    for(int i = 0; i < 3; i++){
                        GameRandom random = new GameRandom();
                        GameDamage finalDamage = ((MagicProjectileToolItem) item.item).getAttackDamage(item).modFinalMultiplier(0.6F);
                        Point2D.Float dir = GameMath.normalize(player.x - (float) targetX, player.y - (float) targetY);
                        int offsetDistance = random.getIntBetween(50, 100);
                        Point2D.Float offset = new Point2D.Float(dir.x * (float) offsetDistance, dir.y * (float) offsetDistance);
                        offset = GameMath.getPerpendicularPoint(offset, (float) random.getIntBetween(-50, 50), dir);
                        soulmissileprojectile projectile = new soulmissileprojectile(level, player.x + offset.x, player.y + offset.y, (float) targetX, (float) targetY, velocity, 800, finalDamage.modDamage(0.33F), 25, player);
                        level.entityManager.projectiles.add(projectile);
                    }
                }
            }
        }
    }
}


