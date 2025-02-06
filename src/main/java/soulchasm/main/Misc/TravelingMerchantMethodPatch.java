package soulchasm.main.Misc;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.TravelingMerchantMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.level.maps.levelData.villageShops.ShopItem;
import necesse.level.maps.levelData.villageShops.VillageShopsData;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;

@ModMethodPatch(target = TravelingMerchantMob.class, name = "getShopItems", arguments = {VillageShopsData.class, ServerClient.class})
public class TravelingMerchantMethodPatch {

    @Advice.OnMethodExit
    static void onExit(@Advice.This TravelingMerchantMob mob, @Advice.Argument(0) VillageShopsData data, @Advice.Argument(1) ServerClient client, @Advice.Return(readOnly = false) ArrayList<ShopItem> items) {
        GameRandom seededRandom1 = new GameRandom(mob.getShopSeed());
        if(client.characterStats().completed_incursions.getData("soulchasmincursionbiome").getTotal() > 0 && seededRandom1.getChance(0.1F)){
            GameRandom random = new GameRandom(mob.getShopSeed()).nextSeeded(42);
            String item = "tobeblindfold";
            InventoryItem inventoryItem = new InventoryItem(ItemRegistry.getItem(item));
            items.add(ShopItem.item(item, (int) inventoryItem.getBrokerValue() + random.getIntBetween(100, 200)));
        }
        if(client.characterStats().completed_incursions.getTotal() > 0 && seededRandom1.getChance(0.2F)){
            GameRandom random = new GameRandom(mob.getShopSeed()).nextSeeded(42);
            String plushie = random.getOneOf("argemiaplushieitem", "fairplushieitem", "v1plushieitem", "fumoplushieitem", "devplushieitem");
            InventoryItem inventoryItem = new InventoryItem(ItemRegistry.getItem(plushie));
            items.add(ShopItem.item(plushie, (int) inventoryItem.getBrokerValue() + random.getIntBetween(100, 200)));
        }
    }
}
