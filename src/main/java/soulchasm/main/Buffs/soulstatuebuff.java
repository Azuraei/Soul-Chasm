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

    private void changeBuffMode(ActiveBuff buff){
        if(buff.owner != null){
            float hp = buff.owner.getHealthPercent();
            if(hp >= 0.8F){
                buff.setModifier(BuffModifiers.ALL_DAMAGE, 0.2F);
                buff.setModifier(BuffModifiers.ATTACK_SPEED, 0.2F);
            } else {
                buff.setModifier(BuffModifiers.COMBAT_HEALTH_REGEN, 2.0F);
                buff.setModifier(BuffModifiers.ARMOR, 0.4F);
            }
            buff.owner.buffManager.forceUpdateBuffs();
        }
    }

    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        changeBuffMode(buff);
    }

    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        Mob owner = buff.owner;
        Mob attacker = null;
        if(buff.getAttacker()!=null){
            attacker = buff.getAttacker().getAttackOwner();
            if(attacker.hasDied()){
                buff.owner.buffManager.removeBuff("soulstatuebuff", true);
            }
        }
        if(owner!=null && attacker!=null){
            idolshieldvisualevent event = new idolshieldvisualevent(attacker, owner);
            buff.owner.getLevel().entityManager.addLevelEvent(event);
        }
        changeBuffMode(buff);
    }

    public boolean shouldDrawDuration(ActiveBuff buff) {
        return false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {

    }
}
