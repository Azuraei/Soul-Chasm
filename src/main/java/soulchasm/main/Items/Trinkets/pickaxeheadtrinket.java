package soulchasm.main.Items.Trinkets;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.level.maps.Level;

public class pickaxeheadtrinket extends TrinketItem {
    public pickaxeheadtrinket() {
        super(Rarity.EPIC, 1200);
    }

    public TrinketBuff[] getBuffs(InventoryItem item) {
        return new TrinketBuff[]{(TrinketBuff)BuffRegistry.getBuff("pickaxeheadbuff")};
    }
    public ListGameTooltips getTrinketTooltips(InventoryItem item, PlayerMob perspective, boolean equipped) {
        ListGameTooltips tooltips = super.getTrinketTooltips(item, perspective, equipped);
        tooltips.add(Localization.translate("itemtooltip", "pickaxeheadtrinket"));
        return tooltips;
    }
}
