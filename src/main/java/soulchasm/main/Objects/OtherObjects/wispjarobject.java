package soulchasm.main.Objects.OtherObjects;

import necesse.engine.localization.Localization;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.SoulChasm;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class wispjarobject extends GameObject {
    public static GameTexture texture;
    public ArrayList<InventoryItem> list;

    public wispjarobject() {
        this.mapColor = new Color(102, 199, 255, 194);
        this.displayMapTooltip = true;
        this.lightLevel = 75;
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
        return new LootTable((new LootItem("wispjar")));
    }

    @Override
    public void loadTextures() {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/wispjarobject");
    }

    @Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        TextureDrawOptions options = texture.initDraw().sprite(GameUtils.getAnim(level.getWorldEntity().getWorldTime(), 7, 800), 0, 32, 32).light(light.minLevelCopy(100F)).pos(drawX, drawY - (texture.getHeight() - 16));

        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            @Override
            public int getSortY() {
                return 16;
            }

            @Override
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public void tickEffect(Level level, int x, int y) {
        super.tickEffect(level, x, y);
        if (GameRandom.globalRandom.getChance(0.020F)) {
            int posX = x * 32 + GameRandom.globalRandom.nextInt(16);
            int posY = y * 32 + GameRandom.globalRandom.nextInt(16);
            boolean mirror = GameRandom.globalRandom.nextBoolean();
            level.entityManager.addParticle((float)posX, (float)(posY + 15), Particle.GType.COSMETIC).sprite(SoulChasm.particleWispSection).fadesAlpha(0.6F, 0.6F).size((options, lifeTime, timeAlive, lifePercent) -> {
            }).height(30.0F).movesConstant(GameRandom.globalRandom.getFloatBetween(0.5F, 1.0F) * GameRandom.globalRandom.getOneOf(1.0F, -1.0F), GameRandom.globalRandom.getFloatBetween(0.5F, 1.0F) * GameRandom.globalRandom.getOneOf(1.0F, -1.0F)).sizeFades(10, 15).modify((options, lifeTime, timeAlive, lifePercent) -> {
                options.mirror(mirror, false);
            }).lifeTime(6000);
        }

    }


    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "glowingjarobject"));
        return tooltips;
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera) {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        texture.initDraw().sprite(0, 0, 32, texture.getHeight()).alpha(alpha).draw(drawX, drawY - (texture.getHeight() - 16));
    }

    public int getLightLevel(Level level, int x, int y) {
        return this.lightLevel;
    }
}
