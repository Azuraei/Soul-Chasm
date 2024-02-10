package soulchasm.main.Buffs.SealVariantsBuffs;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.SummonedCountBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;

public class soulsealfollowerbuff extends SummonedCountBuff {
    public soulsealfollowerbuff() {
        this.canCancel = true;
    }
    @Override
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        super.init(buff, eventSubscriber);
    }
    @Override
    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        if(!buff.owner.buffManager.hasBuff("summonsoulsealbuff")){
            buff.owner.buffManager.removeBuff("soulsealfollowerbuff", true);
        }
    }
    @Override
    public ListGameTooltips getTooltip(ActiveBuff ab) {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "soulsealfollowerbuff"));
        return tooltips;
    }
}
