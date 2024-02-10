package soulchasm.main.Misc.BiomeMisc;

import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameRandom;
import necesse.level.maps.presets.Preset;
import soulchasm.SoulChasm;

import java.awt.*;

public class soulcavesmallruinspreset extends Preset {
    public soulcavesmallruinspreset(int size, GameRandom random) {

        super(size, size);
        int mid = this.width / 2;
        int maxDistance = size / 2 * 32;
        int floor1 = TileRegistry.getTileID("soulcavebrickfloortile");
        int floor2 = TileRegistry.getTileID("soulcavefloortile");
        int lantern = ObjectRegistry.getObjectID("soullanternobject");


        Point point = new Point(size/2, size/2);
        for(int x = 0; x < this.width; ++x) {
            for (int y = 0; y < this.height; ++y) {
                float distance = (float) (new Point(mid * 32 + 16, mid * 32 + 16)).distance(x * 32 + 16, y * 32 + 16);
                float distancePerc = distance / (float) maxDistance;
                if (distancePerc < 0.7F) {
                    this.setTile(x, y, random.getChance(0.70F) ? floor1 : floor2);
                    this.setObject(x, y, 0);
                } else if (distancePerc <= 1.0F) {
                    float chance = Math.abs((distancePerc - 0.5F) * 2.0F - 1.0F) * 2.0F;
                    if (random.getChance(chance)) {
                        this.setTile(x, y, random.getChance(0.50F) ? floor1 : floor2);
                        this.setObject(x, y, 0);
                    }
                }
                if (distancePerc <= 0.9F && this.getObject(x, y) != -1 && random.getChance(0.05F)){
                    String[] randomworkstation = new String[]{"forge", "ironanvil"};
                    this.setObject(x, y, ObjectRegistry.getObjectID(randomworkstation[random.nextInt(randomworkstation.length)]), random.nextInt(4));
                }

                if (distancePerc <= 0.6F && this.getObject(x, y) != -1 && random.getChance(0.2F)){
                    String[] chests = new String[]{"soulcavechest", "oldbarrel", "chasmcrates"};
                    this.setObject(x, y, ObjectRegistry.getObjectID(chests[random.nextInt(chests.length)]), random.nextInt(4));
                    this.addInventory(SoulChasm.soulcaveruinsloottable, random, x, y);
                }
            }

        }
        for (int i = 0; i<2; i++){
            int randomXnumber = random.getIntBetween(-size/2+1,size/2-1);
            int randomYnumber = -random.getIntBetween(-size/2+1,size/2-1);
            this.setObject(point.x + randomXnumber, point.y + randomYnumber, lantern);
        }



    }
}