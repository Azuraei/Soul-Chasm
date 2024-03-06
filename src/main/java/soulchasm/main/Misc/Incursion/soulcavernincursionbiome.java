package soulchasm.main.Misc.Incursion;

import necesse.engine.network.server.Server;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.util.TicketSystemList;
import necesse.engine.world.WorldEntity;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.miscItem.GatewayTabletItem;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootList;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.incursion.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class soulcavernincursionbiome extends IncursionBiome {
    public soulcavernincursionbiome() {
        super("souldragonhead");
    }

    public Collection<Item> getExtractionItems() {
        return Collections.singleton(ItemRegistry.getItem("crystalizedsouloreitem"));
    }

    public LootTable getHuntDrop() {
        return new LootTable(new ChanceLootItem(0.8F, "soulcoreitem"));
    }

    public LootTable getBossDrop() {
        return new LootTable(LootItem.between("soulessence", 20, 25), new LootItemInterface() {
            public void addPossibleLoot(LootList list, Object... extra) {
                list.add("gatewaytablet");
            }
            public void addItems(List<InventoryItem> list, GameRandom random, float lootMultiplier, Object... extra) {
                InventoryItem gatewayTablet = new InventoryItem("gatewaytablet");
                int tier = soulcavernincursionbiome.this.tierLevel + 1;
                if (tier > 3) {
                    tier = 3;
                }

                GatewayTabletItem.initializeGatewayTablet(gatewayTablet, random, tier);
                list.add(gatewayTablet);
            }
        });
    }

    public TicketSystemList<Supplier<IncursionData>> getAvailableIncursions(int tabletTier) {
        TicketSystemList<Supplier<IncursionData>> system = new TicketSystemList<>();
        system.addObject(100, () -> new BiomeHuntIncursionData(1.0F, this, tabletTier));
        system.addObject(100, () -> new BiomeExtractionIncursionData(1.0F, this, tabletTier));
        return system;
    }



    public IncursionLevel getNewIncursionLevel(LevelIdentifier identifier, BiomeMissionIncursionData incursion, Server server, WorldEntity worldEntity) {
        return new soulcavernincursionlevel(identifier, incursion, worldEntity);
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
}
