package soulchasm.main.Objects.OtherObjects;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.registries.ContainerRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.DisplayStandObjectEntity;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.entity.objectEntity.interfaces.OEInventory;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.container.object.OEInventoryContainer;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.furniture.FurnitureObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.SoulChasm;

import java.awt.*;
import java.util.List;

import static soulchasm.SoulChasm.SoulStoneColorLight;

public class soulmonumentobject extends FurnitureObject {
    public GameTexture texture;
    public GameTexture glow_texture;
    protected final GameRandom drawRandom;
    private int particleDelay;

    public soulmonumentobject() {
        super(new Rectangle(2, 0, 30, 30));
        this.hoverHitbox = new Rectangle(2, 0, 30, 30);
        this.toolType = ToolType.PICKAXE;
        this.isLightTransparent = true;
        this.mapColor = SoulStoneColorLight;
        this.drawRandom = new GameRandom();
        this.particleDelay = 0;
    }

    public void loadTextures() {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/soulmonumentobject");
        glow_texture = GameTexture.fromFile("objects/soulmonumentobject_glow");
    }

    @Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        int bobbing;
        synchronized(this.drawRandom) {
            bobbing = (int)(GameUtils.getBobbing(level.getWorldEntity().getTime(), 800 + this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).getIntBetween(0,200)) * 5.0F);
        }
        ObjectEntity ent = level.entityManager.getObjectEntity(tileX, tileY);
        final DrawOptions item;
        if (ent != null && ent.implementsOEInventory()) {
            InventoryItem invItem = ((OEInventory)ent).getInventory().getItem(0);
            item = invItem != null ? invItem.getWorldDrawOptions(perspective, drawX + 16, drawY - 16 + bobbing, light, 0.0F, 28) : () -> {
            };
        } else {
            item = () -> {
            };
        }
        int frame = GameUtils.getAnim((long)this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).nextInt(800) + level.getWorldEntity().getWorldTime(), 4, 800);
        TextureDrawOptions options = texture.initDraw().sprite(frame, 0, 64, texture.getHeight()).light(light).pos(drawX - 16, drawY - texture.getHeight() + 32);
        TextureDrawOptions glow = glow_texture.initDraw().sprite(frame, 0, 64, texture.getHeight()).light(light.minLevelCopy(100)).alpha(0.8F).pos(drawX - 16, drawY - texture.getHeight() + 32);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16;
            }
            public void draw(TickManager tickManager) {
                options.draw();
                item.draw();
                glow.draw();
            }
        });
    }

    public void tickEffect(Level level, int x, int y) {
        super.tickEffect(level, x, y);
        ObjectEntity entity = level.entityManager.getObjectEntity(x, y);
        if (entity != null && ((OEInventory)entity).getInventory().getItem(0) != null) {
            level.lightManager.refreshParticleLightFloat(x * 32, y * 32, new Color(0x36C2FF), 0.6F, 80);
            if(GameRandom.globalRandom.getChance(0.080F)){
                int posX = x * 32 + GameRandom.globalRandom.nextInt(32);
                int posY = y * 32;
                boolean mirror = GameRandom.globalRandom.nextBoolean();
                level.entityManager.addParticle((float)posX, (float)(posY + 16), Particle.GType.COSMETIC).sprite(SoulChasm.particleWispSection).fadesAlpha(0.6F, 0.6F).size((options, lifeTime, timeAlive, lifePercent) -> {
                }).height(30.0F).movesConstant(GameRandom.globalRandom.getFloatBetween(0.1F, 0.5F) * GameRandom.globalRandom.getOneOf(1.0F, -1.0F), GameRandom.globalRandom.getFloatBetween(-2.0F, -4.0F)).sizeFades(10, 15).modify((options, lifeTime, timeAlive, lifePercent) -> {
                    options.mirror(mirror, false);
                }).lifeTime(6000);
            }
            if(particleDelay>=10){
                this.particleDelay = 0;
                int posX = x * 32 + 16;
                int posY = y * 32 + 16;
                level.entityManager.addParticle((float)posX, (float)(posY), Particle.GType.COSMETIC).sprite(SoulChasm.particleMonumentRingSection).fadesAlphaTimeToCustomAlpha(100, 400, 0.2F).size((options, lifeTime, timeAlive, lifePercent) -> {
                }).height(30.0F).heightMoves(30, 50).dontRotate().modify((options, lifeTime, timeAlive, lifePercent) -> {
                    options.size(30);
                }).lifeTime(800);
            } else {
                this.particleDelay++;
            }
        }
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
        return new DisplayStandObjectEntity(level, x, y);
    }

    @Override
    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera) {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        int sprite;
        synchronized(this.drawRandom) {
            sprite = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).nextInt(this.texture.getWidth() / 64);
        }
        texture.initDraw().sprite(sprite, 0, 64, texture.getHeight()).alpha(alpha).draw(drawX - 16, drawY - texture.getHeight() + 32);
    }
}