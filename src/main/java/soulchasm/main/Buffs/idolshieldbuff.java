package soulchasm.main.Buffs;

import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import soulchasm.main.Misc.idolshieldvisualevent;

public class idolshieldbuff extends Buff {

    public idolshieldbuff() {
        this.isVisible = true;
        this.isImportant = true;
    }

    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        Mob owner = buff.owner;
        Mob attacker = buff.getAttacker().getAttackOwner();
        if(owner!=null && attacker!=null){
            idolshieldvisualevent event = new idolshieldvisualevent(attacker, owner);
            buff.owner.getLevel().entityManager.addLevelEvent(event);
        }
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 0.5F);
        buff.setModifier(BuffModifiers.KNOCKBACK_INCOMING_MOD, 0.0F);
    }
}