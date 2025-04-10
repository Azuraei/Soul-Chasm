package soulchasm.main.Misc;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.friendly.human.humanShop.ExoticMerchantHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import necesse.inventory.InventoryItem;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;
import java.util.Arrays;

@ModConstructorPatch(target = ExoticMerchantHumanMob.class, arguments = {})
public class ExoticMerchantMethodPatch {

    @Advice.OnMethodExit
    static void onExit(@Advice.This ExoticMerchantHumanMob merchantMob) {
        //ArrayList<String> plushie = new ArrayList<>(Arrays.asList("argemiaplushieitem", "fairplushieitem", "v1plushieitem", "fumoplushieitem", "devplushieitem"));
        //merchantMob.shop.addSellingItem("randomplushie", new SellingShopItem(2, 1).setRandomPrice(100, 200).setItem((random, client, m) -> new InventoryItem(plushie.get(random.nextInt(plushie.size())))));
        //merchantMob.shop.addSellingItem("tobeblindfold", new SellingShopItem()).setRandomPrice(100, 200).addRequirement((random, client, m, blackboard) -> client.characterStats().completed_incursions.getData("soulchasmincursionbiome").getTotal() > 0);
        //System.out.println("Exited constructor: " + merchantMob.getStringID());
    }
}