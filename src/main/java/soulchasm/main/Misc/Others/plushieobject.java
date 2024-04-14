package soulchasm.main.Misc.Others;

import necesse.engine.Screen;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.sound.SoundEffect;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
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

public class plushieobject extends GameObject {
    public GameTexture texture;
    public final GameRandom drawRandom;
    public final String textureName;

    public plushieobject(String textureName, Color mapColor) {
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
        if(level.isClient()){
            float pitch = GameRandom.globalRandom.getFloatBetween(0.8F, 1.6F);
            Screen.playSound(SoulChasm.plushie_squeak, SoundEffect.effect(x * 32, y * 32).volume(0.5F).pitch(pitch));
        } else {
            String message = GameRandom.globalRandom.getOneOf("pet", "^-^", "yippie!", ":)");
            player.getServerClient().sendUniqueFloatText(player.getX(), player.getY() - 32, new StaticMessage(message), null, 4);
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
        TextureDrawOptions options = texture.initDraw().sprite(0, 0, 31, 32).light(light).pos(drawX, drawY - 12);
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
        return null;
    }
}