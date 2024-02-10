package soulchasm.main.Misc.MethodPatch;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.levelEvent.toolItemEvent.ToolItemEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.friendly.HoneyBeeMob;
import necesse.entity.mobs.friendly.QueenBeeMob;
import necesse.inventory.item.toolItem.miscToolItem.NetToolItem;
import soulchasm.main.Mobs.Passive.firefly;
import soulchasm.main.Mobs.Passive.wisp;
import net.bytebuddy.asm.Advice;


@ModMethodPatch(target = NetToolItem.class, name = "canHitMob", arguments = {Mob.class, ToolItemEvent.class})
public class nettoolmethodpatch {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This NetToolItem netToolItem, @Advice.Argument(0) Mob mob, @Advice.Argument(1) ToolItemEvent event) {
        return true;
    }

    @Advice.OnMethodExit
    static void onExit(@Advice.This NetToolItem netToolItem, @Advice.Argument(0) Mob mob, @Advice.Argument(1) ToolItemEvent event, @Advice.Return(readOnly=false)Boolean target){
        target = mob instanceof wisp || mob instanceof firefly || mob instanceof HoneyBeeMob || mob instanceof QueenBeeMob;

    }
}
