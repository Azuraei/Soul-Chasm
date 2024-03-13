package soulchasm.main.Buffs.TrinketsBuffs.SoulSealBuffs;

import necesse.engine.network.server.FollowPosition;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.enchants.ToolItemEnchantment;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;

public class summonsoulsealbuff extends TrinketBuff {
    private GameDamage attackDamage;
    private ToolItemEnchantment enchantment;
    public summonsoulsealbuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.getGndData().setBoolean("spawned",false);
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack) {
        super.onItemAttacked(buff, targetX, targetY, player, attackHeight, item, slot, animAttack);
        Level level = buff.owner.getLevel();
        if (level.isServer()) {
            if (item.item instanceof SummonToolItem) {
                buff.getGndData().setBoolean("spawned", true);
                attackDamage = ((SummonToolItem) item.item).getAttackDamage(item).modDamage(1.2F);
                enchantment = ((SummonToolItem) item.item).getEnchantment(item);
                PlayerMob playerMob = (PlayerMob) buff.owner;
                runSummon(buff.owner.getLevel(), playerMob.getServerClient());
            }
        }
    }

    @Override
    public void onRemoved(ActiveBuff buff) {
        boolean spawned = buff.getGndData().getBoolean("spawned");
        if(spawned){
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
        mob.updateDamage(attackDamage);
        mob.setEnchantment(enchantment);
        mob.getLevel().entityManager.addMob(mob, client.playerMob.x, client.playerMob.y - 80);
    }
}


