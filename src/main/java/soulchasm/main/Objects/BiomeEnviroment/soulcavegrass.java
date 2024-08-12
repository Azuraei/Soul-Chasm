package soulchasm.main.Objects.BiomeEnviroment;

import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.gameTile.GrassTile;
import necesse.level.maps.Level;
import necesse.level.maps.layers.SimulatePriorityList;
import soulchasm.SoulChasm;
public class soulcavegrass extends GrassTile {

    public soulcavegrass() {
        super();
        this.mapColor = SoulChasm.SoulGrassColor;
        this.canBeMined = true;
        this.terrainTextureName = "soulcavegrass";
        this.isFloor = false;
    }

    public LootTable getLootTable(Level level, int tileX, int tileY) {
        return new LootTable(new ChanceLootItem(0.01F, "soulgrassseeditem"));
    }

    @Override
    public void addSimulateLogic(Level level, int x, int y, long ticks, SimulatePriorityList list, boolean sendChanges) {
        addSimulateGrow(level, x, y, growChance, ticks, "soulcavegrassobject", list, sendChanges);
        addSimulateGrow(level, x, y, growChance, ticks, "lunartear", list, sendChanges);
        addSimulateGrow(level, x, y, growChance, ticks, "lunartearspath", list, sendChanges);
    }
}
