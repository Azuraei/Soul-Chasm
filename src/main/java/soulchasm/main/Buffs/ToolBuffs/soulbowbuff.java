package soulchasm.main.Buffs.ToolBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class soulbowbuff extends Buff {

    public soulbowbuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        this.shouldSave = false;
        this.overrideSync = true;
        this.canCancel = false;
        this.isImportant = true;
        this.isVisible = true;
    }
}