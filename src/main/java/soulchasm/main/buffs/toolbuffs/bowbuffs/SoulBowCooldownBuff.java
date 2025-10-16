package soulchasm.main.buffs.toolbuffs.bowbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class SoulBowCooldownBuff extends Buff {

    public SoulBowCooldownBuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        this.shouldSave = false;
        this.canCancel = false;
        this.isImportant = true;
        this.isVisible = true;
    }

    @Override
    public boolean isVisible(ActiveBuff buff) {
        return !buff.owner.buffManager.hasBuff("soulbowbuff");
    }
}