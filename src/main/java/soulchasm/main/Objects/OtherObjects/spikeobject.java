package soulchasm.main.Objects.OtherObjects;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static soulchasm.SoulChasm.SoulStoneColor;

public class spikeobject extends GameObject {
    public GameTexture texture;
    protected final GameRandom drawRandom;

    public spikeobject() {
        super(new Rectangle(4, 4, 26, 26));
        hoverHitbox = new Rectangle(0, -32, 32, 64);
        toolType = ToolType.UNBREAKABLE;
        isLightTransparent = false;
        mapColor = SoulStoneColor;
        this.drawRandom = new GameRandom();
    }

    @Override
    public void loadTextures() {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/spikeobject");
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        int rotation = level.getObjectRotation(tileX, tileY) % 4;
        TextureDrawOptions options = texture.initDraw().sprite(rotation % 4, 0, 64, texture.getHeight()).light(light).pos(drawX  - 16, drawY - texture.getHeight() + 32);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16;
            }
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera) {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        texture.initDraw().sprite(rotation % 4, 0, 64, texture.getHeight()).alpha(alpha).draw(drawX - 16, drawY - texture.getHeight() + 32);
    }

    public ObjectEntity getNewObjectEntity(Level level, int x, int y) {
        return null;
    }
}