package soulchasm.main.Buffs.ToolBuffs;

import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class soulscythebuff extends Buff {
    public soulscythebuff() {
        this.isVisible = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {}

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event) {
        super.onHasAttacked(buff, event);
        Mob target = event.target;
        target.buffManager.addBuff(new ActiveBuff("soulbleedstackbuff", buff.owner, 8.0F, buff.owner), true);
    }
}