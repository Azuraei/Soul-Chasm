package soulchasm.main.Buffs.SealVariantsBuffs;

import necesse.engine.network.server.FollowPosition;
import necesse.engine.network.server.MobFollower;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;
import soulchasm.main.Mobs.Summon.smallsoulsummon;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.function.Predicate;

public class summonsoulsealbuff extends TrinketBuff {
    public summonsoulsealbuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public void firstAdd(ActiveBuff buff) {
        super.firstAdd(buff);
        if(buff.owner.getLevel()!=null && buff.owner.getLevel().isServer() && !buff.owner.buffManager.hasBuff("soulsealfollowerbuff")){
            PlayerMob playerMob = (PlayerMob) buff.owner;
            runSummon(buff.owner.getLevel(), playerMob.getServerClient());
        }
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


