package soulchasm.main.Items.Trinkets.SealTrinkets;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;


public class SoulSealTrinket extends TrinketItem {

    public SoulSealTrinket() {
        super(Rarity.LEGENDARY, 1200);
        this.disabledBy.add("soulsealtrinket");
        this.disabledBy.add("balancedsealtrinket");
    }
    public TrinketBuff[] getBuffs(InventoryItem item) {
        return new TrinketBuff[]{
                (TrinketBuff)BuffRegistry.getBuff("meleesoulsealbuff"),
                (TrinketBuff)BuffRegistry.getBuff("summonsoulsealbuff"),
                (TrinketBuff)BuffRegistry.getBuff("magicsoulsealbuff"),
                (TrinketBuff)BuffRegistry.getBuff("rangesoulsealbuff"),
        };
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulsealfull"));
        tooltips.add(Localization.translate("itemtooltip", "soulsealfragment2"));
        tooltips.add(Localization.translate("itemtooltip", "soulsealsummonfragment"));
        return tooltips;
    }
}
