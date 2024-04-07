package soulchasm.main.Misc.Others;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.TravelingMerchantMob;
import necesse.level.maps.levelData.villageShops.ShopItem;
import necesse.level.maps.levelData.villageShops.VillageShopsData;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;

@ModMethodPatch(target = TravelingMerchantMob.class, name = "getShopItems", arguments = {VillageShopsData.class, ServerClient.class})
public class travelingmerchantmethodpatch {

    @Advice.OnMethodExit
    static void onExit(@Advice.This TravelingMerchantMob mob, @Advice.Argument(0) VillageShopsData data, @Advice.Argument(1) ServerClient client, @Advice.Return(readOnly = false) ArrayList<ShopItem> items) {
        if(client.characterStats().completed_incursions.getOpened("soulchasmincursionbiome") > 0){
            GameRandom random = new GameRandom(mob.getShopSeed()).nextSeeded(42);
            items.add(ShopItem.item("tobeblindfold", random.getIntBetween(500, 600)));
        }
    }
}
