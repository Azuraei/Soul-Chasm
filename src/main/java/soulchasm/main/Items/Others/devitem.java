package soulchasm.main.Items.Others;

import necesse.engine.network.PacketReader;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.EvilsProtectorBombAttackEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.Level;

public class devitem extends ConsumableItem {
    public devitem() {
        super(1, false);
        this.itemCooldownTime.setBaseValue(100);
        this.rarity = Rarity.LEGENDARY;
        this.worldDrawSize = 32;
    }

    public String canPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        return null;
    }

    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        if (level.isServer()) {
            EvilsProtectorBombAttackEvent event = new EvilsProtectorBombAttackEvent(player, x, y, GameRandom.globalRandom.nextSeeded(), new GameDamage(100));
            level.entityManager.addLevelEvent(event);
        }
        return item;
    }
}
