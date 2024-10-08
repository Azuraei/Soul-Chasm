package soulchasm.main.Mobs.Passive;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.critters.caveling.CavelingMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import soulchasm.SoulChasm;

public class soulcavecaveling extends CavelingMob {
    public soulcavecaveling() {
        super(350, 55);
    }

    public void init() {
        super.init();
        this.popParticleColor = SoulChasm.SoulStoneColorLight;
        this.singleRockSmallStringID = "soulcaverockssmall";
        if (this.item == null) {
            this.item = GameRandom.globalRandom.getOneOf(new InventoryItem("crystalizedsouloreitem", GameRandom.globalRandom.getIntBetween(8, 12)));
        }
    }

    public LootTable getLootTable() {
        return super.getLootTable();
    }

    public LootTable getCavelingDropsAsLootTable() {
        return new LootTable(new LootItem("crystalizedsouloreitem", GameRandom.globalRandom.getIntBetween(8, 12)));
    }
}