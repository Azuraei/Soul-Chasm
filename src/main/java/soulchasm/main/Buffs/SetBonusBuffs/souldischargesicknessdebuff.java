package soulchasm.main.Buffs.SetBonusBuffs;

import necesse.engine.Screen;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;

public class souldischargesicknessdebuff extends Buff {

    public souldischargesicknessdebuff() {
        this.canCancel = false;
        this.isVisible = true;
        this.shouldSave = true;
        this.isImportant =  true;
    }

    @Override
    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        Screen.setSceneShade(0.9F, 0.9F, 0.9F);
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.SPEED, -0.2F);
        buff.setModifier(BuffModifiers.ATTACK_SPEED, -0.4F);
    }

}
