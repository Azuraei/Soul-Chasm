package soulchasm.main.Tiles;

import necesse.engine.util.GameRandom;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.gameTile.TerrainSplatterTile;
import necesse.level.maps.Level;

import java.awt.*;

public class SoulCaveRockTile extends TerrainSplatterTile {
    private final GameRandom drawRandom;

    public SoulCaveRockTile() {
        super(false, "soulcaverocktile");
        this.mapColor = new Color(17, 45, 57, 255);
        this.canBeMined = true;
        this.drawRandom = new GameRandom();
    }

    public Point getTerrainSprite(GameTextureSection terrainTexture, Level level, int tileX, int tileY) {
        int tile;
        synchronized(this.drawRandom) {
            tile = this.drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(terrainTexture.getHeight() / 32);
        }
        return new Point(0, tile);
    }

    public int getTerrainPriority() {
        return 0;
    }
}

