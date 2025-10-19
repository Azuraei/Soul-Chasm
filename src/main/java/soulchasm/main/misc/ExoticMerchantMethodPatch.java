package soulchasm.main.misc;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.ExoticMerchantHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(
        target = ExoticMerchantHumanMob.class,
        arguments = {}
)
public class ExoticMerchantMethodPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This ExoticMerchantHumanMob merchantMob) {
        String[] plushies = new String[]{"argemiaplushieitem", "v1plushieitem", "fairplushieitem", "fumoplushieitem", "devplushieitem", "sharkplushieitem", "rockplushieitem"};
        GameRandom random = new GameRandom();
        merchantMob.shop.addSellingItem("plushie", new SellingShopItem(5, 2)).setItem(random.getOneOf(plushies)).setRandomPrice(100, 200);
        merchantMob.shop.addSellingItem("plushie_extra", new SellingShopItem(2, 2)).setItem(random.getOneOf(plushies)).setRandomPrice(100, 200).addRandomAvailableRequirement(0.25F);
        merchantMob.shop.addSellingItem("tobeblindfold", new SellingShopItem()).setRandomPrice(100, 200).addRandomAvailableRequirement(0.15F);
    }
}