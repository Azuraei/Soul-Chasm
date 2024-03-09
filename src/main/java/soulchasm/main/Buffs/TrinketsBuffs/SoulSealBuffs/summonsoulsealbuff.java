package soulchasm.main.Buffs.TrinketsBuffs.SoulSealBuffs;

import necesse.engine.network.server.FollowPosition;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.level.maps.Level;

public class summonsoulsealbuff extends TrinketBuff {
    public summonsoulsealbuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public void firstAdd(ActiveBuff buff) {
        super.firstAdd(buff);
        boolean spawn = buff.getGndData().getBoolean("spawn");
        if(buff.owner.getLevel()!=null && buff.owner.getLevel().isServer() && !spawn){
            buff.getGndData().setBoolean("spawn", true);
            PlayerMob playerMob = (PlayerMob) buff.owner;
            runSummon(buff.owner.getLevel(), playerMob.getServerClient());
        }
    }
    @Override
    public void onRemoved(ActiveBuff buff) {
        boolean spawn = buff.getGndData().getBoolean("spawn");
        if(spawn){
            buff.owner.buffManager.removeBuff("soulsealfollowerbuff", true);
        }
        super.onRemoved(buff);
    }

    public void runSummon(Level level, ServerClient client) {
        AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("smallsoulsummon", level);
        this.summonMob(client, mob);
    }
    public void summonMob(ServerClient client, AttackingFollowingMob mob) {
        client.addFollower("summonsealfollower", mob, FollowPosition.newFlying(0, -80, 1, 1), "soulsealfollowerbuff", 1.0F, 1, null, false);
        mob.getLevel().entityManager.addMob(mob, client.playerMob.x, client.playerMob.y - 90);
    }
}


