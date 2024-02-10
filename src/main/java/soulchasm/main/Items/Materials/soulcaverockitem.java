package soulchasm.main.Items.Materials;

import necesse.inventory.item.matItem.MatItem;

public class soulcaverockitem extends MatItem {
    public soulcaverockitem() {
        super(999);
        this.rarity = Rarity.UNCOMMON;
        this.setItemCategory("materials");
        this.keyWords.add("material");
    }
}
