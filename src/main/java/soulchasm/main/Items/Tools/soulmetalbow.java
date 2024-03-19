package soulchasm.main.Items.Tools;

import necesse.engine.Screen;
import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.arrowItem.ArrowItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.main.Misc.Others.soulmetalbowattackhandler;

import java.awt.*;

public class soulmetalbow extends BowProjectileToolItem implements ItemInteractAction {
    public int attackSpriteStretch = 8;
    public Color particleColor = new Color(36, 138, 255);
    public soulmetalbow() {
        super(1400);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(250);
        this.attackDamage.setBaseValue(45.0F).setUpgradedValue(1.0F, 45.0F);
        this.attackRange.setBaseValue(600);
        this.velocity.setBaseValue(250).setUpgradedValue(1.0F, 250);
        this.attackXOffset = 12;
        this.attackYOffset = 28;
    }

    public boolean canLevelInteract(Level level, int x, int y, PlayerMob player, InventoryItem item) {
        boolean hasBuffs = player.buffManager.hasBuff("soulbowbuff") || player.buffManager.hasBuff("soulbowcooldownbuff") ;
        return !hasBuffs;
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int seed, PacketReader contentReader) {
        player.buffManager.addBuff(new ActiveBuff("soulbowbuff", player, 10F, null), false);
        item.getGndData().setBoolean("altFireCheck", true);
        return item;
    }

    private void exitAlternativeFire(InventoryItem item, PlayerMob player){
        item.getGndData().setBoolean("altFireActive", player.buffManager.hasBuff("soulbowbuff"));
        if(item.getGndData().getBoolean("altFireCheck") && !item.getGndData().getBoolean("altFireActive")){
            item.getGndData().setBoolean("altFireCheck", false);
            player.buffManager.addBuff(new ActiveBuff("soulbowcooldownbuff", player, 15F, null), false);
        }
    }

    public float getAttackMovementMod(InventoryItem item) {
        return item.getGndData().getBoolean("altFireActive") ? 0.3F : 0.5F;
    }

