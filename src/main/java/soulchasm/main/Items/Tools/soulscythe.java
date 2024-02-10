package soulchasm.main.Items.Tools;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.GreatswordChargeLevel;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.swordToolItem.GreatswordToolItem;
import necesse.level.maps.Level;
import soulchasm.main.Misc.soulscytheattackhandler;

import java.awt.*;

public class soulscythe extends GreatswordToolItem {
    public soulscythe() {
        super(Rarity.LEGENDARY, 300, 250, 110, 300, 1200, 6, 6, GreatswordToolItem.getThreeChargeLevels(500, 600, 800));
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        player.startAttackHandler(new soulscytheattackhandler(this, player, slot, item, this, seed, x, y, this.chargeLevels));
        return item;
    }

    @Override
    public float getAttackMovementMod(InventoryItem item) {
        return 0.9F;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "soulscythe"));
        return tooltips;
    }
}
