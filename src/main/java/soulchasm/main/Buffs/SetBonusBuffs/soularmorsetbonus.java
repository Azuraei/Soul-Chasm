package soulchasm.main.Buffs.SetBonusBuffs;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;

public class soularmorsetbonus extends SetBonusBuff implements BuffAbility {
    public soularmorsetbonus() {
        this.canCancel = false;
        this.isVisible = false;
        this.shouldSave = false;
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    @Override
    public void tickEffect(ActiveBuff buff, Mob owner) {
        super.tickEffect(buff, owner);
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content) {
        Mob owner = buff.owner;
        float active = 15.0F;
        float cooldown = 60F;
        owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("souldischargebuff"), owner, active, null), false);
        owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("soularmorcooldown"), owner, cooldown, null), false);
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content) {
        return !buff.owner.buffManager.hasBuff("soularmorcooldown");
    }


    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard) {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "soularmorsetbonus"));
        return tooltips;
    }
}
