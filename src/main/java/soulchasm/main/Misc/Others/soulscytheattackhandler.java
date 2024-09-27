package soulchasm.main.Misc.Others;

import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.GreatswordAttackHandler;
import necesse.entity.mobs.attackHandler.GreatswordChargeLevel;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.enchants.ToolItemModifiers;
import soulchasm.main.Items.Tools.soulscythe;
import soulchasm.main.Projectiles.WeaponProjectiles.soulscytheprojectile;
import soulchasm.main.Projectiles.WeaponProjectiles.soulscythesmallprojectile;

import java.awt.geom.Point2D;

public class soulscytheattackhandler extends GreatswordAttackHandler {
    public soulscytheattackhandler(PlayerMob player, PlayerInventorySlot slot, InventoryItem item, soulscythe toolItem, int seed, int startX, int startY, GreatswordChargeLevel... chargeLevels) {
        super(player, slot, item, toolItem, seed, startX, startY, chargeLevels);
    }

    public void onEndAttack(boolean bySelf) {
        super.onEndAttack(bySelf);
        Point2D.Float dir = GameMath.getAngleDir(this.currentAngle);
        if (this.currentChargeLevel >= 1) {
            this.applyEffect();
        }
        if (this.currentChargeLevel == 2) {
            this.launchProjectile(dir, player.buffManager.hasBuff("meleesoulsealbuff"));
        }
    }

    private void launchProjectile(Point2D.Float dir, boolean buffMode) {
        float rangeMod = 4.5F;
        float velocity = 80.0F;
        float finalVelocity = (float)Math.round(this.toolItem.getEnchantment(this.item).applyModifierLimited(ToolItemModifiers.VELOCITY, ToolItemModifiers.VELOCITY.defaultBuffManagerValue) * velocity * (Float)this.player.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY));
        Projectile projectile = new soulscytheprojectile(this.player.getLevel(),this.player, this.player.x, this.player.y, this.player.x + dir.x * 100.0F, this.player.y + dir.y * 100.0F, finalVelocity, (int)((float)this.toolItem.getAttackRange(this.item) * rangeMod), this.toolItem.getAttackDamage(this.item).modDamage(0.7F), 5);
        GameRandom random = new GameRandom(this.seed);
        projectile.resetUniqueID(random);
        projectile.setModifier(new ResilienceOnHitProjectileModifier(toolItem.getResilienceGain(item)));
        this.player.getLevel().entityManager.projectiles.add(projectile);
        projectile.moveDist(60.0);
        if (this.player.isServer()) {
            this.player.getLevel().getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile), projectile, this.player.getServerClient());
        }
        if(buffMode){
            launchSmallerProjectiles(dir);
        }
    }
    private void launchSmallerProjectiles(Point2D.Float dir){
        for(int i = -1; i<=1; i++){
            if(i!=0){
                float rangeMod = 2.5F;
                float velocity = 60.0F;
                Projectile projectile2 = new soulscythesmallprojectile(this.player.getLevel(),this.player, this.player.x, this.player.y, this.player.x + dir.x * 100.0F, this.player.y + dir.y * 100.0F, velocity, (int)((float)this.toolItem.getAttackRange(this.item) * rangeMod), this.toolItem.getAttackDamage(this.item).modDamage(0.3F), 5);
                GameRandom random = new GameRandom(this.seed);
                projectile2.resetUniqueID(random);
                projectile2.moveDist(40.0);
                projectile2.setAngle(projectile2.getAngleToTarget(this.player.x + dir.x * 100.0F, this.player.y + dir.y * 100.0F) + 45.0F * i);
                projectile2.speed = velocity;
                this.player.getLevel().entityManager.projectiles.add(projectile2);
                if (this.player.isServer()) {
                    this.player.getLevel().getServer().network.sendToClientsWithEntityExcept(new PacketSpawnProjectile(projectile2), projectile2, this.player.getServerClient());
                }
            }
        }
    }

    private void applyEffect() {
        player.buffManager.addBuff(new ActiveBuff("soulscythebuff", player, 1.5F, null), false);
    }
}
