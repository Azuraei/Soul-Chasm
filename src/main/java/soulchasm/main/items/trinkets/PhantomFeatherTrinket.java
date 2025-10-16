package soulchasm.main.items.trinkets;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.inventory.lootTable.presets.IncursionTrinketsLootTable;


public class PhantomFeatherTrinket extends TrinketItem {
    public PhantomFeatherTrinket() {

        super(Rarity.EPIC, 1000, IncursionTrinketsLootTable.incursionTrinkets);
    }

    public TrinketBuff[] getBuffs(InventoryItem item) {
        return new TrinketBuff[]{(TrinketBuff)BuffRegistry.getBuff("phantomfeatherbuff")};
    }
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "phantomfeathertrinket"));
        return tooltips;
    }
}
