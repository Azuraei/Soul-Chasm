package soulchasm.main.Items.Tools;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.GreatswordChargeLevel;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem.GreatswordToolItem;
import necesse.level.maps.Level;
import soulchasm.main.Misc.SoulScytheAttackHandler;

import java.awt.*;

public class SoulScythe extends GreatswordToolItem {
    public SoulScythe() {
        super(1950, SoulScythe.getScytheChargeLevels(300, 600, 900));
        this.rarity = Rarity.LEGENDARY;
        this.attackAnimTime.setBaseValue(300);
        this.attackDamage.setBaseValue(215.0F).setUpgradedValue(1.0F, 215.0F);
        this.attackRange.setBaseValue(110);
        this.knockback.setBaseValue(75);
        this.resilienceGain.setBaseValue(2.5F);
        this.attackXOffset = 6;
        this.attackYOffset = 6;
    }

    public static GreatswordChargeLevel[] getScytheChargeLevels(int level1Time, int level2Time, int level3Time) {
        return new GreatswordChargeLevel[]{new GreatswordChargeLevel(level1Time, 1.0F, new Color(208, 223, 255, 255)), new GreatswordChargeLevel(level2Time, 1.5F, new Color(84, 183, 255)), new GreatswordChargeLevel(level3Time, 2.0F, new Color(0, 130, 220))};
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent) {
        attackerMob.startAttackHandler(new SoulScytheAttackHandler(attackerMob, slot, item, this, seed, x, y, this.chargeLevels));
        return item;
    }

    @Override
    public float getAttackMovementMod(InventoryItem item) {
        return 0.9F;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulscythe"));
        return tooltips;
    }
}
