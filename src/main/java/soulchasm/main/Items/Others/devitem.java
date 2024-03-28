package soulchasm.main.Items.Others;

import necesse.engine.network.PacketReader;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.Level;
import soulchasm.main.Misc.Others.GroundEruptionEvent.dragongrounderuptionevent;

public class devitem extends ConsumableItem {
    public devitem() {
        super(1, false);
        this.itemCooldownTime.setBaseValue(1000);
        this.rarity = Rarity.LEGENDARY;
        this.worldDrawSize = 32;
    }

    public String canPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        return null;
    }

    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        if (level.isServer()) {
            for(int i = 0; i<5; i++){
                int range = 300;
                int pos_x = x + GameRandom.globalRandom.getIntBetween(-range, range);
                int pos_y = y + GameRandom.globalRandom.getIntBetween(-range, range);
                dragongrounderuptionevent event = new dragongrounderuptionevent(player, pos_x, pos_y, GameRandom.globalRandom.nextSeeded(), new GameDamage(100));
                level.entityManager.addLevelEvent(event);
            }
        }
        return item;
    }
}
