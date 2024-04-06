package soulchasm.main.Buffs.SetBonusBuffs;

import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.ShownCooldownBuff;

public class soularmorcooldown extends ShownCooldownBuff {
    public boolean isVisible(ActiveBuff buff) {
        return true;
    }

    private void applyNextBuff(ActiveBuff buff){
        if(buff.getDurationLeft() <= 40 && !buff.getGndData().getBoolean("tryDebuffApply") && GameRandom.globalRandom.getChance(0.5)){
            buff.getGndData().setBoolean("tryDebuffApply", true);
            buff.owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("souldischargesicknessdebuff"), buff.owner, 25F, null), false);
        }
    }

    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        applyNextBuff(buff);
    }

    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        applyNextBuff(buff);
    }
}
