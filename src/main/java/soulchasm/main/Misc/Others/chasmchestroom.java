package soulchasm.main.Misc.Others;

import necesse.engine.util.GameRandom;
import necesse.inventory.lootTable.LootTable;
import necesse.level.maps.Level;
import necesse.level.maps.levelBuffManager.LevelModifiers;
import necesse.level.maps.presets.Preset;
import necesse.level.maps.presets.PresetMirrorException;
import necesse.level.maps.presets.PresetRotateException;
import necesse.level.maps.presets.PresetRotation;
import necesse.level.maps.presets.set.ChestRoomSet;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class chasmchestroom extends Preset {
    public chasmchestroom(GameRandom random, LootTable lootTable, AtomicInteger lootRotation, ChestRoomSet... chestRoomSets) {
        super(7, 7);
        this.applyScript("PRESET = {\n\twidth = 7,\n\theight = 7,\n\ttileData = [watertile, grasstile, sandtile, dirttile, rocktile, dungeonfloor, farmland, woodfloor, rockfloor, sandstonetile, swamprocktile, snowrocktile, lavatile, snowtile, icetile, stonepathtile, graveltile, sandbrick],\n\ttiles = [-1, 8, 8, 8, 8, 8, -1, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, -1, 8, 8, 8, 8, 8, -1],\n\tobjectData = [air, oaktree, oaksapling, pinetree, pinesapling, cactus, cactussapling, workstation, forge, carpentersbench, carpentersbench2, ironanvil, demonicworkstation, alchemytable, woodwall, wooddoor, wooddooropen, stonewall, stonedoor, stonedooropen, sandstonewall, sandstonedoor, sandstonedooropen, swampstonewall, swampstonedoor, swampstonedooropen, icewall, icedoor, icedooropen, brickwall, brickdoor, brickdooropen, dungeonwall, dungeondoor, dungeondooropen, woodfence, woodfencegate, woodfencegateopen, storagebox, barrel, demonchest, torch, walltorch, ironlamp, goldlamp, firechalice, firechalicer, sign, flowerpot, itemstand, armorstand, trainingdummy, wooddinnertable, wooddinnertable2, wooddesk, woodmodulartable, woodchair, dungeondinnertable, dungeondinnertable2, dungeonchair, golddinnertable, golddinnertable2, goldchair, woodbookshelf, dungeonbookshelf, woodbathtub, woodbathtubr, dungeonbathtub, dungeonbathtubr, bathtub, bathtubr, woodtoilet, dungeontoilet, woolcarpet, woolcarpetr, woolcarpetd, woolcarpetdr, leathercarpet, leathercarpetr, leathercarpetd, leathercarpetdr, woodbed, woodbed2, woodpressureplate, rockpressureplate, dungeonpressureplate, rocklever, rockleveractive, telepad, ledpanel, andgate, nandgate, orgate, norgate, xorgate, tflipflopgate, rslatchgate, timergate, buffergate, sensorgate, soundgate, stoneflametrap, dungeonflametrap, stonearrowtrap, dungeonarrowtrap, tnt, sunflowerseed4, sunflowerseed3, sunflowerseed2, sunflowerseed1, sunflowerseed, wildsunflower, sunflower, firemoneseed4, firemoneseed3, firemoneseed2, firemoneseed1, firemoneseed, wildfiremone, firemone, iceblossomseed4, iceblossomseed3, iceblossomseed2, iceblossomseed1, iceblossomseed, wildiceblossom, iceblossom, mushroomflower, mushroom4, mushroom3, mushroom2, mushroom1, mushroom, wildmushroom, compostbin, feedingtrough, feedingtrough2, surfacerock, surfacerockr, rock, ironorerock, copperorerock, goldorerock, sandstonerock, ironoresandstone, copperoresandstone, goldoresandstone, quartzsandstone, swamprock, ironoreswamp, copperoreswamp, goldoreswamp, snowrock, ironoresnow, copperoresnow, goldoresnow, clayrock, grass, swampgrass, snowpile3, snowpile2, snowpile1, snowpile0, ladderdown, ladderup, dungeonentrance, dungeonexit, coinstack, crate, snowcrate, swampcrate, vase, ancienttotem, settlementflag],\n\tobjects = [-1, 17, 17, 17, 17, 17, -1, 17, 17, 0, 0, 0, 17, 17, 17, 0, 0, 0, 0, 0, 17, 17, 0, 0, 0, 0, 0, 17, 17, 0, 0, 0, 0, 0, 17, 17, 17, 0, 0, 0, 17, 17, -1, 17, 17, 17, 17, 17, -1],\n\trotations = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\n}");
        ChestRoomSet defaultSet = ChestRoomSet.stone;
        ChestRoomSet set = chestRoomSets.length == 0 ? ChestRoomSet.stone : (ChestRoomSet)random.getOneOf(chestRoomSets);
        set.replacePreset(defaultSet, this);
        this.addCustomApply(this.openingApply(random, lootTable, lootRotation, set));
    }

    private Preset.CustomApply openingApply(final GameRandom random, final LootTable lootTable, final AtomicInteger lootRotation, final ChestRoomSet set) {
        return new Preset.CustomApply() {
            public Preset.UndoLogic applyToLevel(Level level, int presetX, int presetY) {
                int trapID = set.traps.isEmpty() ? -1 : (Integer)random.getOneOf(set.traps);
                boolean spawnTrap = trapID != -1 && random.getChance(0.9F);
                boolean trapDir = spawnTrap && random.nextBoolean();
                ArrayList<Runnable> validOpenings = new ArrayList();
                Runnable topOpening = () -> {
                    level.setObject(presetX + 3, presetY + 3, set.inventoryObject, 0);
                    level.setObject(presetX + 3, presetY, set.wallSet.doorClosed, 0);
                    if (spawnTrap) {
                        chasmchestroom.this.placeTrap(level, set.pressureplate, trapID, presetX + 3, presetY + 1, trapDir ? 1 : 3, 2);
                    }

                };
                Runnable rightOpening = () -> {
                    level.setObject(presetX + 3, presetY + 3, set.inventoryObject, 1);
                    level.setObject(presetX + 6, presetY + 3, set.wallSet.doorClosed, 1);
                    if (spawnTrap) {
                        chasmchestroom.this.placeTrap(level, set.pressureplate, trapID, presetX + 5, presetY + 3, trapDir ? 0 : 2, 2);
                    }

                };
                Runnable botOpening = () -> {
                    level.setObject(presetX + 3, presetY + 3, set.inventoryObject, 2);
                    level.setObject(presetX + 3, presetY + 6, set.wallSet.doorClosed, 2);
                    if (spawnTrap) {
                        chasmchestroom.this.placeTrap(level, set.pressureplate, trapID, presetX + 3, presetY + 5, trapDir ? 1 : 3, 2);
                    }

                };
                Runnable leftOpening = () -> {
                    level.setObject(presetX + 3, presetY + 3, set.inventoryObject, 3);
                    level.setObject(presetX, presetY + 3, set.wallSet.doorClosed, 3);
                    if (spawnTrap) {
                        chasmchestroom.this.placeTrap(level, set.pressureplate, trapID, presetX + 1, presetY + 3, trapDir ? 0 : 2, 2);
                    }

                };
                if (!level.getObject(presetX + 3, presetY - 1).isSolid) {
                    validOpenings.add(topOpening);
                }

                if (!level.getObject(presetX + 7, presetY + 3).isSolid) {
                    validOpenings.add(rightOpening);
                }

                if (!level.getObject(presetX + 3, presetY + 7).isSolid) {
                    validOpenings.add(botOpening);
                }

                if (!level.getObject(presetX - 1, presetY + 3).isSolid) {
                    validOpenings.add(leftOpening);
                }

                if (validOpenings.isEmpty()) {
                    random.runOneOf(new Runnable[]{topOpening, rightOpening, botOpening, leftOpening});
                } else {
                    ((Runnable)random.getOneOf(validOpenings)).run();
                }
                lootTable.applyToLevel(random, (Float)level.buffManager.getModifier(LevelModifiers.LOOT), level, presetX + 3, presetY + 3, new Object[]{level, lootRotation});
                return (level1, presetX1, presetY1) -> {
                };
            }

            public Preset.CustomApply mirrorX(int width) throws PresetMirrorException {
                return chasmchestroom.this.openingApply(random, lootTable, lootRotation, set);
            }

            public Preset.CustomApply mirrorY(int height) throws PresetMirrorException {
                return chasmchestroom.this.openingApply(random, lootTable, lootRotation, set);
            }

            public Preset.CustomApply rotate(PresetRotation angle, int width, int height) throws PresetRotateException {
                return chasmchestroom.this.openingApply(random, lootTable, lootRotation, set);
            }
        };
    }

    private void placeTrap(Level level, int plateID, int trapID, int plateX, int plateY, int dir, int trapRange) {
        level.setObject(plateX, plateY, plateID);
        dir %= 4;
        int i;
        if (dir == 0) {
            level.setObject(plateX, plateY - trapRange, trapID, 2);

            for(i = 0; i <= trapRange; ++i) {
                level.wireManager.setWire(plateX, plateY - i, 0, true);
            }
        } else if (dir == 1) {
            level.setObject(plateX + trapRange, plateY, trapID, 3);

            for(i = 0; i <= trapRange; ++i) {
                level.wireManager.setWire(plateX + i, plateY, 0, true);
            }
        } else if (dir == 2) {
            level.setObject(plateX, plateY + trapRange, trapID, 0);

            for(i = 0; i <= trapRange; ++i) {
                level.wireManager.setWire(plateX, plateY + i, 0, true);
            }
        } else {
            level.setObject(plateX - trapRange, plateY, trapID, 1);

            for(i = 0; i <= trapRange; ++i) {
                level.wireManager.setWire(plateX - i, plateY, 0, true);
            }
        }

    }
}