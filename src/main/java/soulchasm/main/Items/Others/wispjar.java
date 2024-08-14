package soulchasm.main.Items.Others;

import necesse.engine.registries.ObjectRegistry;
import necesse.inventory.item.placeableItem.objectItem.ObjectItem;

public class wispjar extends ObjectItem {
    public wispjar() {
        super(ObjectRegistry.getObject("wispjarobject"), false);
        this.stackSize = 20;
        this.rarity = Rarity.UNCOMMON;
    }
}
