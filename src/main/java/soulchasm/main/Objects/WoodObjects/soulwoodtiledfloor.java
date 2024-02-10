package soulchasm.main.Objects.WoodObjects;

import necesse.level.gameTile.SimpleTiledFloorTile;
import soulchasm.SoulChasm;

import java.awt.*;


public class soulwoodtiledfloor extends SimpleTiledFloorTile {


    public soulwoodtiledfloor() {
        super("soulwoodtiledfloor", SoulChasm.SoulWoodColor);


    }

    public int getTerrainPriority() {
        return 400;
    }
}
