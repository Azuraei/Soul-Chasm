package soulchasm.main.Items.Trinkets.SealTrinkets;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;


public class MagicSoulSealTrinket extends TrinketItem {

    public MagicSoulSealTrinket() {
        super(Rarity.EPIC, 1000);
        this.disabledBy.add("summonsoulsealtrinket");
        this.disabledBy.add("magicsoulsealtrinket");
        this.disabledBy.add("rangesoulsealtrinket");
        this.disabledBy.add("meleesoulsealtrinket");
        this.disabledBy.add("soulsealtrinket");
        this.disabledBy.add("balancedsealtrinket");
    }
    public TrinketBuff[] getBuffs(InventoryItem item) {
        return new TrinketBuff[]{(TrinketBuff)BuffRegistry.getBuff("magicsoulsealbuff")};
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulsealfragment"));
        tooltips.add(Localization.translate("itemtooltip", "soulsealfragment2"));
        return tooltips;
    }
}
