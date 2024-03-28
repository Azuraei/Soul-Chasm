package soulchasm.main.Items.Others;

import necesse.engine.network.PacketReader;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.WaitForSecondsEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.Level;
import soulchasm.main.Misc.Others.GroundEruptionEvent.dragongrounderuptionevent;
import soulchasm.main.Misc.Others.SpinningProjectileSpawnerEvent.spinspawnevent;
import soulchasm.main.Misc.Others.SpinningProjectileSpawnerEvent.spinspawnvisualevent;

public class devitem extends ConsumableItem implements ItemInteractAction {
    public devitem() {
        super(1, false);
        this.itemCooldownTime.setBaseValue(1000);
        this.rarity = Rarity.LEGENDARY;
        this.worldDrawSize = 32;
    }

    public boolean canLevelInteract(Level level, int x, int y, PlayerMob player, InventoryItem item) {
        return true;
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int seed, PacketReader contentReader) {
        item.getGndData().setBoolean("alt", !item.getGndData().getBoolean("alt"));
        return item;
    }

    public String canPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        return null;
    }

    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        if (level.isServer()) {
            if(item.getGndData().getBoolean("alt")){
                for(int i = 0; i<5; i++){
                    level.entityManager.addLevelEventHidden(new WaitForSecondsEvent(0.5F * i) {
                        public void onWaitOver() {
                            int range = 300;
                            int pos_x = x + GameRandom.globalRandom.getIntBetween(-range, range);
                            int pos_y = y + GameRandom.globalRandom.getIntBetween(-range, range);
                            dragongrounderuptionevent event = new dragongrounderuptionevent(player, pos_x, pos_y, GameRandom.globalRandom.nextSeeded(), new GameDamage(100));
                            level.entityManager.addLevelEvent(event);
                        }
                    });
                }
            } else {
                spinspawnvisualevent event = new spinspawnvisualevent(x, y, 6000);
                level.entityManager.addLevelEvent(event);
                spinspawnevent event2 = new spinspawnevent(player, x, y, GameRandom.globalRandom.nextSeeded(), new GameDamage(100), 3000);
                level.entityManager.addLevelEvent(event2);

            }
        }
        return item;
    }
}
