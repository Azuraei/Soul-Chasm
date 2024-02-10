package soulchasm.main.Objects.BiomeEnviroment;

import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.gameTile.GrassTile;
import necesse.level.maps.Level;
import necesse.level.maps.layers.SimulatePriorityList;

import java.awt.*;
public class soulcavegrass extends GrassTile {

    public soulcavegrass() {
        super();
        this.mapColor = new Color(28, 143, 139);
        this.canBeMined = true;
        this.terrainTextureName = "soulcavegrass";
    }

    public LootTable getLootTable(Level level, int tileX, int tileY) {
        return new LootTable(new ChanceLootItem(0.04F, "soulgrassseeditem"));
    }

    @Override
    public void addSimulateLogic(Level level, int x, int y, long ticks, SimulatePriorityList list, boolean sendChanges) {
        addSimulateGrow(level, x, y, growChance, ticks, "soulcavegrassobject", list, sendChanges);
        addSimulateGrow(level, x, y, growChance, ticks, "lunartear", list, sendChanges);
    }
}
