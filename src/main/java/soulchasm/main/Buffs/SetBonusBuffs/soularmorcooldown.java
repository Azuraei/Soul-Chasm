package soulchasm.main.Buffs.SetBonusBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.ShownCooldownBuff;

public class soularmorcooldown extends ShownCooldownBuff {
    public boolean isVisible(ActiveBuff buff) {
        return true;
    }
}
