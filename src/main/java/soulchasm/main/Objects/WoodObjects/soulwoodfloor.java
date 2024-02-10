package soulchasm.main.Objects.WoodObjects;

import soulchasm.SoulChasm;

import java.awt.*;


public class soulwoodfloor extends necesse.level.gameTile.SimpleFloorTile {

    public soulwoodfloor() {
        super("soulwoodfloor", SoulChasm.SoulWoodColor);
    }
    public int getTerrainPriority() {
        return 400;
    }
}
