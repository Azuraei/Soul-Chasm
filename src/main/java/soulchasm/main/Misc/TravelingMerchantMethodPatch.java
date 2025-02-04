package soulchasm.main.Misc;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.TravelingMerchantMob;
import necesse.level.maps.levelData.villageShops.ShopItem;
import necesse.level.maps.levelData.villageShops.VillageShopsData;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;

@ModMethodPatch(target = TravelingMerchantMob.class, name = "getShopItems", arguments = {VillageShopsData.class, ServerClient.class})
public class TravelingMerchantMethodPatch {

    @Advice.OnMethodExit
    static void onExit(@Advice.This TravelingMerchantMob mob, @Advice.Argument(0) VillageShopsData data, @Advice.Argument(1) ServerClient client, @Advice.Return(readOnly = false) ArrayList<ShopItem> items) {
        GameRandom seededRandom1 = new GameRandom(mob.getShopSeed());
        if(client.characterStats().completed_incursions.getData("soulchasmincursionbiome").getTotal() > 0){
            GameRandom random = new GameRandom(mob.getShopSeed()).nextSeeded(42);
            items.add(ShopItem.item("tobeblindfold", random.getIntBetween(500, 600)));
        }
        if(client.characterStats().completed_incursions.getData("soulchasmincursionbiome").getTotal()>0 && seededRandom1.getChance(0.5F)){
            GameRandom random = new GameRandom(mob.getShopSeed()).nextSeeded(42);
            String plushie = random.getOneOf("argemiaplushieobject", "fairplushieobject", "v1plushieobject");
            items.add(ShopItem.item(plushie, random.getIntBetween(500, 1000)));
        }
    }
}
