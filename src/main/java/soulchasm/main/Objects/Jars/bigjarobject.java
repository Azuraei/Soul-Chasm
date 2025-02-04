package soulchasm.main.Objects.Jars;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.registries.ContainerRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.entity.objectEntity.interfaces.OEInventory;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.container.object.OEInventoryContainer;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class bigjarobject extends GameObject {
    public static GameTexture texture;
    public ArrayList<InventoryItem> list;

    public bigjarobject() {
        this.mapColor = new Color(224, 246, 244, 152);
        this.displayMapTooltip = true;
        this.drawDamage = false;
        this.objectHealth = 1;
        this.toolType = ToolType.ALL;
        this.isLightTransparent = true;
        this.canPlaceOnShore = true;
        this.replaceRotations = false;
        this.collision = new Rectangle(8, 4, 16, 16);
        this.hoverHitbox = new Rectangle(2, -14, 28, 28);
    }
    @Override
    public void loadTextures() {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/bigjarobject");
    }

    @Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        ObjectEntity ent = level.entityManager.getObjectEntity(tileX, tileY);
        final DrawOptions item;
        if (ent != null && ent.implementsOEInventory()) {
            InventoryItem invItem = ((OEInventory)ent).getInventory().getItem(0);
            item = invItem != null ? invItem.getWorldDrawOptions(perspective, drawX + 16, drawY + 8, light, 0.0F, 18) : () -> {
            };
        } else {
            item = () -> {
            };
        }
        TextureDrawOptions options = texture.initDraw().light(light).pos(drawX, drawY - (texture.getHeight() - 16));
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 15;
            }
            public void draw(TickManager tickManager) {
                item.draw();
            }
        });
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16;
            }
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public String getInteractTip(Level level, int x, int y, PlayerMob perspective, boolean debug) {
        return Localization.translate("controls", "opentip");
    }

    public boolean canInteract(Level level, int x, int y, PlayerMob player) {
        return true;
    }
    public void interact(Level level, int x, int y, PlayerMob player) {
        if (level.isServer()) {
            OEInventoryContainer.openAndSendContainer(ContainerRegistry.OE_INVENTORY_CONTAINER, player.getServerClient(), level, x, y);
        }
    }
    public ObjectEntity getNewObjectEntity(Level level, int x, int y) {
        jarobjectentity objectEntity = new jarobjectentity(level, x, y);
        objectEntity.inventory.spoilRateModifier = 0.5F;
        return objectEntity;
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "bigjarobject"));
        tooltips.add(Localization.translate("itemtooltip", "coolingboxtip"));
        return tooltips;
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera) {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        texture.initDraw().sprite(0, 0, 32, texture.getHeight()).alpha(alpha).draw(drawX, drawY - (texture.getHeight() - 16));
    }
}
