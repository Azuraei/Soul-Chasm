package soulchasm.main.Items.Trinkets;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class phantomdasherstrinket extends TrinketItem {
    public phantomdasherstrinket() {
        super(Rarity.EPIC, 1200);
        this.disables.add("zephyrboots");
        this.disables.add("leatherdashers");
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "sprinttip"));
        tooltips.add(Localization.translate("itemtooltip", "staminausertip"));
        tooltips.add(Localization.translate("itemtooltip", "phantomdasherstrinket"));
        return tooltips;
    }

    public TrinketBuff[] getBuffs(InventoryItem item) {
        return new TrinketBuff[]{(TrinketBuff) BuffRegistry.getBuff("phantomdashersbuff")};
    }
}
