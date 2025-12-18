package soulchasm.main.misc;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.Mob;
import necesse.inventory.item.toolItem.miscToolItem.NetToolItem;
import net.bytebuddy.asm.Advice;
import soulchasm.main.mobs.friendly.Wisp;


@ModMethodPatch(target = NetToolItem.class, name = "canHitMob", arguments = {Mob.class, ToolItemMobAbilityEvent.class})
public class NetToolMethodPatch {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This NetToolItem netToolItem, @Advice.Argument(0) Mob mob, @Advice.Argument(1) ToolItemMobAbilityEvent toolItemEvent) {
        return false;
    }

    @Advice.OnMethodExit
    static void onExit(@Advice.This NetToolItem netToolItem, @Advice.Argument(0) Mob mob, @Advice.Argument(1) ToolItemMobAbilityEvent toolItemEvent, @Advice.Return(readOnly = false) boolean result) {
        if (mob instanceof Wisp) {
            result = true;
        }
    }
}
