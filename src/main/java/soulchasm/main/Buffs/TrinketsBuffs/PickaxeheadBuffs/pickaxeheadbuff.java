package soulchasm.main.Buffs.TrinketsBuffs.PickaxeheadBuffs;

import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.pickaxeToolItem.PickaxeToolItem;

public class pickaxeheadbuff extends TrinketBuff {

    public pickaxeheadbuff() {
        this.canCancel = false;
        this.isVisible = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack) {
        super.onItemAttacked(buff, targetX, targetY, player, attackHeight, item, slot, animAttack);
        if(item.item instanceof PickaxeToolItem && player.getLevel().isClient()){
            buff.owner.buffManager.addBuff(new ActiveBuff("pickaxeheadstackbuff", player, 10F, null), false);
        }
    }

}
