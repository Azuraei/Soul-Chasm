package soulchasm.main.Misc;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.seasons.GameSeasons;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.ExoticMerchantHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.HumanShop;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import necesse.entity.mobs.friendly.human.humanShop.ShopManager;
import necesse.inventory.InventoryItem;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;
import java.util.Arrays;

@ModConstructorPatch(target = ExoticMerchantHumanMob.class, arguments = {})
public class ExoticMerchantMethodPatch {

    @Advice.OnMethodExit
    static void onExit(@Advice.This ExoticMerchantHumanMob merchantMob) {
        String[] plushies = new String[]{"argemiaplushieitem", "v1plushieitem", "fairplushieitem", "fumoplushieitem", "devplushieitem"};
        GameRandom random = new GameRandom();
        merchantMob.shop.addSellingItem("plushie", new SellingShopItem(2, 2)).setItem(random.getOneOf(plushies)).setRandomPrice(100, 200);
        merchantMob.shop.addSellingItem("tobeblindfold", new SellingShopItem()).setRandomPrice(100, 200);
    }
}