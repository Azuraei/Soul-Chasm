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

import static soulchasm.SoulChasm.SoulCaveChestRoomSet;
import static soulchasm.SoulChasm.soulcavechestloottable;

public class soulchasmincursionlevel extends IncursionLevel {

    public soulchasmincursionlevel(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
        super(identifier, width, height, worldEntity);
    }

    public soulchasmincursionlevel(LevelIdentifier identifier, BiomeMissionIncursionData incursion, WorldEntity worldEntity) {
        super(identifier, 300, 300, incursion, worldEntity);
        this.biome = BiomeRegistry.getBiome("soulcavern");
        this.isCave = true;
        this.generateLevel(incursion);
    }

    public void generateLevel(BiomeMissionIncursionData incursionData) {
        CaveGeneration cg = new CaveGeneration(this, "soulcaverocktile", "soulcaverock");
        cg.random.setSeed(incursionData.getUniqueID());
        GameEvents.triggerEvent(new GenerateCaveLayoutEvent(this, cg), (e) -> {
            cg.generateLevel(0.40F, 4, 3, 6);
        });
        GameEvents.triggerEvent(new GeneratedCaveLayoutEvent(this, cg));
        GameEvents.triggerEvent(new GenerateCaveMiniBiomesEvent(this, cg), (e) -> {
            GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.03F, 2, 7.0F, 20.0F, 3.0F, 8.0F, TileRegistry.getTileID("meltedsouls"), 1.0F, true);
            this.liquidManager.calculateShores();
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("soulcaverocks"), 0.005F);
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("soulcaverockssmall"), 0.01F);
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("soulcrystalbig"), 0.005F);
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("soullanternobject"), 0.0006F);
        });
        GameEvents.triggerEvent(new GeneratedCaveMiniBiomesEvent(this, cg));
        GameEvents.triggerEvent(new GenerateCaveOresEvent(this, cg), (e) -> {
            cg.generateOreVeins(0.16F, 3, 6, ObjectRegistry.getObjectID("crystalizedsoul"));
        });
        GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.05F, 4, 3.0F, 6.0F, 2.0F, 4.0F, TileRegistry.getTileID("soulcavecracktile"), 0.8F, false);
        GameEvents.triggerEvent(new GeneratedCaveOresEvent(this, cg));

        PresetGeneration presets = new PresetGeneration(this);
        GameEvents.triggerEvent(new GenerateCaveStructuresEvent(this, cg, presets), (e) -> {

            int smallruinsAmount = cg.random.getIntBetween(8, 15);
            for(int ix = 0; ix < smallruinsAmount; ++ix) {
                Preset smallruins = new soulcavesmallruinspreset(8,cg.random);
                presets.findRandomValidPositionAndApply(cg.random, 6, smallruins, 10, true, true);
            }

            int shrineAmount = cg.random.getIntBetween(5, 10);
            for(int ix = 0; ix < shrineAmount; ++ix) {
                Preset shrine = new soulcaveshrinepreset(10,cg.random);
                presets.findRandomValidPositionAndApply(cg.random, 6, shrine, 10, true, true);
            }

            AtomicInteger chestRoomRotation = new AtomicInteger();
            int chestRoomAmount = cg.random.getIntBetween(10, 15);

            for(int ix = 0; ix < chestRoomAmount; ++ix) {
                Preset chestRoom = new RandomCaveChestRoom(cg.random, soulcavechestloottable, chestRoomRotation, SoulCaveChestRoomSet);
                chestRoom.replaceTile(TileRegistry.getTileID("soulcavefloortile"), cg.random.getOneOf(TileRegistry.getTileID("soulcavefloortile"), TileRegistry.getTileID("soulcavebrickfloortile")));
                presets.findRandomValidPositionAndApply(cg.random, 6, chestRoom, 10, true, true);
            }

            cg.generateRandomCrates(0.03F, ObjectRegistry.getObjectID("chasmcrates"));

            GameRandom random = new GameRandom(this.getSeed());
            (new soulcavearenapreset(65, random)).applyToLevelCentered(this, this.width / 2, this.height / 2);

        });

        soulchasmincursionbiome.generateEntrance(this, cg.random, 1, cg.rockTile, "soulcavebrickfloortile", "soulcavefloortile", "soullanternobject");
        if (incursionData instanceof BiomeExtractionIncursionData) {
            cg.generateGuaranteedOreVeins(100, 12, 18, ObjectRegistry.getObjectID("crystalizedsoul"));
        }
        cg.generateGuaranteedOreVeins(100, 6, 12, ObjectRegistry.getObjectID("upgradeshardsoulcaverock"));
        cg.generateGuaranteedOreVeins(100, 6, 12, ObjectRegistry.getObjectID("alchemyshardsoulcaverock"));
        GameEvents.triggerEvent(new GeneratedCaveStructuresEvent(this, cg, presets));
        GenerationTools.checkValid(this);
    }
    public LootTable getCrateLootTable() {
        return LootTablePresets.incursionCrate;
    }
}
