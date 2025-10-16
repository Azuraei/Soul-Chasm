package soulchasm.main.items.trinkets.SealTrinkets;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.inventory.lootTable.presets.IncursionTrinketsLootTable;


public class MeleeSoulSealTrinket extends TrinketItem {

    public MeleeSoulSealTrinket() {
        super(Rarity.EPIC, 1000, IncursionTrinketsLootTable.incursionTrinkets);
        this.disabledBy.add("summonsoulsealtrinket");
        this.disabledBy.add("magicsoulsealtrinket");
        this.disabledBy.add("rangesoulsealtrinket");
        this.disabledBy.add("meleesoulsealtrinket");
        this.disabledBy.add("soulsealtrinket");
        this.disabledBy.add("balancedsealtrinket");
    }
    public TrinketBuff[] getBuffs(InventoryItem item) {
        return new TrinketBuff[]{(TrinketBuff)BuffRegistry.getBuff("meleesoulsealbuff")};
    }


    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulsealfragment"));
        tooltips.add(Localization.translate("itemtooltip", "soulsealfragment2"));
        return tooltips;
    }
}
