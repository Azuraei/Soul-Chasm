package soulchasm.main.objects;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.gameObject.RockObject;
import necesse.level.gameObject.SingleRockSmall;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class ChasmDebreeObject extends SingleRockSmall {
    String droppedStone;
    public ChasmDebreeObject(RockObject type, String textureName, Color mapColor) {
        super(type, textureName, mapColor);
        this.canPlaceOnLiquid = false;
        this.type = type;
        this.droppedStone = "chasmrockitem";
    }

    @Override
    public GameMessage getNewLocalization() {
        return new LocalMessage("object", "chasmdebree");
    }

    @Override
    public LootTable getLootTable(Level level, int layerID, int tileX, int tileY) {
        return new LootTable(LootItem.between(this.droppedStone, 2, 6));
    }
}
