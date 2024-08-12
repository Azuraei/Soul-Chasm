package soulchasm.main.Objects.BiomeEnviroment;

import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.gameObject.GrassObject;
import necesse.level.maps.Level;

public class soulcavegrassobject extends GrassObject {
    public soulcavegrassobject() {
        super("soulcavegrassobject", 4);
    }
    public LootTable getLootTable(Level level, int tileX, int tileY) {
        return new LootTable(new ChanceLootItem(0.04F, "soulgrassseeditem"));
    }
}
