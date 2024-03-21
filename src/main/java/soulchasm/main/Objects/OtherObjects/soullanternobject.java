package soulchasm.main.Objects.OtherObjects;

import necesse.engine.tickManager.TickManager;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class soullanternobject extends GameObject {
    public static GameTexture texture;
    public ArrayList<InventoryItem> list;

    public soullanternobject() {
        this.mapColor = new Color(43, 91, 122, 194);
        this.displayMapTooltip = true;
        this.lightLevel = 125;
        this.lightHue = 240.0F;
        this.lightSat = 0.3F;
        this.drawDamage = false;
        this.objectHealth = 1;
        this.toolType = ToolType.ALL;
        this.isLightTransparent = true;
        this.roomProperties.add("lights");
        this.canPlaceOnShore = true;
        this.replaceCategories.add("torch");
        this.replaceRotations = false;
        this.collision = new Rectangle(8, 4, 16, 16);
    }

    @Override
    public LootTable getLootTable(Level level, int tileX, int tileY) {
        return new LootTable((new LootItem("soullantern")));
    }

    @Override
    public void loadTextures() {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/soullanternobject");
    }

    @Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY) + 5;
        int active = this.isActive(level, tileX, tileY) ? 0 : 1;
        TextureDrawOptions options = texture.initDraw().sprite(active, 0, 32).light(light).pos(drawX, drawY - (texture.getHeight() - 16));

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
        int drawY = camera.getTileDrawY(tileY) + 5;
        texture.initDraw().sprite(0, 0, 32, texture.getHeight()).alpha(alpha).draw(drawX, drawY - (texture.getHeight() - 16));
    }

    public int getLightLevel(Level level, int x, int y) {
        return this.isActive(level, x, y) ? this.lightLevel : 0;
    }

    public boolean isActive(Level level, int x, int y) {
        byte rotation = level.getObjectRotation(x, y);
        return this.getMultiTile(rotation).streamIDs(x, y).noneMatch((c) -> level.wireManager.isWireActiveAny(c.tileX, c.tileY));
    }

    public void onWireUpdate(Level level, int x, int y, int wireID, boolean active) {
        byte rotation = level.getObjectRotation(x, y);
        Rectangle rect = this.getMultiTile(rotation).getTileRectangle(x, y);
        level.lightManager.updateStaticLight(rect.x, rect.y, rect.x + rect.width - 1, rect.y + rect.height - 1, true);
    }
}
