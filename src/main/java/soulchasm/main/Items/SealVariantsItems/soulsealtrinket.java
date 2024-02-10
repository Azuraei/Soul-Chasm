package soulchasm.main.Items.SealVariantsItems;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;


public class soulsealtrinket extends TrinketItem {

    public soulsealtrinket() {
        super(Rarity.LEGENDARY, 2500);
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

    public ListGameTooltips getTrinketTooltips(InventoryItem item, PlayerMob perspective, boolean equipped) {
        ListGameTooltips tooltips = super.getTrinketTooltips(item, perspective, equipped);
        tooltips.add(Localization.translate("itemtooltip", "soulsealfull"));
        return tooltips;
    }
}
