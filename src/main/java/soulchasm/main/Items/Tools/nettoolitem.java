package soulchasm.main.Items.Tools;

import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.friendly.HoneyBeeMob;
import necesse.entity.mobs.friendly.QueenBeeMob;
import necesse.inventory.item.toolItem.miscToolItem.NetToolItem;
import soulchasm.main.Mobs.Passive.firefly;
import soulchasm.main.Mobs.Passive.wisp;

public class nettoolitem extends NetToolItem {
    public boolean canHitMob(Mob mob, ToolItemMobAbilityEvent event) {
        return mob instanceof wisp || mob instanceof firefly || mob instanceof HoneyBeeMob || mob instanceof QueenBeeMob;
    }
}
