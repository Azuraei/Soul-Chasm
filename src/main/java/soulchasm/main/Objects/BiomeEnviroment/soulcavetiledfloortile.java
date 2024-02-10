package soulchasm.main.Objects.BiomeEnviroment;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.gameTile.TerrainSplatterTile;
import necesse.level.maps.Level;
import java.awt.*;
import static soulchasm.SoulChasm.SoulStoneColorLight;

public class soulcavetiledfloortile extends TerrainSplatterTile {
    private final GameRandom drawRandom;

    public soulcavetiledfloortile() {
        super(false, "soulcavetiledfloortile");
        this.mapColor = SoulStoneColorLight;
        this.canBeMined = true;
        this.drawRandom = new GameRandom();
    }

    public ModifierValue<Float> getSpeedModifier(Mob mob) {
        return mob.isFlying() ? super.getSpeedModifier(mob) : new ModifierValue(BuffModifiers.SPEED, 0.3F);
    }


    public Point getTerrainSprite(GameTextureSection terrainTexture, Level level, int tileX, int tileY) {
        int tile;
        synchronized(this.drawRandom) {
            tile = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).nextInt(terrainTexture.getHeight() / 32);
        }

        return new Point(0, tile);
    }

    public int getTerrainPriority() {
        return 110;
    }
}
