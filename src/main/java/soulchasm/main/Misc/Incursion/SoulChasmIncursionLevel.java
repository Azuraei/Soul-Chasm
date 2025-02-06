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
import soulchasm.main.Misc.Presets.SoulCaveArenaPreset;
import soulchasm.main.Misc.Presets.SoulCaveBigShrinePreset;
import soulchasm.main.Misc.Presets.SoulCaveShrinePreset;

public class SoulChasmIncursionLevel extends IncursionLevel {
    public SoulChasmIncursionLevel(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
        super(identifier, width, height, worldEntity);
    }

    public SoulChasmIncursionLevel(LevelIdentifier identifier, BiomeMissionIncursionData incursion, WorldEntity worldEntity) {
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
            GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.01F, 3, 7.0F, 15.0F, 3.0F, 8.0F, TileRegistry.getTileID("soulcavefloortile"), 0.8F, false);
            GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.04F, 2, 7.0F, 25.0F, 3.0F, 12.0F, TileRegistry.getTileID("meltedsouls"), 1.0F, true);
            cg.generateTileVeins(0.1F, 6, 12, TileRegistry.getTileID("soulcavecracktile"), ObjectRegistry.getObjectID("air"));
            this.liquidManager.calculateShores();

            Preset shrineBig = new SoulCaveBigShrinePreset(cg.random);
            presets.findRandomValidPositionAndApply(cg.random, 5, shrineBig, 10, false, false);

            int shrineAmount = cg.random.getIntBetween(3, 5);
            for(int ix = 0; ix < shrineAmount; ++ix) {
                Preset shrine = new SoulCaveShrinePreset(10,cg.random);
                presets.findRandomValidPositionAndApply(cg.random, 6, shrine, 10, true, true);
            }
            SoulChasmIncursionBiome.generateEntrance(this, cg.random, 1, cg.rockTile, "soulcavebrickfloortile", "soulcavefloortile", "soullantern");
            Preset arena = new SoulCaveArenaPreset(55, new GameRandom(this.getSeed()));
            arena.applyToLevelCentered(this, this.width / 2, this.height / 2);
        });
        GameEvents.triggerEvent(new GeneratedCaveStructuresEvent(this, cg, presets));
        //DECORATIONS
        GameEvents.triggerEvent(new GenerateCaveMiniBiomesEvent(this, cg), (e) -> {
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("soulcaverocks"), 0.005F);
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("soulcaverocksmall"), 0.01F);
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
