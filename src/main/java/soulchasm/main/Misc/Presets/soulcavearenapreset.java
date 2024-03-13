package soulchasm.main.Misc.Presets;

import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameRandom;
import necesse.level.maps.presets.Preset;

import java.awt.*;

public class soulcavearenapreset extends Preset {
    public soulcavearenapreset(int size, GameRandom random) {
        super(size, size);
        int mid = size / 2;
        int maxDistance = size / 2 * 32;
        int grasstile = TileRegistry.getTileID("soulcavegrass");
        int soulstonetile = TileRegistry.getTileID("soulcavebrickfloortile");
        int additionaltile = TileRegistry.getTileID("soulcavefloortile");
        int tree = ObjectRegistry.getObjectID("soultree");
        int grass = ObjectRegistry.getObjectID("soulcavegrassobject");
        int lunartear = ObjectRegistry.getObjectID("lunartear");
        int lunartearpath = ObjectRegistry.getObjectID("lunartearspath");
        int wall = ObjectRegistry.getObjectID("soulbrickwall");

        for(int x = 0; x < this.width; ++x) {
            for(int y = 0; y < this.height; ++y) {
                float distance = (float)(new Point(mid * 32 + 16, mid * 32 + 16)).distance((double)(x * 32 + 16), (double)(y * 32 + 16));
                float distancePerc = distance / (float)maxDistance;
                if (distancePerc < 0.85F && distancePerc > 0.15F) {
                    this.setTile(x, y, random.getChance(0.99F) ? grasstile : additionaltile);
                    this.setObject(x, y, 0);
                } else if (distancePerc <= 1.0F) {
                    float chance = Math.abs((distancePerc - 0.5F) * 2.0F - 1.0F) * 2.0F;
                    if (random.getChance(chance)) {
                        this.setTile(x, y, random.getChance(0.70F) ? soulstonetile : additionaltile);
                        this.setObject(x, y, 0);
                    }
                }

                if (distancePerc <= 0.8F && this.getObject(x, y) != -1 && random.getChance(0.015F) && this.getTile(x, y)==grasstile) {
                    this.setObject(x, y, tree);
                }
                if (distancePerc <= 0.8F && this.getObject(x, y) != -1 && random.getChance(0.2F) && this.getTile(x, y)==grasstile) {
                    this.setObject(x, y, grass);
                }
                if (distancePerc <= 0.8F && this.getObject(x, y) != -1 && random.getChance(0.05F) && this.getTile(x, y)==grasstile) {
                    this.setObject(x, y, random.getChance(0.5F)? lunartear:lunartearpath);
                }

                if (distance < (float)maxDistance && distance >= (float)(maxDistance - 40) && random.getChance(0.9F)) {
                    this.setObject(x, y, wall);
                }

            }
        }
        this.setObject(mid, mid, ObjectRegistry.getObjectID("soulbossaltarobject"));
        this.setObject(mid+4, mid, ObjectRegistry.getObjectID("statueobject"), 3);
        this.setObject(mid-4, mid, ObjectRegistry.getObjectID("statueobject"), 1);
        this.setObject(mid, mid+4, ObjectRegistry.getObjectID("statueobject"), 0);
        this.setObject(mid, mid-4, ObjectRegistry.getObjectID("statueobject"), 2);
        this.setTile(mid+4, mid, soulstonetile);
        this.setTile(mid-4, mid, soulstonetile);
        this.setTile(mid, mid+4, soulstonetile);
        this.setTile(mid, mid-4, soulstonetile);
    }
}