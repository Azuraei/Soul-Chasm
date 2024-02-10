package soulchasm.main.Buffs;

import necesse.engine.util.GameMath;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;

public class soulstealerbuff extends TrinketBuff {
    private int effectStacks;

    public soulstealerbuff() {
        this.canCancel = false;
        this.isVisible = false;
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        this.updateBuff(buff);
        buff.setModifier(BuffModifiers.HEALTH_REGEN, 0F);
        this.effectStacks = 0;
    }
    public void serverTick(ActiveBuff buff) {
        this.updateBuff(buff);
    }
    public void clientTick(ActiveBuff buff) {
        this.updateBuff(buff);
    }

    private void updateBuff(ActiveBuff buff) {
        if(buff.owner.isInCombat() && effectStacks<1000){
            if(buff.getModifier(BuffModifiers.HEALTH_REGEN)!=0){
                buff.setModifier(BuffModifiers.HEALTH_REGEN, 0F);
            }
            effectStacks++;
        } else if (effectStacks>0) {
            if (effectStacks < 950) {
                buff.setModifier(BuffModifiers.HEALTH_REGEN, 4F);
            }
            effectStacks--;
        }
        if(effectStacks==0 && buff.getModifier(BuffModifiers.HEALTH_REGEN)!=0){
            buff.setModifier(BuffModifiers.HEALTH_REGEN, 0F);
        }
        updateModifiers(buff, effectStacks);
    }

    private void updateModifiers(ActiveBuff buff, int effectStacks){
        buff.setModifier(BuffModifiers.ALL_DAMAGE, GameMath.toDecimals((0.001F * effectStacks), 2));
        buff.setModifier(BuffModifiers.MAX_HEALTH, GameMath.toDecimals(-0.005F * effectStacks * 0.1F, 2));
        buff.forceManagerUpdate();
    }
}
