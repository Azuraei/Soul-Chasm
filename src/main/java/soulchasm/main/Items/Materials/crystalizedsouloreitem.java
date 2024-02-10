package soulchasm.main.Items.Materials;

import necesse.inventory.item.matItem.MatItem;


public class crystalizedsouloreitem extends MatItem {
    public crystalizedsouloreitem() {
        super(100);
        this.rarity = Rarity.UNCOMMON;
        this.setItemCategory("materials");
        this.keyWords.add("material");
    }
}
