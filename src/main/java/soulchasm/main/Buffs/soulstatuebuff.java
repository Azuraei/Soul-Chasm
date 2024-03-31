package soulchasm.main.Buffs;

import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import soulchasm.main.Misc.Others.Events.idolshieldvisualevent;

public class soulstatuebuff extends Buff {

    public soulstatuebuff() {
        this.isVisible = true;
        this.isImportant = true;
    }

    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        if(buff.getAttacker()!=null){
            Mob owner = buff.owner;
            Mob attacker = buff.getAttacker().getAttackOwner();
            if(owner!=null && attacker!=null){
                idolshieldvisualevent event = new idolshieldvisualevent(attacker, owner);
                buff.owner.getLevel().entityManager.addLevelEvent(event);
            }
        }
    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.2F);
        buff.setModifier(BuffModifiers.CRIT_CHANCE, 0.2F);
        buff.setModifier(BuffModifiers.CRIT_DAMAGE, 0.2F);
        buff.setModifier(BuffModifiers.ATTACK_SPEED, 0.2F);
        buff.setModifier(BuffModifiers.SPEED, 0.2F);
    }
}
