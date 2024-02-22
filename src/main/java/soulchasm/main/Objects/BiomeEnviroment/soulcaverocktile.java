package soulchasm.main.Objects.BiomeEnviroment;

import necesse.engine.util.GameRandom;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.gameTile.TerrainSplatterTile;
import necesse.level.maps.Level;

import java.awt.*;

public class soulcaverocktile extends TerrainSplatterTile {
    private final GameRandom drawRandom;

    public soulcaverocktile() {
        super(false, "soulcaverocktile");
        this.mapColor = new Color(48, 52, 54);
        this.canBeMined = true;
        this.drawRandom = new GameRandom();
    }

    public Point getTerrainSprite(GameTextureSection terrainTexture, Level level, int tileX, int tileY) {
        int tile;
        synchronized(this.drawRandom) {
            tile = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).nextInt(terrainTexture.getHeight() / 32);
        }
        return new Point(0, tile);
    }

    public int getTerrainPriority() {
        return 100;
    }
}

