package soulchasm.main.Buffs.TrinketsBuffs.SoulSealBuffs;

import necesse.engine.network.server.FollowPosition;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.GameResources;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.enchants.ToolItemEnchantment;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;
import soulchasm.main.Items.Tools.soulstatue;

public class summonsoulsealbuff extends TrinketBuff {
    private GameDamage attackDamage;
    private ToolItemEnchantment enchantment;
    private AttackingFollowingMob summonedMob;
    public summonsoulsealbuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.getGndData().setBoolean("spawned",false);
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack) {
        super.onItemAttacked(buff, targetX, targetY, player, attackHeight, item, slot, animAttack);
        Level level = buff.owner.getLevel();
        if (level.isServer()) {
            if (item.item instanceof SummonToolItem && !(item.item instanceof soulstatue)) {
                buff.getGndData().setBoolean("spawned", true);
                attackDamage = ((SummonToolItem) item.item).getAttackDamage(item).modDamage(1.2F);
                enchantment = ((SummonToolItem) item.item).getEnchantment(item);
                PlayerMob playerMob = (PlayerMob) buff.owner;
                if(playerMob.getServerClient().getFollowerCount("summonsealfollower")==0){
                    runSummon(buff.owner.getLevel(), playerMob.getServerClient());
                    playSound(false, summonedMob);
                } else if(summonedMob!=null) {
                    playSound(true, summonedMob);
                    summonedMob.updateDamage(attackDamage);
                    summonedMob.setEnchantment(enchantment);
                }
            }
        }
    }

    private void playSound(boolean onUpdate, AttackingFollowingMob mob){
        SoundManager.playSound(onUpdate ? GameResources.fadedeath3 : GameResources.magicbolt4, SoundEffect.effect(mob).volume(0.5F).pitch(onUpdate ? 1.2F : 1.0F));
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
        int offset = 65;
        summonedMob = mob;
        client.addFollower("summonsealfollower", mob, FollowPosition.newFlying(0, -offset, 1, 1), "soulsealfollowerbuff", 1.0F, 1, null, false);
        mob.updateDamage(attackDamage);
        mob.setEnchantment(enchantment);
        mob.getLevel().entityManager.addMob(mob, client.playerMob.x, client.playerMob.y - offset);
    }
}


