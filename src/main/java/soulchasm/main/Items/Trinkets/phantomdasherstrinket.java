package soulchasm.main.Items.Trinkets;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class phantomdasherstrinket extends TrinketItem {
    public phantomdasherstrinket() {
        super(Rarity.EPIC, 1600);
        this.disables.add("zephyrboots");
        this.disables.add("leatherdashers");
    }

    public ListGameTooltips getTrinketTooltips(InventoryItem item, PlayerMob perspective, boolean equipped) {
        ListGameTooltips tooltips = super.getTrinketTooltips(item, perspective, equipped);
        tooltips.add(Localization.translate("itemtooltip", "sprinttip"));
        tooltips.add(Localization.translate("itemtooltip", "staminausertip"));
        tooltips.add(Localization.translate("itemtooltip", "phantomdasherstrinket"));
        return tooltips;
    }

    public TrinketBuff[] getBuffs(InventoryItem item) {
        return new TrinketBuff[]{(TrinketBuff) BuffRegistry.getBuff("phantomdashersbuff")};
    }
}
