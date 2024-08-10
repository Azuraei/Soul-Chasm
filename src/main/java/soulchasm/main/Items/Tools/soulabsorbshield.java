package soulchasm.main.Items.Tools;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.Entity;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.ShieldTrinketItem;
import necesse.level.maps.Level;
import soulchasm.main.Projectiles.BossProjectiles.soulflamethrower;

import java.awt.*;

public class soulabsorbshield extends ShieldTrinketItem {
    public soulabsorbshield() {
        super(Rarity.EPIC, 5, 0.15F, 10000, 0.3F, 50, 180, 1400);

    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulabsorbshield"));
        return tooltips;
    }

    @Override
    public void onShieldHit(InventoryItem item, Mob mob, MobWasHitEvent hitEvent) {
        Attacker attacker = hitEvent.attacker;
        if (mob.isClient()) {
            PlayerMob me = mob.getLevel().getClient().getPlayer();
            if (mob == me) {
                this.playHitSound(item, mob, hitEvent);
            }
            if(attacker instanceof Mob || attacker instanceof Projectile){
                SoundManager.playSound(GameResources.firespell1, SoundEffect.effect(mob).volume(0.4F));
            }
        } else if (hitEvent.attacker instanceof Mob) {
            Mob attackOwner = (Mob)hitEvent.attacker;
            float knockbackModifier = attackOwner.getKnockbackModifier();
            if (knockbackModifier != 0.0F) {
                attackOwner.knockback(attackOwner.x - mob.x, attackOwner.y - mob.y, (float)this.knockback / knockbackModifier);
                attackOwner.sendMovementPacket(false);
            }
        }
        if(attacker != null && mob.getLevel().isServer() && attacker instanceof Mob || attacker instanceof Projectile){
            Level level = mob.getLevel();
            for(int i = -1; i<=1; i++){
                GameRandom random = new GameRandom();
                soulflamethrower projectile = new soulflamethrower(level, mob.x, mob.y, ((Entity) attacker).x, ((Entity) attacker).y, 250, new GameDamage((hitEvent.damage + 60) * 3).modDamage(0.33F), mob);
                projectile.resetUniqueID(random);
                projectile.setAngle(projectile.getAngle() + 10 * i);
                projectile.piercing = 10;
                projectile.moveDist(-10.0);
                mob.getLevel().entityManager.projectiles.add(projectile);
            }
        }
        mob.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("soulabsorbshieldbuff"), mob, 4F, null), false);
        this.drawBlockParticles(mob);
    }

    private void drawBlockParticles(Mob mob) {
        ParticleTypeSwitcher typeSwitcher = new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);
        int particleCount = 10;
        float anglePerParticle = 360.0F / (float)particleCount;
        for(int i = 0; i < particleCount; ++i) {
            int angle = (int)((float)i * anglePerParticle + GameRandom.globalRandom.nextFloat() * anglePerParticle);
            float dx = (float)Math.sin(Math.toRadians(angle)) * (float)GameRandom.globalRandom.getIntBetween(25, 50);
            float dy = (float)Math.cos(Math.toRadians(angle)) * (float)GameRandom.globalRandom.getIntBetween(25, 50);
            mob.getLevel().entityManager.addParticle(mob, typeSwitcher.next()).color(new Color(48, 123, 255)).movesFriction(dx, dy, 0.8F).sprite(GameResources.magicSparkParticles.sprite(GameRandom.globalRandom.nextInt(4), 0, 22)).sizeFades(30, 40).givesLight(180.0F, 200.0F).lifeTime(500);
        }

    }
}
