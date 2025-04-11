package soulchasm.main.Misc;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.seasons.GameSeasons;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.ExoticMerchantHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.HumanShop;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import necesse.inventory.InventoryItem;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;
import java.util.Arrays;

@ModConstructorPatch(target = ExoticMerchantHumanMob.class, arguments = {})
public class ExoticMerchantMethodPatch {

    @Advice.OnMethodExit
    static void onExit(@Advice.This ExoticMerchantHumanMob merchantMob) {
        SellingShopItem.ShopItemRequirement hasCompletedChasm = new SellingShopItem.ShopItemRequirement() {
            public boolean test(GameRandom random, ServerClient client, HumanShop mob, GameBlackboard blackboard) {
                System.out.print("debug1");
                return client.characterStats().completed_incursions.getData("soulchasmincursionbiome").getTotal() > 0;
            }
        };

        ArrayList<String> plushieList = new ArrayList<>(Arrays.asList("argemiaplushieitem", "v1plushieitem", "fairplushieitem", "fumoplushieitem", "devplushieitem"));
        GameRandom random = new GameRandom(merchantMob.getShopSeed());
        InventoryItem item = new InventoryItem(random.getOneOf(plushieList));
        merchantMob.shop.addSellingItem("plushie", new SellingShopItem(2, 2)).setItem(item).setRandomPrice(100, 200);
        merchantMob.shop.addSellingItem("tobeblindfold", new SellingShopItem()).setRandomPrice(100, 200).addRequirement(hasCompletedChasm);
    }
}