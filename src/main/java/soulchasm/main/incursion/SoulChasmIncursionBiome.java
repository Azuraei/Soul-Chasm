package soulchasm.main.incursion;

import necesse.engine.network.server.Server;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.util.TicketSystemList;
import necesse.engine.world.WorldEntity;
import necesse.entity.mobs.Mob;
import necesse.entity.objectEntity.FallenAltarObjectEntity;
import necesse.inventory.item.Item;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.incursion.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class SoulChasmIncursionBiome extends IncursionBiome {
    public SoulChasmIncursionBiome() {
        super("chasmdragon");
    }

    public Collection<Item> getExtractionItems(IncursionData incursionData) {
        return Collections.singleton(ItemRegistry.getItem("crystalizedsouloreitem"));
    }

    public LootTable getHuntDrop(IncursionData incursionData) {
        return new LootTable(new ChanceLootItem(0.7F, "soulcore"));
    }

    public LootTable getBossDrop(IncursionData incursionData) {
        return new LootTable(super.getBossDrop(incursionData), LootItem.between("soulessence", 20, 25), new ChanceLootItem(0.1F, "carkeys"));
    }

    public TicketSystemList<Supplier<IncursionData>> getAvailableIncursions(int tabletTier, IncursionData incursionData) {
        TicketSystemList<Supplier<IncursionData>> system = new TicketSystemList();
        int huntTickets = 100;
        int extractionTickets = 100;
        if (incursionData != null) {
            huntTickets = (int)((float)huntTickets * (Float)incursionData.nextIncursionModifiers.getModifier(IncursionDataModifiers.MODIFIER_HUNT_DROPS));
            extractionTickets = (int)((float)extractionTickets * (Float)incursionData.nextIncursionModifiers.getModifier(IncursionDataModifiers.MODIFIER_EXTRACTION_DROPS));
        }

        system.addObject(huntTickets, (Supplier)() -> new BiomeHuntIncursionData(1.0F, this, tabletTier));
        system.addObject(extractionTickets, (Supplier)() -> new BiomeExtractionIncursionData(1.0F, this, tabletTier));
        return system;
    }

    public IncursionLevel getNewIncursionLevel(FallenAltarObjectEntity altar, LevelIdentifier identifier, BiomeMissionIncursionData incursion, Server server, WorldEntity worldEntity, AltarData altarData) {
        return new SoulChasmIncursionLevel(identifier, incursion, worldEntity, altarData);
    }

    public ArrayList<Color> getFallenAltarGatewayColorsForBiome() {
        ArrayList<Color> gatewayColors = new ArrayList<>();
        gatewayColors.add(new Color(0, 100, 130));
        gatewayColors.add(new Color(0, 188, 252));
        gatewayColors.add(new Color(0, 151, 212));
        gatewayColors.add(new Color(0, 209, 255));
        gatewayColors.add(new Color(102, 255, 255));
        gatewayColors.add(new Color(236, 240, 255));
        return gatewayColors;
    }

    public LootTable getExtraIncursionDrops(Mob mob) {
        LootTable mobDrops = super.getExtraIncursionDrops(mob);
        return mob.isBoss() ? new LootTable(mobDrops, new LootItem("soulessence", 1)) : mobDrops;
    }
}
