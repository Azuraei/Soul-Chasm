package soulchasm.main.Items.Trinkets;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;


public class phantomfeathertrinket extends TrinketItem {
    public phantomfeathertrinket() {

        super(Rarity.EPIC, 1200);
    }

    public TrinketBuff[] getBuffs(InventoryItem item) {
        return new TrinketBuff[]{(TrinketBuff)BuffRegistry.getBuff("phantomfeatherbuff")};
    }
    public ListGameTooltips getTrinketTooltips(InventoryItem item, PlayerMob perspective, boolean equipped) {
        ListGameTooltips tooltips = super.getTrinketTooltips(item, perspective, equipped);
        tooltips.add(Localization.translate("itemtooltip", "phantomfeathertrinket"));
        return tooltips;
    }
}
