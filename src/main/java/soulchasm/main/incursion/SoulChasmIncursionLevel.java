package soulchasm.main.incursion;

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
import necesse.level.maps.incursion.AltarData;
import necesse.level.maps.incursion.BiomeExtractionIncursionData;
import necesse.level.maps.incursion.BiomeMissionIncursionData;
import necesse.level.maps.incursion.IncursionBiome;
import necesse.level.maps.presets.Preset;
import soulchasm.main.misc.presets.SoulCaveArenaPreset;
import soulchasm.main.misc.presets.SoulCaveBigShrinePreset;
import soulchasm.main.misc.presets.SoulCaveShrinePreset;

public class SoulChasmIncursionLevel extends IncursionLevel {
    public SoulChasmIncursionLevel(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
        super(identifier, width, height, worldEntity);
    }

    public SoulChasmIncursionLevel(LevelIdentifier identifier, BiomeMissionIncursionData incursion, WorldEntity worldEntity, AltarData altarData) {
        super(identifier, 150, 150, incursion, worldEntity);
        this.baseBiome = BiomeRegistry.getBiome("soulchasm");
        this.isCave = true;
        this.generateLevel(incursion, altarData);
    }

    public void generateLevel(BiomeMissionIncursionData incursionData, AltarData altarData) {
        CaveGeneration cg = new CaveGeneration(this, "soulcaverocktile", "soulcaverock");
        cg.random.setSeed(incursionData.getUniqueID());
        GameEvents.triggerEvent(new GenerateCaveLayoutEvent(this, cg), (e) -> cg.generateLevel(0.38F, 4, 3, 6));
        GameEvents.triggerEvent(new GeneratedCaveLayoutEvent(this, cg));
        PresetGeneration presets = new PresetGeneration(this);
        PresetGeneration entranceAndPerkPresets = new PresetGeneration(this);

        int centerX = this.tileWidth / 2;
        int centerY = this.tileHeight / 2;

        //STRUCTURES
        GameEvents.triggerEvent(new GenerateCaveStructuresEvent(this, cg, presets), (e) -> {
            cg.generateRandomCrates(0.03F, ObjectRegistry.getObjectID("chasmcrates"));
            GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.025F, 6, 7.0F, 20.0F, 4.0F, 8.0F, TileRegistry.getTileID("soulcavefloortile"), 0.7F, false);
            GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.04F, 2, 7.0F, 25.0F, 3.0F, 12.0F, TileRegistry.getTileID("meltedsouls"), 1.0F, true);
            GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.025F, 6, 7.0F, 20.0F, 1.0F, 3.0F, TileRegistry.getTileID("soulcavecracktile"), 0.8F, false);
            this.liquidManager.calculateShores();
            //Big shrine
            Preset shrineBig = new SoulCaveBigShrinePreset(cg.random);
            presets.findRandomValidPositionAndApply(cg.random, 5, shrineBig, 10, false, false);
            //Shrine
            int shrineAmount = cg.random.getIntBetween(3, 5);
            for(int ix = 0; ix < shrineAmount; ++ix) {
                Preset shrine = new SoulCaveShrinePreset(10,cg.random);
                presets.findRandomValidPositionAndApply(cg.random, 6, shrine, 10, true, true);
            }

            int spawnSize = 10;
            entranceAndPerkPresets.addOccupiedSpace(centerX - spawnSize / 2, centerY - spawnSize / 2, spawnSize, spawnSize);
            Preset arena = new SoulCaveArenaPreset(55, new GameRandom(this.getSeed()));
            arena.applyToLevelCentered(this, centerX, centerY);
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
        IncursionBiome.addReturnPortal(this, (float) (centerX * 32.0), (float) (centerY * 32.0));
        GenerationTools.checkValid(this);
        if (incursionData instanceof BiomeExtractionIncursionData) {
            cg.generateGuaranteedOreVeins(100, 8, 16, ObjectRegistry.getObjectID("crystalizedsoul"));
        }
        cg.generateGuaranteedOreVeins(75, 6, 12, ObjectRegistry.getObjectID("upgradeshardsoulcaverock"));
        cg.generateGuaranteedOreVeins(75, 6, 12, ObjectRegistry.getObjectID("alchemyshardsoulcaverock"));
        this.generateUpgradeAndAlchemyVeinsBasedOnPerks(altarData, cg, "upgradeshardsoulcaverock", "alchemyshardsoulcaverock", cg.random);
        GameEvents.triggerEvent(new GeneratedCaveOresEvent(this, cg));
    }
    public LootTable getCrateLootTable() {
        return LootTablePresets.incursionCrate;
    }
}
