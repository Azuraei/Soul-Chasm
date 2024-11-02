package soulchasm.main.Objects.OtherObjects;

import necesse.engine.registries.TileRegistry;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.gameObject.GrassObject;
import necesse.level.maps.Level;

public class lunartear extends GrassObject {
    public lunartear() {
        super("lunartears", 3);
        this.toolType = ToolType.PICKAXE;
        this.attackThrough = true;
        this.lightLevel = 30;
        this.lightHue = 240.0F;
        this.lightSat = 0.05F;
    }

    public LootTable getLootTable(Level level, int layerID, int tileX, int tileY) {
        LootTable lootTableBase = super.getLootTable(level, layerID, tileX, tileY);
        lootTableBase.items.add(new ChanceLootItem(0.01F, "lunartearflowerhead"));
        return lootTableBase;
    }

    @Override
    public void attackThrough(Level level, int x, int y, GameDamage damage, Attacker attacker) {
    }

    public String canPlace(Level level, int layerID, int x, int y, int rotation, boolean byPlayer, boolean ignoreOtherLayers) {
        String error = super.canPlace(level, layerID, x, y, rotation, byPlayer, ignoreOtherLayers);
        if (error != null) {
            return error;
        } else {
            return level.getTileID(x, y) != TileRegistry.getTileID("soulcavegrass") ? "wrongtile" : null;
        }
    }

    public boolean isValid(Level level, int layerI, int x, int y) {
        if (!super.isValid(level, layerI, x, y)) {
            return false;
        } else {
            int tileID = level.getTileID(x, y);
            return tileID == TileRegistry.getTileID("soulcavegrass");
        }
    }
}