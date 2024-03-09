package soulchasm.main.Misc.Presets;

import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameRandom;
import necesse.level.maps.presets.Preset;
import soulchasm.SoulChasm;

import java.awt.*;

public class soulcaveshrinepreset extends Preset {
    public soulcaveshrinepreset(int size, GameRandom random) {

        super(size, size);
        int mid = this.width / 2;
        int maxDistance = size / 2 * 32;
        int floor1 = TileRegistry.getTileID("soulcavebrickfloortile");
        int floor2 = TileRegistry.getTileID("soulcavefloortile");
        int monument = ObjectRegistry.getObjectID("soulmonumentobject");

        Point point = new Point(size/2, size/2);
        for(int x = 0; x < this.width; ++x) {
            for (int y = 0; y < this.height; ++y) {
                float distance = (float) (new Point(mid * 32 + 16, mid * 32 + 16)).distance(x * 32 + 16, y * 32 + 16);
                float distancePerc = distance / (float) maxDistance;
                if (distancePerc < 0.7F) {
                    this.setTile(x, y, random.getChance(0.7F) ? floor1 : floor2);
                    this.setObject(x, y, 0);
                } else if (distancePerc <= 1.0F) {
                    float chance = Math.abs((distancePerc - 0.5F) * 2.0F - 1.0F) * 2.0F;
                    if (random.getChance(chance)) {
                        this.setTile(x, y, random.getChance(0.5F) ? floor1 : floor2);
                        this.setObject(x, y, 0);
                    }
                }
            }
        }
        this.setObject(point.x, point.y, monument);
        this.addInventory(SoulChasm.soulcavemonumentshrineloottable, random, point.x, point.y, monument);
        //this.addMob("", point.x, point.y, false);
        this.setObject(point.x+4, point.y, ObjectRegistry.getObjectID("statueobject"), 3);
        this.setObject(point.x-4, point.y, ObjectRegistry.getObjectID("statueobject"), 1);
        this.setObject(point.x, point.y+4, ObjectRegistry.getObjectID("statueobject"), 0);
        this.setObject(point.x, point.y-4, ObjectRegistry.getObjectID("statueobject"), 2);
    }
}