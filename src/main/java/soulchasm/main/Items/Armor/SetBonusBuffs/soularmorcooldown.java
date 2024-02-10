package soulchasm.main.Items.Armor.SetBonusBuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.ShownCooldownBuff;

public class soularmorcooldown extends ShownCooldownBuff {
    @Override
    public boolean isVisible(ActiveBuff buff) {
        return true;
    }
}
