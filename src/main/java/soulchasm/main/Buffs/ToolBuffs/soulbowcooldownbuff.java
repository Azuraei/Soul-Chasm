package soulchasm.main.Buffs.ToolBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class soulbowcooldownbuff extends Buff {

    public soulbowcooldownbuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        this.canCancel = false;
        this.isImportant = true;
        this.isVisible = true;
    }
}