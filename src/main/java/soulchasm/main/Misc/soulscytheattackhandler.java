package soulchasm.main.Misc;

import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.GreatswordAttackHandler;
import necesse.entity.mobs.attackHandler.GreatswordChargeLevel;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.swordToolItem.GreatswordToolItem;
import soulchasm.main.Items.Tools.soulscythe;

public class soulscytheattackhandler extends GreatswordAttackHandler {
    public soulscytheattackhandler(soulscythe weapon, PlayerMob player, PlayerInventorySlot slot, InventoryItem item, GreatswordToolItem toolItem, int seed, int startX, int startY, GreatswordChargeLevel... chargeLevels) {
        super(player, slot, item, toolItem, seed, startX, startY, chargeLevels);
    }

    public void onEndAttack(boolean bySelf) {
        super.onEndAttack(bySelf);
        if (this.currentChargeLevel == 2) {
            this.applyEffect();
        }
    }

    private void applyEffect() {
        player.buffManager.addBuff(new ActiveBuff("soulscythebuff", player, 0.5F, null), false);
    }
}
