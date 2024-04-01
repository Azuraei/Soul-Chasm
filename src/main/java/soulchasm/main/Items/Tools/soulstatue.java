package soulchasm.main.Items.Tools;

import necesse.engine.Screen;
import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.server.FollowPosition;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;

public class soulstatue extends SummonToolItem {
    public soulstatue() {
        super("soulstatuesummon", FollowPosition.WALK_CLOSE, 3, 2200);
        this.rarity = Rarity.EPIC;
    }

    @Override
    public void draw(InventoryItem item, PlayerMob perspective, int x, int y, boolean inInventory) {
        this.drawIcon(item, perspective, x, y, 32);
        if (inInventory && perspective != null) {
            float percentCooldown = this.getItemCooldownPercent(item, perspective);
            if (percentCooldown > 0.0F) {
                int size = 34;
                int pixels = GameMath.limit((int)(percentCooldown * (float)size), 1, size);
                Screen.initQuadDraw(size, pixels).color(0.0F, 0.0F, 0.0F, 0.5F).draw(x - 1, y + Math.abs(pixels - size) - 1);
            }
        }
    }

    public void runSummon(Level level, int x, int y, ServerClient client, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob(this.mobStringID, level);
        this.summonMob(client, mob, x, y, attackHeight, item);
    }

    @Override
    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        float spaceTaken = this.getSummonSpaceTaken(item, perspective);
        return spaceTaken != 1.0F ? new StringTooltips(Localization.translate("itemtooltip", "summonuseslots", "count", (int) spaceTaken)) : null;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "soulstatuetip"), 400);
        tooltips.add(Localization.translate("itemtooltip", "summonfocustip"));
        GameTooltips spaceTaken = this.getSpaceTakenTooltip(item, perspective);
        if (spaceTaken != null) {
            tooltips.add(spaceTaken);
        }
        return tooltips;
    }
}
