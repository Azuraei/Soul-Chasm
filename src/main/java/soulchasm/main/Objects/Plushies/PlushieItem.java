package soulchasm.main.Objects.Plushies;

import necesse.engine.network.PacketReader;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.GameResources;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.MobSpawnItem;
import necesse.level.maps.Level;

public class PlushieItem extends MobSpawnItem {
    private final String mobType;
    public PlushieItem(String mobType) {
        super(100, true, mobType);
        this.mobType = mobType;
    }
    @Override
    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        if (level.isServer()) {
            Mob mob = MobRegistry.getMob(this.mobType, level);
            this.beforeSpawned(level, x, y, player, item, contentReader, mob);
            level.entityManager.addMob(mob, x, y);
        }

        if (this.singleUse) {
            item.setAmount(item.getAmount() - 1);
        }

        if (level.isClient()) {
            SoundManager.playSound(GameResources.pop, SoundEffect.effect(player).pitch(0.8F));
        }

        return item;
    }
}
