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
        int lantern = ObjectRegistry.getObjectID("soullantern");
        int spike = ObjectRegistry.getObjectID("spikeobject");

        for(int x = 0; x < this.width; ++x) {
            for(int y = 0; y < this.height; ++y) {
                float distance = (float)(new Point(mid * 32 + 16, mid * 32 + 16)).distance(x * 32 + 16, y * 32 + 16);
                float distancePerc = distance / (float)maxDistance;
                if (distancePerc < 0.85F && distancePerc > 0.18F) {
                    this.setTile(x, y, random.getChance(0.99F) ? grasstile : additionaltile);
                    this.setObject(x, y, 0);
                } else if (distancePerc <= 1.0F) {
                    float chance = Math.abs((distancePerc - 0.25F) * 2.0F - 1.0F) * 2.0F;
                    if (random.getChance(chance)) {
                        this.setTile(x, y, random.getChance(0.70F) ? soulstonetile : additionaltile);
                        this.setObject(x, y, 0);
                    }
                }

                if (distancePerc <= 0.8F && this.getObject(x, y) != -1 && random.getChance(0.015F) && this.getTile(x, y)==grasstile) {
                    this.setObject(x, y, tree);
                }
                if (distancePerc <= 0.8F && this.getObject(x, y) != -1 && random.getChance(0.15F) && this.getTile(x, y)==grasstile) {
                    this.setObject(x, y, grass);
                }
                if (distancePerc <= 0.8F && this.getObject(x, y) != -1 && random.getChance(0.1F) && this.getTile(x, y)==grasstile) {
                    this.setObject(x, y, random.getChance(0.5F)? lunartear:lunartearpath);
                }

                if (distance < (float)maxDistance && distance >= (float)(maxDistance - 40) && random.getChance(0.9F)) {
                    this.setObject(x, y, wall);
                }

            }
        }
        this.addMob("sphereeffectmob", mid, mid, false);
        int spikeOffset = 5;
        this.setObject(mid+spikeOffset, mid, spike, 3);
        this.setObject(mid-spikeOffset, mid, spike, 1);
        this.setObject(mid, mid+spikeOffset-1, spike, 0);
        this.setObject(mid, mid-spikeOffset+1, spike, 2);
        int lanternOffset = 2;
        this.setObject(mid+lanternOffset, mid+lanternOffset, lantern);
        this.setObject(mid+lanternOffset, mid-lanternOffset, lantern);
        this.setObject(mid-lanternOffset, mid+lanternOffset, lantern);
        this.setObject(mid-lanternOffset, mid-lanternOffset, lantern);
    }
}
