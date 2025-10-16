package soulchasm.main.buffs.armorbuffs;

import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.ShownCooldownBuff;

public class SoulArmorCooldown extends ShownCooldownBuff {
    public boolean isVisible(ActiveBuff buff) {
        return true;
    }
}
