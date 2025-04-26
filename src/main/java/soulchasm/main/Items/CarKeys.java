package soulchasm.main.Items;

import necesse.engine.localization.Localization;
import necesse.engine.network.packet.PacketOpenContainer;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.ContainerRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.container.Container;
import necesse.inventory.container.ContainerActionResult;
import necesse.inventory.container.item.ItemInventoryContainer;
import necesse.inventory.container.slots.ContainerSlot;
import necesse.inventory.item.miscItem.InternalInventoryItemInterface;
import necesse.inventory.item.mountItem.MountItem;
import soulchasm.SoulChasm;
import soulchasm.main.Mobs.Summon.CarMob;

import java.util.function.Supplier;

public class CarKeys extends MountItem implements InternalInventoryItemInterface {

    public CarKeys() {
        super("carmob");
        this.rarity = Rarity.LEGENDARY;
    }

    public Supplier<ContainerActionResult> getInventoryRightClickAction(Container container, InventoryItem item, int slotIndex, ContainerSlot slot) {
        return () -> {
            PlayerInventorySlot playerSlot = null;
            if (slot.getInventory() == container.getClient().playerMob.getInv().main) {
                playerSlot = new PlayerInventorySlot(container.getClient().playerMob.getInv().main, slot.getInventorySlot());
            }

            if (slot.getInventory() == container.getClient().playerMob.getInv().cloud) {
                playerSlot = new PlayerInventorySlot(container.getClient().playerMob.getInv().cloud, slot.getInventorySlot());
            }

            if (slotIndex == container.CLIENT_MOUNT_SLOT){
                playerSlot = new PlayerInventorySlot(container.getClient().playerMob.getInv().equipment.getSelectedEquipmentInventory(), container.getSlot(container.CLIENT_MOUNT_SLOT).getInventorySlot());
            }

            if (playerSlot != null) {
                if (container.getClient().isServer()) {
                    ServerClient client = container.getClient().getServerClient();
                    this.openContainer(client, playerSlot);
                }

                return new ContainerActionResult(-1002911334);
            } else {
                return new ContainerActionResult(208675834, Localization.translate("itemtooltip", "rclickinvopenerror"));
            }
        };
    }

    protected void openContainer(ServerClient client, PlayerInventorySlot inventorySlot) {
        PacketOpenContainer p = new PacketOpenContainer(SoulChasm.CAR_COLOR_CONTAINER, ItemInventoryContainer.getContainerContent(this, inventorySlot));
        ContainerRegistry.openAndSendContainer(client, p);
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "cartip"));
        return tooltips;
    }

    @Override
    public String getInventoryRightClickControlTip(Container container, InventoryItem item, int slotIndex, ContainerSlot slot) {
        return null;
    }

    protected void beforeSpawn(Mob mob, InventoryItem item, PlayerMob player) {
        CarMob c = (CarMob) mob;
        c.colorIndex = item.getGndData().getInt("carColorIndex");
    }

    @Override
    public int getInternalInventorySize() {
        return 0;
    }

    @Override
    public boolean isValidItem(InventoryItem item) {
        return false;
    }
}
