package soulchasm.main.Items.Materials;

import necesse.inventory.item.matItem.MatItem;


public class soulmetalbar extends MatItem {
    public soulmetalbar() {
        super(100);
        this.rarity = Rarity.UNCOMMON;
        this.setItemCategory("materials");
        this.keyWords.add("material");
    }
}
