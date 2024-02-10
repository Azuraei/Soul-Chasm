package soulchasm.main.Items.Materials;

import necesse.inventory.item.matItem.MatItem;

public class soulcoreitem extends MatItem {
    public soulcoreitem() {
        super(100);
        this.rarity = Rarity.UNCOMMON;
        this.dropsAsMatDeathPenalty = true;
        this.setItemCategory("materials");
        this.keyWords.add("material");
    }
}
