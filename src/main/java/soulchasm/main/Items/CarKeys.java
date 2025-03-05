package soulchasm.main.Items;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.mountItem.MountItem;
import necesse.level.maps.Level;
import soulchasm.main.Mobs.Summon.CarMob;

public class CarKeys extends MountItem implements ItemInteractAction {

    public CarKeys() {
        super("carmob");
        this.rarity = Rarity.LEGENDARY;
    }

    public boolean canLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item) {
        float distance;
        distance = GameMath.preciseDistance(x, y, attackerMob.x, attackerMob.y);
        return !(distance > 150);
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int seed, GNDItemMap mapContent) {
        String[] colors = new String[]{"Red", "Orange", "Yellow", "Green", "Blue", "Purple", "White", "Black"};
        item.getGndData().getInt("carColorIndex", 4);
        int index =  item.getGndData().getInt("carColorIndex");
        String message;
        if(attackerMob.getLevel().isServer()) {
            if (index < 7) {
                item.getGndData().setInt("carColorIndex", index + 1);
            } else {
                item.getGndData().setInt("carColorIndex", 0);
            }
        }
        message = colors[index];
        if(attackerMob.getLevel().isServer() && attackerMob.isPlayer) {
            PlayerMob playerMob = (PlayerMob) attackerMob;
            playerMob.getServerClient().sendUniqueFloatText(attackerMob.getX(), attackerMob.getY() - 32, new StaticMessage(message), null, 5);
        }
        return item;
    }

    protected void beforeSpawn(Mob mob, InventoryItem item, PlayerMob player) {
        CarMob c = (CarMob) mob;
        c.textureColorIndex = item.getGndData().getInt("carColorIndex");
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "cartip"));
        return tooltips;
    }
}
