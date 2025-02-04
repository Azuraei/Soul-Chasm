package soulchasm.main.Misc.Incursion;

import necesse.engine.GameEvents;
import necesse.engine.events.worldGeneration.*;
import necesse.engine.registries.BiomeRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.generationModules.CaveGeneration;
import necesse.level.maps.generationModules.GenerationTools;
import necesse.level.maps.generationModules.PresetGeneration;
import necesse.level.maps.incursion.BiomeExtractionIncursionData;
import necesse.level.maps.incursion.BiomeMissionIncursionData;
import necesse.level.maps.presets.Preset;
import necesse.level.maps.presets.RandomCaveChestRoom;
import soulchasm.main.Misc.Presets.soulcavearenapreset;
import soulchasm.main.Misc.Presets.soulcaveshrinepreset;
import soulchasm.main.Misc.Presets.soulcavesmallruinspreset;

import java.util.concurrent.atomic.AtomicInteger;

import static soulchasm.SoulChasm.chasmChestRoomSet;

public class soulchasmincursionlevel extends IncursionLevel {
    public soulchasmincursionlevel(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
        super(identifier, width, height, worldEntity);
    }

    public soulchasmincursionlevel(LevelIdentifier identifier, BiomeMissionIncursionData incursion, WorldEntity worldEntity) {
        super(identifier, 150, 150, incursion, worldEntity);
        this.biome = BiomeRegistry.getBiome("soulcavern");
        this.isCave = true;
        this.generateLevel(incursion);
    }

    public void generateLevel(BiomeMissionIncursionData incursionData) {
        CaveGeneration cg = new CaveGeneration(this, "soulcaverocktile", "soulcaverock");
        cg.random.setSeed(incursionData.getUniqueID());
        GameEvents.triggerEvent(new GenerateCaveLayoutEvent(this, cg), (e) -> cg.generateLevel(0.38F, 4, 3, 6));
        GameEvents.triggerEvent(new GeneratedCaveLayoutEvent(this, cg));
        //STRUCTURES
        PresetGeneration presets = new PresetGeneration(this);
        GameEvents.triggerEvent(new GenerateCaveStructuresEvent(this, cg, presets), (e) -> {
            cg.generateRandomCrates(0.03F, ObjectRegistry.getObjectID("chasmcrates"));
            GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.04F, 2, 7.0F, 25.0F, 3.0F, 12.0F, TileRegistry.getTileID("meltedsouls"), 1.0F, true);
            cg.generateTileVeins(0.1F, 6, 12, TileRegistry.getTileID("soulcavecracktile"), ObjectRegistry.getObjectID("air"));
            this.liquidManager.calculateShores();
            /*
            int smallRuinAmount = cg.random.getIntBetween(8, 15);
            for(int ix = 0; ix < smallRuinAmount; ++ix) {
                Preset smallRuin = new soulcavesmallruinspreset(8,cg.random);
                presets.findRandomValidPositionAndApply(cg.random, 6, smallRuin, 10, true, true);
            }
            AtomicInteger chestRoomRotation = new AtomicInteger();
            int chestRoomAmount = cg.random.getIntBetween(10, 15);
            for(int ix = 0; ix < chestRoomAmount; ++ix) {
                Preset chestRoom = new RandomCaveChestRoom(cg.random, chasmChestLootTable, chestRoomRotation, chasmChestRoomSet);
                chestRoom.replaceTile(TileRegistry.getTileID("soulcavefloortile"), cg.random.getOneOf(TileRegistry.getTileID("soulcavefloortile"), TileRegistry.getTileID("soulcavebrickfloortile")));
                presets.findRandomValidPositionAndApply(cg.random, 6, chestRoom, 10, true, true);
            }
             */
            int shrineAmount = cg.random.getIntBetween(5, 10);
            for(int ix = 0; ix < shrineAmount; ++ix) {
                Preset shrine = new soulcaveshrinepreset(10,cg.random);
                presets.findRandomValidPositionAndApply(cg.random, 6, shrine, 10, true, true);
            }
            soulchasmincursionbiome.generateEntrance(this, cg.random, 1, cg.rockTile, "soulcavebrickfloortile", "soulcavefloortile", "soullantern");
            Preset arena = new soulcavearenapreset(55, new GameRandom(this.getSeed()));
            arena.applyToLevelCentered(this, this.width / 2, this.height / 2);
        });
        GameEvents.triggerEvent(new GeneratedCaveStructuresEvent(this, cg, presets));
        //DECORATIONS
        GameEvents.triggerEvent(new GenerateCaveMiniBiomesEvent(this, cg), (e) -> {
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("soulcaverocks"), 0.005F);
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("soulcaverockssmall"), 0.01F);
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("soulcavedecorations"), 0.008F);
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("soulcrystalbig"), 0.006F);
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("soullantern"), 0.0015F);
        });
        GameEvents.triggerEvent(new GeneratedCaveMiniBiomesEvent(this, cg));
        //INCURSION
        GenerationTools.checkValid(this);
        if (incursionData instanceof BiomeExtractionIncursionData) {
            cg.generateGuaranteedOreVeins(100, 8, 16, ObjectRegistry.getObjectID("crystalizedsoul"));
        }
        cg.generateGuaranteedOreVeins(100, 6, 12, ObjectRegistry.getObjectID("upgradeshardsoulcaverock"));
        cg.generateGuaranteedOreVeins(100, 6, 12, ObjectRegistry.getObjectID("alchemyshardsoulcaverock"));
        GameEvents.triggerEvent(new GeneratedCaveOresEvent(this, cg));
    }
    public LootTable getCrateLootTable() {
        return LootTablePresets.incursionCrate;
    }
}
