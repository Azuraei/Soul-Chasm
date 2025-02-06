package soulchasm.main.Plushies;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.MobSpawnItem;
import necesse.level.maps.Level;

public class PlushieItem extends MobSpawnItem {
    private final String mobType;
    private final boolean addCustomTip;
    public PlushieItem(String mobType, boolean addCustomTip) {
        super(100, true, mobType);
        this.mobType = mobType;
        this.addCustomTip = addCustomTip;
    }

    @Override
    public GameMessage getNewLocalization() {
        return new StaticMessage("");
    }

    public GameMessage getLocalization(InventoryItem item) {
        String message = Localization.translate("itemtooltip", mobType + "plushie");
        return new StaticMessage(message);
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

    @Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = this.getBaseTooltips(item, perspective, blackboard);
        tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "placetip")));
        if (addCustomTip){
            tooltips.add(new StringTooltips(Localization.translate("itemtooltip", this.mobType+"tip")));
        }
        return tooltips;
    }
}
