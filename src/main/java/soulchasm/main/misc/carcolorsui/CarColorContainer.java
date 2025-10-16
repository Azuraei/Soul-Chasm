package soulchasm.main.misc.carcolorsui;

import necesse.engine.network.NetworkClient;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.container.Container;
import necesse.inventory.container.customAction.StringCustomAction;
import soulchasm.main.items.CarKeys;

public class CarColorContainer extends Container {
    public final StringCustomAction getColorIndex;
    public final int itemID;
    public final PlayerInventorySlot inventoryItemSlot;

    public CarColorContainer(final NetworkClient client, int uniqueSeed, Packet content){
        super(client, uniqueSeed);
        PacketReader reader = new PacketReader(content);
        this.itemID = reader.getNextShortUnsigned();
        int itemInventoryID = reader.getNextInt();
        int itemInventorySlot = reader.getNextInt();
        this.inventoryItemSlot = new PlayerInventorySlot(itemInventoryID, itemInventorySlot);
        InventoryItem item = this.inventoryItemSlot.getItem(client.playerMob.getInv());

        this.getColorIndex = this.registerAction(new StringCustomAction() {
            @Override
            protected void run(String s) {
                if (client.isServer()) {
                    if (item.item instanceof CarKeys){
                        item.getGndData().setInt("carColorIndex", Integer.parseInt(s));
                    } else {
                        System.out.println("CarKeys Error - invalid item!");
                    }
                    client.getServerClient().closeContainer(true);
                }
            }
        });
    }
}
