package soulchasm.main.tiles;

import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.gameTile.GrassTile;
import necesse.level.gameTile.TerrainSplatterTile;
import necesse.level.maps.Level;
import necesse.level.maps.regionSystem.SimulatePriorityList;
import soulchasm.SoulChasm;

import java.awt.*;

public class ChasmGrassTile extends TerrainSplatterTile {
    public static double growChance = GameMath.getAverageSuccessRuns(4900.0F);
    public static double spreadChance = GameMath.getAverageSuccessRuns(550.0F);
    private final GameRandom drawRandom;

    public ChasmGrassTile() {
        super(false, "chasmgrass");
        this.mapColor = SoulChasm.chasmGrassMapColor;
        this.canBeMined = true;
        this.drawRandom = new GameRandom();
        this.isOrganic = true;
    }

    public LootTable getLootTable(Level level, int tileX, int tileY) {
        return new LootTable(new ChanceLootItem(0.08F, "chasmgrassseeditem"));
    }

    public void addSimulateLogic(Level level, int x, int y, long ticks, SimulatePriorityList list, boolean sendChanges) {
        GrassTile.addSimulateGrow(level, x, y, growChance, ticks, "chasmgrassobject", list, sendChanges);
        GrassTile.addSimulateGrow(level, x, y, growChance, ticks, "lunartear", list, sendChanges);
        GrassTile.addSimulateGrow(level, x, y, growChance, ticks, "lunartearspath", list, sendChanges);
    }

    public double spreadToDirtChance() {
        return spreadChance;
    }

    public Point getTerrainSprite(GameTextureSection terrainTexture, Level level, int tileX, int tileY) {
        int tile;
        synchronized(this.drawRandom) {
            tile = this.drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(terrainTexture.getHeight() / 32);
        }
        return new Point(0, tile);
    }

    public int getTerrainPriority() {
        return 100;
    }
}