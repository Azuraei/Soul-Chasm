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
        this.droppedStone = "soulcaverockitem";
    }

    @Override
    public GameMessage getNewLocalization() {
        return new LocalMessage("object", "soulcavedecorations");
    }

    @Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        GameTexture texture = this.texture.getDamagedTexture(this, level, tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        final int randomYOffset = this.getRandomYOffset(tileX, tileY);
        int sprite;
        boolean mirror;
        synchronized(this.drawRandom) {
            mirror = this.drawRandom.seeded(getTileSeed(tileX, tileY)).nextBoolean();
            if(this.drawRandom.seeded(getTileSeed(tileX, tileY)).getChance(0.8F)){
                sprite = this.drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(6);
            } else {
                sprite = this.drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(5,8);
            }
        }
        drawY += randomYOffset;
        final TextureDrawOptions options = texture.initDraw().sprite(sprite, 0, 32, texture.getHeight()).light(light).mirror(mirror, false).pos(drawX, drawY - texture.getHeight() + 32);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16 + randomYOffset;
            }

            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }
    @Override
    public LootTable getLootTable(Level level, int layerID, int tileX, int tileY) {
        return new LootTable(LootItem.between(this.droppedStone, 2, 6));
    }
}
