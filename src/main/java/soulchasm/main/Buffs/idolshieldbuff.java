package soulchasm.main.Buffs;

import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import soulchasm.main.Misc.Events.idolshieldvisualevent;

public class idolshieldbuff extends Buff {

    public idolshieldbuff() {
        this.isVisible = false;
    }

    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        Mob owner = buff.owner;
        Mob attacker = null;
        if(buff.getAttacker()!=null){
            attacker = buff.getAttacker().getAttackOwner();
        }
        if(owner!=null && attacker!=null){
            idolshieldvisualevent event = new idolshieldvisualevent(attacker, owner);
            buff.owner.getLevel().entityManager.addLevelEvent(event);
        }
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 0.25F);
        buff.setModifier(BuffModifiers.SLOW, 0.2F);
        buff.setModifier(BuffModifiers.ATTACK_SPEED, 0.2F);
        buff.setModifier(BuffModifiers.KNOCKBACK_INCOMING_MOD, 0.0F);
    }
}
