package soulchasm.main.items.tools;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameFont.FontManager;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.IncursionSummonWeaponsLootTable;
import necesse.level.maps.Level;

public class SoulIdolSummon extends SummonToolItem {
    private static final int summonCost = 2;
    public SoulIdolSummon() {
        super("soulstatuesummon", FollowPosition.WALK_CLOSE, summonCost, 2200, IncursionSummonWeaponsLootTable.incursionSummonWeapons);
        this.rarity = Rarity.EPIC;
        this.canBeUsedForRaids = false;
    }

    @Override
    public void draw(InventoryItem item, PlayerMob perspective, int x, int y, boolean inInventory) {
        super.draw(item, perspective, x, y, inInventory);
        if (this.drawMaxSummons && inInventory) {
            int maxSummons = this.getMaxSummons(item, perspective);
            if (maxSummons > 999) {
                maxSummons = 999;
            }
            if (maxSummons % summonCost == 0) {
                String amountString = String.valueOf(maxSummons / summonCost);
                int width = FontManager.bit.getWidthCeil(amountString, tipFontOptions);
                FontManager.bit.drawString((float)(x + 28 - width), (float)(y + 16), amountString, tipFontOptions);
            }
        }
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "soulstatuetip"), 400);
        GameTooltips spaceTaken = this.getSpaceTakenTooltip(item, perspective);
        if (spaceTaken != null) {
            tooltips.add(spaceTaken);
        }
        return tooltips;
    }

    public boolean canLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item) {
        return false;
    }
}
