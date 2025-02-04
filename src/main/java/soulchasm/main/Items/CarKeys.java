package soulchasm.main.Items;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketMobMount;
import necesse.engine.network.server.FollowPosition;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.mountItem.MountItem;
import necesse.level.maps.Level;
import soulchasm.main.Mobs.Summon.CarMob;

import java.awt.geom.Point2D;

public class CarKeys extends MountItem implements ItemInteractAction {

    public CarKeys() {
        super("carmob");
        this.rarity = Rarity.LEGENDARY;
    }

    public boolean canLevelInteract(Level level, int x, int y, PlayerMob player, InventoryItem item) {
        float distance;
        distance = GameMath.preciseDistance(x, y, player.x, player.y);
        return !(distance > 150);
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int seed, PacketReader contentReader) {
        item.getGndData().getInt("carColorIndex", 4);
        int index =  item.getGndData().getInt("carColorIndex");
        String message;
        if(player.getLevel().isServer()) {
            if (index < 7) {
                item.getGndData().setInt("carColorIndex", index + 1);
            } else {
                item.getGndData().setInt("carColorIndex", 0);
            }
        }
        switch (item.getGndData().getInt("carColorIndex")){
            case 0:
                message = "Red";
                break;
            case 1:
                message = "Orange";
                break;
            case 2:
                message = "Yellow";
                break;
            case 3:
                message = "Green";
                break;
            case 4:
                message = "Blue";
                break;
            case 5:
                message = "Purple";
                break;
            case 6:
                message = "White";
                break;
            case 7:
                message = "Black";
                break;
            default:
                message = "None";
                break;
        }
        if(player.getLevel().isServer()) {
            player.getServerClient().sendUniqueFloatText(player.getX(), player.getY() - 32, new StaticMessage(message), null, 5);
        }
        return item;
    }

    @Override
    public InventoryItem useMount(ServerClient client, float playerX, float playerY, InventoryItem item, Level level) {
        GNDItemMap gndData = item.getGndData();
        PlayerMob player = client.playerMob;
        Mob lastMount = player.getMount();
        if (lastMount != null) {
            player.dx = lastMount.dx;
            player.dy = lastMount.dy;
        }

        if (lastMount != null) {
            if (lastMount.getStringID().equals(this.mobStringID)) {
                client.playerMob.buffManager.removeBuff("summonedmount", true);
            } else {
                client.playerMob.dismount();
                level.getServer().network.sendToClientsAt(new PacketMobMount(client.slot, -1, true, playerX, playerY), level);
            }
        } else {
            CarMob m = (CarMob) MobRegistry.getMob(this.mobStringID, level);
            FollowPosition followPosition = this.getFollowPosition(item, player);
            client.addFollower("summonedmount", m, followPosition, "summonedmount", 1.0F, 1, null, false);
            Point2D.Float spawnPos = this.getMountSpawnPos(m, client, playerX, playerY, item, level);
            m.setPos(spawnPos.x, spawnPos.y, true);
            boolean mount = client.playerMob.mount(m, this.setMounterPos);
            if (mount) {
                m.dx = player.dx;
                m.dy = player.dy;
                m.summonItem = item;
                m.textureColorIndex = gndData.getInt("carColorIndex");
                this.beforeSpawn(m, item, player);
                level.entityManager.addMob(m, spawnPos.x, spawnPos.y);
                level.getServer().network.sendToClientsAt(new PacketMobMount(client.slot, m.getUniqueID(), this.setMounterPos, playerX, playerY), level);
            }
        }
        return item;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "cartip"));
        return tooltips;
    }
}
