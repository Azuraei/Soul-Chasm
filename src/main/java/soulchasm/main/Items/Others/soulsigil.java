package soulchasm.main.Items.Others;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.Level;

public class soulsigil extends ConsumableItem {
    public soulsigil() {
        super(1, true);
        this.itemCooldownTime.setBaseValue(2000);
        this.setItemCategory("consumable", "bossitems");
        this.dropsAsMatDeathPenalty = true;
        this.keyWords.add("boss");
        this.rarity = Rarity.LEGENDARY;
        this.worldDrawSize = 32;
    }

    public String canPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        return level instanceof IncursionLevel ? null : "notincursion";
    }

    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        if (level.isServer()) {
            if (level instanceof IncursionLevel) {
                GameMessage summonError = ((IncursionLevel)level).canSummonBoss("souldragonhead");
                if (summonError != null) {
                    if (player != null && player.isServerClient()) {
                        player.getServerClient().sendChatMessage(summonError);
                    }

                    return item;
                }
            }

            System.out.println("Ancient Dragon has been summoned at " + level.getIdentifier() + ".");
            float angle = (float) GameRandom.globalRandom.nextInt(360);
            float nx = GameMath.cos(angle);
            float ny = GameMath.sin(angle);
            float distance = 960.0F;
            Mob mob = MobRegistry.getMob("souldragonhead", level);
            level.entityManager.addMob(mob, (float)(player.getX() + (int)(nx * distance)), (float)(player.getY() + (int)(ny * distance)));
            level.getServer().network.sendToClientsAt(new PacketChatMessage(new LocalMessage("misc", "bosssummon", "name", mob.getLocalization())), level);
            if (level instanceof IncursionLevel) {
                ((IncursionLevel)level).onBossSummoned(mob);
            }
        }

        if (this.singleUse) {
            item.setAmount(item.getAmount() - 1);
        }

        return item;
    }


    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soulsigil"));
        return tooltips;
    }
}
