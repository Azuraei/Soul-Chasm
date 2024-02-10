package soulchasm.main.Misc.Incursion;

import necesse.engine.network.server.Server;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.util.TicketSystemList;
import necesse.engine.world.WorldEntity;
import necesse.engine.world.worldData.incursions.GatewayTabletData;
import necesse.engine.world.worldData.incursions.IncursionDataStats;
import necesse.inventory.item.Item;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.recipe.Ingredient;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.incursion.*;

import java.util.Collection;
import java.util.Collections;
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
        return new LootTable(LootItem.between("soulessence", 20, 25));
    }

    public void addIncursions(Collection<IncursionData> list, GatewayTabletData tablet, GameRandom random) {
        float difficultyPercent = random.getFloatBetween(0.0F, 1.0F);
        float difficulty = GameMath.lerp(difficultyPercent, 0.6F, 1.4F);
        Ingredient cost = new Ingredient("slimeessence", getRandomCost(random, difficultyPercent, 5, 20, 5));
        IncursionDataStats stats = tablet.openedStats.getData(this);
        TicketSystemList<Supplier<IncursionData>> weights = new TicketSystemList<>();
        weights.addObject(stats.getCount(BiomeHuntIncursionData.class) + 1, () -> new BiomeHuntIncursionData(difficulty, this, cost));
        weights.addObject(stats.getCount(BiomeExtractionIncursionData.class) + 1, () -> new BiomeExtractionIncursionData(difficulty, this, cost));
        list.add((IncursionData)((Supplier<?>)weights.reversed().getRandomObject(random)).get());
    }

    public IncursionLevel getNewIncursionLevel(LevelIdentifier identifier, BiomeMissionIncursionData incursion, Server server, WorldEntity worldEntity) {
        return new soulcavernincursionlevel(identifier, incursion, worldEntity);
    }
}
