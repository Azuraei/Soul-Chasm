package soulchasm.main.Buffs.PhantomDashersBuffs;

import necesse.engine.Settings;
import necesse.engine.control.Control;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.ActiveBuffAbility;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.StaminaBuff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.camera.GameCamera;

public class phantomdashersbuff extends TrinketBuff implements ActiveBuffAbility {
    public phantomdashersbuff() {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public Packet getStartAbilityContent(PlayerMob player, ActiveBuff buff, GameCamera camera) {
        return this.getRunningAbilityContent(player, buff);
    }

    public Packet getRunningAbilityContent(PlayerMob player, ActiveBuff buff) {
        Packet content = new Packet();
        PacketWriter writer = new PacketWriter(content);
        StaminaBuff.writeStaminaData(player, writer);
        return content;
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content) {
        if (buff.owner.isRiding()) {
            return false;
        } else {
            return player.isServer() && Settings.giveClientsPower || StaminaBuff.canStartStaminaUsage(buff.owner);
        }
    }

    public void onActiveAbilityStarted(PlayerMob player, ActiveBuff buff, Packet content) {
        PacketReader reader = new PacketReader(content);
        if (!player.isServer() || Settings.giveClientsPower) {
            StaminaBuff.readStaminaData(player, reader);
        }

        player.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("phantomdashersactivebuff"), player, 1.0F, (Attacker)null), false);
    }

    public boolean tickActiveAbility(PlayerMob player, ActiveBuff buff, boolean isRunningClient) {
        if (player.inLiquid()) {
            player.buffManager.removeBuff(BuffRegistry.getBuff("phantomdashersactivebuff"), false);
        } else {
            ActiveBuff speedBuff = player.buffManager.getBuff(BuffRegistry.getBuff("phantomdashersactivebuff"));
            if (speedBuff != null) {
                speedBuff.setDurationLeftSeconds(1.0F);
            } else {
                player.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("phantomdashersactivebuff"), player, 1.0F, (Attacker)null), false);
            }

            if ((player.moveX != 0.0F || player.moveY != 0.0F) && (player.dx != 0.0F || player.dy != 0.0F)) {
                long msToDeplete = 4000L;
                float usage = 50.0F / (float)msToDeplete;
                if (!StaminaBuff.useStaminaAndGetValid(player, usage)) {
                    return false;
                }
            }
        }

        return !isRunningClient || Control.TRINKET_ABILITY.isDown();
    }

    public void onActiveAbilityUpdate(PlayerMob player, ActiveBuff buff, Packet content) {
    }

    public void onActiveAbilityStopped(PlayerMob player, ActiveBuff buff) {
        player.buffManager.removeBuff(BuffRegistry.getBuff("phantomdashersactivebuff"), false);
    }
}
