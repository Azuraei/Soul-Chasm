package soulchasm.main.Items.Tools;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.server.FollowPosition;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameFont.FontManager;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;

public class soulstatue extends SummonToolItem {
    public soulstatue() {
        super("soulstatuesummon", FollowPosition.WALK_CLOSE, 3.0F, 1900);
        this.rarity = Rarity.EPIC;
    }

    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        return null;
    }

    public int getMaxSummons(InventoryItem item, PlayerMob player) {
        return 3;
    }

    @Override
    public void draw(InventoryItem item, PlayerMob perspective, int x, int y, boolean inInventory) {
        super.draw(item, perspective, x, y, inInventory);
        if (this.drawMaxSummons && inInventory) {
            int maxSummons = this.getMaxSummons(item, perspective)/3;
            if (maxSummons > 999) {
                maxSummons = 999;
            }
            if (maxSummons != 1) {
                String amountString = String.valueOf(maxSummons);
                int width = FontManager.bit.getWidthCeil(amountString, tipFontOptions);
                FontManager.bit.drawString((float)(x + 28 - width), (float)(y + 16), amountString, tipFontOptions);
            }
        }
    }

    public void runSummon(Level level, int x, int y, ServerClient client, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob(this.mobStringID, level);
        this.summonMob(client, mob, x, y, attackHeight, item);
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "soulstatuetip"));
        return tooltips;
    }
}
