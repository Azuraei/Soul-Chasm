package soulchasm.main.Items.Tools;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.server.FollowPosition;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.ToolItemSummonedMob;
import necesse.gfx.gameFont.FontManager;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;

import java.awt.geom.Point2D;

public class soulstatue extends SummonToolItem {
    private static final int summonCost = 2;
    public soulstatue() {
        super("soulstatuesummon", FollowPosition.WALK_CLOSE, summonCost, 2200);
        this.rarity = Rarity.EPIC;
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

    @Override
    public void summonMob(ServerClient client, ToolItemSummonedMob mob, int x, int y, int attackHeight, InventoryItem item) {
        Mob castedMob = (Mob)mob;
        client.addFollower(this.summonType, castedMob, this.followPosition, "summonedmob", this.getSummonSpaceTaken(item, client.playerMob), (p) -> this.getMaxSummons(item, p), null, false);
        Point2D.Float spawnPoint = new Point2D.Float(x, y);
        mob.updateDamage(this.getAttackDamage(item));
        mob.setEnchantment(this.getEnchantment(item));
        this.beforeSpawn(mob, item, client.playerMob);
        castedMob.getLevel().entityManager.addMob(castedMob, spawnPoint.x, spawnPoint.y);
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
        GameTooltips spaceTaken = this.getSpaceTakenTooltip(item, perspective);
        if (spaceTaken != null) {
            tooltips.add(spaceTaken);
        }
        return tooltips;
    }

    public boolean canLevelInteract(Level level, int x, int y, PlayerMob player, InventoryItem item) {
        return false;
    }
}
