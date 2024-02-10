package soulchasm.main.Objects.OtherObjects;

import necesse.engine.localization.Localization;
import necesse.engine.tickManager.TickManager;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
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
        int minLight = 100;
        TextureDrawOptions options = texture.initDraw().light(light).pos(drawX, drawY - (texture.getHeight() - 16));
        TextureDrawOptions glow = texture.initDraw().light(light.minLevelCopy((float)minLight)).pos(drawX, drawY - (texture.getHeight() - 16));

        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            @Override
            public int getSortY() {
                return 16;
            }

            @Override
            public void draw(TickManager tickManager) {
                options.draw();
                glow.draw();
            }
        });
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera) {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY) + 5;
        texture.initDraw().sprite(0, 0, 32, texture.getHeight()).alpha(alpha).draw(drawX, drawY - (texture.getHeight() - 16));
    }

    public int getLightLevel(Level level, int x, int y) {
        return this.lightLevel;
    }
}
