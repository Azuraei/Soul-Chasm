package soulchasm.main.Buffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class idolshieldbuff extends Buff {

    public idolshieldbuff() {
        this.isVisible = true;
        this.isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 0.5F);
        buff.setModifier(BuffModifiers.KNOCKBACK_INCOMING_MOD, 0.0F);
    }
}