    public float getItemCooldownPercent(InventoryItem item, PlayerMob perspective) {
        return perspective.buffManager.getBuffDurationLeftSeconds(BuffRegistry.getBuff("soulbowcooldownbuff")) / 8.0F;
    }
    public void tickHolding(InventoryItem item, PlayerMob player) {
        super.tickHolding(item, player);
        exitAlternativeFire(item, player);
    }
    public boolean getConstantUse(InventoryItem item) {
        return true;
    }
    @Override
    public void showAttack(Level level, int x, int y, AttackAnimMob mob, int attackHeight, InventoryItem item, int seed, PacketReader contentReader) {
        if (level.isClient() && item.getGndData().getBoolean("shouldFire")) {
            Screen.playSound(GameResources.bow, SoundEffect.effect(mob));
        }
    }
    @Override
    public Projectile getProjectile(Level level, int x, int y, Mob owner, InventoryItem item, int seed, ArrowItem arrow, boolean consumeAmmo, PacketReader contentReader) {
        boolean altFire = item.getGndData().getBoolean("altFireActive");
        if(altFire){
            float percentCharge = item.getGndData().getFloat("chargePercent");
            percentCharge = GameMath.limit(percentCharge, 0.0F, 1.0F);
            float velocityMod;
            float rangeMod;
            float damageMod;
            float knockbackMod;
            if (percentCharge >= 1.0F) {
                velocityMod = 3.0F;
                rangeMod = 3.0F;
                damageMod = 8.0F;
                knockbackMod = 5.0F;
            } else {
                velocityMod = GameMath.lerp(percentCharge, 0.1F, 0.4F);
                rangeMod = GameMath.lerp(percentCharge, 0.05F, 0.4F);
                damageMod = GameMath.lerp(percentCharge, 0.05F, 0.4F);
                knockbackMod = GameMath.lerp(percentCharge, 0.05F, 0.2F);
            }
            GameDamage damage = arrow.modDamage(this.getAttackDamage(item)).modDamage(damageMod);
            float velocity = arrow.modVelocity((float)this.getProjectileVelocity(item, owner)) * velocityMod;
            float range = (float)arrow.modRange(this.getAttackRange(item)) * rangeMod;
            float knockback = (float)arrow.modKnockback(this.getKnockback(item, owner)) * knockbackMod;
            return ProjectileRegistry.getProjectile("soularrowprojectile", level, owner.x, owner.y, (float) x, (float) y, velocity, (int) range, damage, (int) knockback, owner);
        } else {
            float velocity = arrow.modVelocity((float)this.getProjectileVelocity(item, owner));
            int range = arrow.modRange(this.getAttackRange(item));
            GameDamage damage = arrow.modDamage(this.getAttackDamage(item));
            int knockback = arrow.modKnockback(this.getKnockback(item, owner));
            float resilienceGain = this.getResilienceGain(item);
            return this.getProjectile(level, x, y, owner, item, seed, arrow, consumeAmmo, velocity, range, damage, knockback, resilienceGain, contentReader);
        }
    }
    @Override
    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        boolean altFire = item.getGndData().getBoolean("altFireActive");
        if(altFire){
            int animTime = this.getAttackAnimTime(item, player) * 3;
            item.getGndData().setBoolean("shouldFire", false);
            player.startAttackHandler(new soulmetalbowattackhandler(player, slot, item, this, animTime, this.particleColor, seed));
            return item;
        } else {
            int arrowID = contentReader.getNextShortUnsigned();
            if (arrowID != 65535) {
                Item arrow = ItemRegistry.getItem(arrowID);
                if (arrow != null && arrow.type == Type.ARROW) {
                    GameRandom random = new GameRandom(seed + 5);
                    float ammoConsumeChance = ((ArrowItem)arrow).getAmmoConsumeChance() * this.getAmmoConsumeChance(player, item);
                    boolean consumeAmmo = ammoConsumeChance >= 1.0F || ammoConsumeChance > 0.0F && random.getChance(ammoConsumeChance);
                    if (!consumeAmmo || player.getInv().main.removeItems(level, player, arrow, 1, "arrowammo") >= 1) {
                        this.fireProjectiles(level, x, y, player, item, seed, (ArrowItem)arrow, consumeAmmo, contentReader);
                    }
                }
            }
            item.getGndData().setBoolean("shouldFire", true);
            return item;
        }
    }
    public void superOnAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        super.onAttack(level, x, y, player, attackHeight, item, slot, animAttack, seed, contentReader);
    }
    public boolean shouldRunOnAttackedBuffEvent(Level level, int x, int y, PlayerMob player, InventoryItem item, PlayerInventorySlot slot, int animTime, int seed, PacketReader contentReader) {
        return false;
    }
    public ItemAttackDrawOptions setupItemSpriteAttackDrawOptions(ItemAttackDrawOptions options, InventoryItem item, PlayerMob player, int mobDir, float attackDirX, float attackDirY, float attackProgress, Color itemColor, GameLight light) {
        float chargePercent = item.getGndData().getFloat("chargePercent");
        if (chargePercent > 0.0F) {
            chargePercent = Math.min(chargePercent, 1.0F);
            GameSprite attackSprite = this.getAttackSprite(item, player);
            int addedWidth = (int)(chargePercent * (float)this.attackSpriteStretch);
            attackSprite = new GameSprite(attackSprite, attackSprite.width + addedWidth, attackSprite.height);
            options.armPosOffset(-addedWidth + this.attackSpriteStretch / 2, 0);
            ItemAttackDrawOptions.AttackItemSprite itemSprite = options.itemSprite(attackSprite);
            itemSprite.itemRotatePoint(this.attackXOffset + addedWidth - this.attackSpriteStretch / 2, this.attackYOffset);
            if (itemColor != null) {
                itemSprite.itemColor(itemColor);
            }

            return itemSprite.itemEnd();
        } else {
            return super.setupItemSpriteAttackDrawOptions(options, item, player, mobDir, attackDirX, attackDirY, attackProgress, itemColor, light);
        }
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulmetalbowtip"));
        return tooltips;
    }
}
