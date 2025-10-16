package soulchasm.main.buffs.trinketbuffs.PickaxeheadBuffs;

import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.pickaxeToolItem.PickaxeToolItem;

public class PickaxeHeadBuff extends TrinketBuff {

    public PickaxeHeadBuff() {
        this.canCancel = false;
        this.isVisible = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, GNDItemMap attackMap) {
        super.onItemAttacked(buff, targetX, targetY, attackerMob, attackHeight, item, slot, animAttack, attackMap);
        if(item.item instanceof PickaxeToolItem && attackerMob.getLevel().isClient()){
            buff.owner.buffManager.addBuff(new ActiveBuff("pickaxeheadstackbuff", attackerMob, 10F, null), false);
        }
    }

}
