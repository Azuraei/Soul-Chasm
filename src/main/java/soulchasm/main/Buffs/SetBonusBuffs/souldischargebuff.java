package soulchasm.main.Buffs.SetBonusBuffs;

import necesse.engine.Screen;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class souldischargebuff extends Buff {

    public souldischargebuff() {
        this.canCancel = false;
        this.isVisible = true;
        this.shouldSave = false;
        this.isImportant =  true;
    }

    private void applyNextBuff(ActiveBuff buff){
        if(buff.getDurationLeft() <= 40 && !buff.owner.buffManager.hasBuff("souldischargesicknessdebuff")){
            buff.owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("souldischargesicknessdebuff"), buff.owner, 20F, null), false);
        }
    }

    @Override
    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        Screen.setSceneShade(0.9F, 0.9F, 1.2F);
        applyNextBuff(buff);
    }

    @Override
    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        applyNextBuff(buff);
    }


    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.SPEED, 1.0F);
        buff.setModifier(BuffModifiers.ATTACK_SPEED, 0.8F);
    }

}
