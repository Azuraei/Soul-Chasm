package soulchasm.main.Items.Tools;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem.GreatswordToolItem;
import necesse.level.maps.Level;
import soulchasm.main.Misc.soulscytheattackhandler;

public class soulscythe extends GreatswordToolItem {
    public soulscythe() {
        super(1200, GreatswordToolItem.getThreeChargeLevels(400, 700, 900));
        this.rarity = Rarity.LEGENDARY;
        this.attackAnimTime.setBaseValue(300);
        this.attackDamage.setBaseValue(250.0F).setUpgradedValue(1.0F, 175.0F);
        this.attackRange.setBaseValue(110);
        this.knockback.setBaseValue(75);
        this.resilienceGain.setBaseValue(1.0F);
        this.attackXOffset = 6;
        this.attackYOffset = 6;
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        player.startAttackHandler(new soulscytheattackhandler(player, slot, item, this, seed, x, y, this.chargeLevels));
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
