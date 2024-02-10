package soulchasm.main.Items.Materials;

import necesse.inventory.item.matItem.MatItem;


public class souldragonscales extends MatItem {
    public souldragonscales() {
        super(100);
        this.rarity = Rarity.EPIC;
        this.setItemCategory("materials");
        this.keyWords.add("material");
    }
}
