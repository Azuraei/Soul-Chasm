package soulchasm.main.Objects.BiomeEnviroment;

import necesse.level.gameTile.SimpleFloorTile;

import static soulchasm.SoulChasm.SoulStoneColorLight;

public class soulcavebrickfloortile extends SimpleFloorTile {

    public soulcavebrickfloortile() {
        super("soulcavebrickfloortile", SoulStoneColorLight);

    }
    public int getTerrainPriority() {
        return 100;
    }
}
