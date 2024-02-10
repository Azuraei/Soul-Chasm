package soulchasm.main.Items.Tools;

import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;


public class soulmetalbow extends BowProjectileToolItem {
    public soulmetalbow() {
        super(1000);
        this.animSpeed = 300;
        this.rarity = Rarity.EPIC;
        this.attackDamage = new GameDamage(DamageTypeRegistry.RANGED, 45.0F);
        this.velocity = 200;
        this.attackRange = 600;
        this.attackXOffset = 12;
        this.attackYOffset = 28;
    }

    public void tickHolding(InventoryItem item, PlayerMob player) {
        player.buffManager.addBuff(new ActiveBuff("soulbowbuff", player, 0.2F, null), false);
        super.tickHolding(item, player);
    }

    protected void addTooltips(ListGameTooltips tooltips, InventoryItem item, boolean isSettlerWeapon) {
        tooltips.add(Localization.translate("itemtooltip", "soulmetalbowtip"));
    }
}
