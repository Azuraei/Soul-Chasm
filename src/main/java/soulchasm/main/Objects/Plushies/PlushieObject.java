package soulchasm.main.Objects.Plushies;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.entity.objectEntity.PortalObjectEntity;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.SoulChasm;

import java.awt.*;
import java.util.List;

public class PlushieObject extends GameObject {
    public GameTexture texture;
    public final GameRandom drawRandom;
    public final String textureName;

    public PlushieObject(String textureName, Color mapColor) {
        super(new Rectangle(11, 11, 10, 10));
        this.hoverHitbox = new Rectangle(3, -6, 24, 24);
        this.objectHealth = 1;
        this.toolType = ToolType.ALL;
        this.isLightTransparent = true;
        this.isSolid = false;
        this.stackSize = 10;
        this.mapColor = mapColor;
        this.textureName = textureName;
        this.drawRandom = new GameRandom();
        this.rarity = Item.Rarity.EPIC;
    }

    public void loadTextures() {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/"+this.textureName);
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", this.textureName));
        return tooltips;
    }

    public String getInteractTip(Level level, int x, int y, PlayerMob perspective, boolean debug) {
        return Localization.translate("ui", "plushie_tip");
    }
    public boolean canInteract(Level level, int x, int y, PlayerMob player) {
        return true;
    }
    public void interact(Level level, int x, int y, PlayerMob player) {
        if (level.isServer() && player.isServerClient()) {
            ObjectEntity objectEntity = level.entityManager.getObjectEntity(x, y);
            if (objectEntity instanceof PlushieObjectEntity) {
                ((PlushieObjectEntity)objectEntity).use();
            }
        }
        super.interact(level, x, y, player);
    }

    public float getSquish(Level level, int x, int y){
        ObjectEntity objectEntity = level.entityManager.getObjectEntity(x, y);
        if (objectEntity instanceof PlushieObjectEntity) {
            PlushieObjectEntity object = (PlushieObjectEntity) objectEntity;
            float perc = GameUtils.getAnimFloat((long) (object.duration - object.time), (int) PlushieObjectEntity.squishDuration);
            //(float)(Math.sin((double)perc * Math.PI * (double)2.0F) + (double)1.0F) / 2.0F
            return object.test;
        } else {
            return 1;
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawRandomX;
        int drawRandomY;
        synchronized(this.drawRandom) {
            drawRandomX = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).getIntBetween(-2,2);
            drawRandomY = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).getIntBetween(-2,2);
        }
        int drawX = camera.getTileDrawX(tileX) + drawRandomX;
        int drawY = camera.getTileDrawY(tileY) + drawRandomY;
        TextureDrawOptions options = texture.initDraw().sprite(0, 0, 31, 32).size(31, (int) (32 * getSquish(level, tileX, tileY))).light(light).pos(drawX, drawY - 12);
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
        int drawRandomX;
        int drawRandomY;
        synchronized(this.drawRandom) {
            drawRandomX = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).getIntBetween(-2,2);
            drawRandomY = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).getIntBetween(-2,2);
        }
        int drawX = camera.getTileDrawX(tileX) + drawRandomX;
        int drawY = camera.getTileDrawY(tileY) + drawRandomY;
        texture.initDraw().sprite(0, 0, 31, 32).alpha(alpha).draw(drawX, drawY - 12);
    }

    public ObjectEntity getNewObjectEntity(Level level, int x, int y) {
        return new PlushieObjectEntity(level, x, y);
    }
}