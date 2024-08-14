package soulchasm.main.Items.Others;

import necesse.engine.registries.ObjectRegistry;
import necesse.inventory.item.placeableItem.objectItem.ObjectItem;

public class fireflyjar extends ObjectItem {
    public fireflyjar() {
        super(ObjectRegistry.getObject("fireflyjarobject"), false);
        this.stackSize = 20;
        this.rarity = Rarity.UNCOMMON;
    }
}
