package soulchasm.main.items.tools;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.modifiers.ResilienceOnHitProjectileModifier;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemControllerInteract;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.toolItem.projectileToolItem.magicProjectileToolItem.MagicProjectileToolItem;
import necesse.inventory.lootTable.presets.IncursionMagicWeaponsLootTable;
import necesse.level.maps.Level;
import soulchasm.main.projectiles.weaponprojectiles.BookofSoulsProjectile;
import soulchasm.main.projectiles.weaponprojectiles.BookofSoulsSmallProjectile;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;
public class BookofSouls extends MagicProjectileToolItem implements ItemInteractAction {
    public BookofSouls() {
        super(1400, IncursionMagicWeaponsLootTable.incursionMagicWeapons);
        this.rarity = Rarity.EPIC;
        this.attackAnimTime.setBaseValue(600);
        this.attackDamage.setBaseValue(165.0F).setUpgradedValue(1.0F, 165.0F);
        this.attackRange.setBaseValue(500);
        this.velocity.setBaseValue(200).setUpgradedValue(1.0F, 200);
        this.knockback.setBaseValue(30);
        this.attackXOffset = 10;
        this.attackYOffset = 10;
        this.manaCost.setBaseValue(4.0F);
        this.attackCooldownTime.setBaseValue(500);
        this.canBeUsedForRaids = false;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bookofsoulstip"), 400);
        tooltips.add(Localization.translate("itemtooltip", "bookofsoulssecondarytip"), 400);
        return tooltips;
    }
    public boolean getConstantUse(InventoryItem item) {
        return true;
    }
    public boolean animDrawBehindHand(InventoryItem item) {
        return true;
    }

    private void energyCalculations(PlayerMob player, GNDItemMap gndData){
        float energy = gndData.getFloat("energy");
        energy = GameMath.limit(energy, 0.0F, 1.0F);
        gndData.setFloat("energy", energy);
        player.buffManager.getBuff("bookofsoulbuff").getGndData().setFloat("energy", energy);
    }
    public void tickHolding(InventoryItem item, PlayerMob player) {
        GNDItemMap gndData = item.getGndData();
        float currentEnergy = gndData.getFloat("energy");
        player.buffManager.addBuff(new ActiveBuff("bookofsoulbuff", player, 0.2F, null), false);
        energyCalculations(player,gndData);
        if(gndData.getBoolean("altFireActive")){
            if(currentEnergy > 0){
                gndData.setFloat("energy",(float)(currentEnergy - 0.0005));
                player.buffManager.addBuff(new ActiveBuff("soulofsoulsoverchargebuff", player, 0.2F, null), false);
            } else {
                if(player.getLevel().isClient()){
                    SoundManager.playSound(GameResources.fadedeath1, SoundEffect.effect(player).volume(1.0F).pitch(0.6F));
                }
                gndData.setBoolean("altFireActive", false);
            }
        }
        super.tickHolding(item, player);
    }

    @Override
    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent) {
        super.showAttack(level, x, y, attackerMob, attackHeight, item, animAttack, seed, mapContent);
        if (level.isClient()) {
            SoundManager.playSound(GameResources.magicbolt2, SoundEffect.effect(attackerMob).volume(0.3F).pitch(GameRandom.globalRandom.getFloatBetween(1.5F, 1.6F)));
        }
    }

    @Override
    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
        GNDItemMap gndData = item.getGndData();
        float currentEnergy = item.getGndData().getFloat("energy");
        if(!gndData.getBoolean("altFireActive") && !attackerMob.buffManager.hasBuff("soulofsoulsoverchargebuff")){
            item.getGndData().setFloat("energy", (float)(currentEnergy + 0.025 * this.getAttackDamage(item).finalDamageMultiplier));
            BookofSoulsProjectile projectile = new BookofSoulsProjectile(level, attackerMob, attackerMob.x, attackerMob.y, (float)x, (float)y, this.getProjectileVelocity(item, attackerMob), 1000, this.getAttackDamage(item), this.getKnockback(item, attackerMob));
            projectile.setModifier(new ResilienceOnHitProjectileModifier(this.getResilienceGain(item)));
            projectile.resetUniqueID(new GameRandom(seed));
            attackerMob.addAndSendAttackerProjectile(projectile, 30);
        } else {
            item.getGndData().setFloat("energy",(float)(currentEnergy - 0.03));
            float angle = GameMath.getAngle(new Point2D.Float(attackerMob.dx, attackerMob.dy));
            float randomAngleOffset = 80.0F;
            int projectiles = 4;
            for(int i = 0; i < projectiles; ++i) {
                Point2D.Float dir = GameMath.getAngleDir(angle + GameRandom.globalRandom.getFloatBetween(-randomAngleOffset, randomAngleOffset));
                BookofSoulsSmallProjectile projectile = new BookofSoulsSmallProjectile(level, attackerMob, attackerMob.x, attackerMob.y, (float)x + dir.x * 80.0F, (float)y + dir.y * 80.0F, this.getProjectileVelocity(item, attackerMob), 800, this.getAttackDamage(item).modDamage(0.3F), 5);
                projectile.resetUniqueID(new GameRandom(seed));
                projectile.resetUniqueID(new GameRandom(seed));
                attackerMob.addAndSendAttackerProjectile(projectile, 30);
            }
        }
        this.consumeMana(attackerMob, item);
        return item;
    }


    public boolean canLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item) {
        return true;
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int seed, GNDItemMap mapContent) {
        GNDItemMap gndData = item.getGndData();
        boolean altFire = gndData.getBoolean("altFireActive");
        gndData.setBoolean("altFireActive", !altFire);
        if (level.isClient()) {
            if(altFire){
                SoundManager.playSound(GameResources.fadedeath1, SoundEffect.effect(attackerMob).volume(1.0F).pitch(0.6F));
            } else {
                SoundManager.playSound(GameResources.fadedeath1, SoundEffect.effect(attackerMob).volume(1.0F).pitch(1.2F));
            }
        }
        return item;
    }

    public ItemControllerInteract getControllerInteract(Level level, PlayerMob player, InventoryItem item, boolean beforeObjectInteract, int interactDir, LinkedList<Rectangle> mobInteractBoxes, LinkedList<Rectangle> tileInteractBoxes) {
        Point2D.Float controllerAimDir = player.getControllerAimDir();
        Point levelPos = this.getControllerAttackLevelPos(level, controllerAimDir.x, controllerAimDir.y, player, item);
        return new ItemControllerInteract(levelPos.x, levelPos.y) {
            public DrawOptions getDrawOptions(GameCamera camera) {
                return null;
            }

            public void onCurrentlyFocused(GameCamera camera) {
            }
        };
    }
}
