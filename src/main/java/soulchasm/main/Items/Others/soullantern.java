package soulchasm.main.Items.Others;

import necesse.engine.registries.ObjectRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.objectItem.ObjectItem;

import java.awt.*;

public class soullantern extends ObjectItem {
    public soullantern() {
        super(ObjectRegistry.getObject("soullanternobject"), false);
        this.stackSize = 20;
        this.rarity = Rarity.UNCOMMON;
    }

    public void loadTextures() {
        super.loadTextures();
        this.holdTexture = GameTexture.fromFile("player/holditems/soullantern");
    }

    public void tickHolding(InventoryItem item, PlayerMob player) {
        super.tickHolding(item, player);
        player.getLevel().lightManager.refreshParticleLightFloat(player.x - 3, player.y - 3, new Color(0x1495FF), 0.3F, 125);
    }
}
