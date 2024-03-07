package soulchasm.main.Misc;

import necesse.engine.network.packet.PacketSpawnProjectile;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.GreatswordAttackHandler;
import necesse.entity.mobs.attackHandler.GreatswordChargeLevel;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.projectile.Projectile;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.enchants.ToolItemModifiers;
import soulchasm.main.Items.Tools.soulscythe;
import soulchasm.main.Projectiles.WeaponProjectiles.soulscytheprojectile;

import java.awt.geom.Point2D;

public class soulscytheattackhandler extends GreatswordAttackHandler {
    public soulscytheattackhandler(PlayerMob player, PlayerInventorySlot slot, InventoryItem item, soulscythe toolItem, int seed, int startX, int startY, GreatswordChargeLevel... chargeLevels) {
        super(player, slot, item, toolItem, seed, startX, startY, chargeLevels);
    }

    public void onEndAttack(boolean bySelf) {
        super.onEndAttack(bySelf);
        Point2D.Float dir = GameMath.getAngleDir(this.currentAngle);
        if (this.currentChargeLevel == 2) {
            this.launchProjectile(dir);
            this.applyEffect();
        }
    }

    private void launchProjectile(Point2D.Float dir) {
        float rangeMod = 10.0F;
        float velocity = 160.0F;
        float finalVelocity = (float)Math.round(this.toolItem.getEnchantment(this.item).applyModifierLimited(ToolItemModifiers.VELOCITY, ToolItemModifiers.VELOCITY.defaultBuffManagerValue) * velocity * (Float)this.player.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY));
        Projectile projectile = new soulscytheprojectile(this.player.getLevel(),this.player, this.player.x, this.player.y, this.player.x + dir.x * 100.0F, this.player.y + dir.y * 100.0F, finalVelocity, (int)((float)this.toolItem.getAttackRange(this.item) * rangeMod), this.toolItem.getAttackDamage(this.item), this.toolItem.getKnockback(this.item, player));
        GameRandom random = new GameRandom(this.seed);
        projectile.resetUniqueID(random);
        this.player.getLevel().entityManager.projectiles.add(projectile);
        projectile.moveDist(20.0);
        if (this.player.isServer()) {
            this.player.getLevel().getServer().network.sendToClientsAtExcept(new PacketSpawnProjectile(projectile), this.player.getServerClient(), this.player.getServerClient());
        }

    }
    private void applyEffect() {
        player.buffManager.addBuff(new ActiveBuff("soulscythebuff", player, 0.5F, null), false);
    }
}
