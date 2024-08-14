package soulchasm.main.Objects.OtherObjects;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static soulchasm.SoulChasm.SoulCrystalColor;

public class soulcrystalbig extends GameObject {
    protected String textureName;
    protected String glowtextureName;
    public GameTexture texture;
    public GameTexture glowtexture;
    protected final GameRandom drawRandom;

    public soulcrystalbig() {
        super(new Rectangle(7, 7, 22, 18));
        this.textureName = "soulcrystalbig";
        this.glowtextureName = "soulcrystalbig_glow";
        this.mapColor = SoulCrystalColor;
        this.toolTier = 5;
        this.displayMapTooltip = true;
        this.drawDamage = false;
        this.isLightTransparent = true;
        this.drawRandom = new GameRandom();
        this.canPlaceOnLiquid = false;
        this.isOre = true;
        this.isIncursionExtractionObject = false;
    }

    public void loadTextures() {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/" + this.textureName);
        this.glowtexture = GameTexture.fromFile("objects/" + this.glowtextureName);

    }

    public int getRandomYOffset(int tileX, int tileY) {
        synchronized(this.drawRandom) {
            return (int)((this.drawRandom.seeded(this.getTileSeed(tileX, tileY, 1)).nextFloat() * 2.0F - 1.0F) * 8.0F) - 4;
        }
    }

    protected Rectangle getCollision(Level level, int x, int y, int rotation) {
        Rectangle collision = super.getCollision(level, x, y, rotation);
        collision.y += this.getRandomYOffset(x, y);
        return collision;
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX)-14;
        int drawY = camera.getTileDrawY(tileY);
        final int randomYOffset = this.getRandomYOffset(tileX, tileY);
        int sprite;
        synchronized(this.drawRandom) {
            sprite = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).nextInt(this.texture.getWidth() / 64);
        }

        drawY += randomYOffset;
        final TextureDrawOptions options = this.texture.initDraw().sprite(sprite, 0, 64, this.texture.getHeight()).light(light).pos(drawX, drawY - this.texture.getHeight() + 32);
        final TextureDrawOptions glow = this.glowtexture.initDraw().sprite(sprite, 0, 64, this.texture.getHeight()).light(light.minLevelCopy(30F)).pos(drawX, drawY - this.texture.getHeight() + 32);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16 + randomYOffset;
            }

            public void draw(TickManager tickManager) {
                options.draw();
                glow.draw();
            }
        });
    }

    @Override
    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera) {
        int drawX = camera.getTileDrawX(tileX)-14;
        int drawY = camera.getTileDrawY(tileY);
        final int randomYOffset = this.getRandomYOffset(tileX, tileY);
        int sprite;
        synchronized(this.drawRandom) {
            sprite = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).nextInt(this.texture.getWidth() / 64);
        }
        drawY += randomYOffset;
        texture.initDraw().sprite(sprite, 0, 64, this.texture.getHeight()).alpha(alpha).draw(drawX, drawY - this.texture.getHeight() + 32);
    }

    public void tickEffect(Level level, int x, int y) {
        super.tickEffect(level, x, y);
        if (GameRandom.globalRandom.getChance(0.04F) && !level.getObject(x, y).drawsFullTile() && level.getLightLevel(x, y).getLevel() > 0.0F) {
            int posX = x * 32 + GameRandom.globalRandom.nextInt(32);
            int posY = y * 32 + GameRandom.globalRandom.nextInt(20);
            boolean mirror = GameRandom.globalRandom.nextBoolean();
            level.entityManager.addParticle((float)posX, (float)(posY + 30), Particle.GType.COSMETIC).sprite(GameResources.magicSparkParticles.sprite(GameRandom.globalRandom.nextInt(4), 0, 22, 22)).color(new Color(0x76D6FF)).fadesAlpha(0.4F, 0.4F).size((options, lifeTime, timeAlive, lifePercent) -> {
            }).height(30.0F).movesConstant(GameRandom.globalRandom.getFloatBetween(0.2F, 0.2F) * (Float)GameRandom.globalRandom.getOneOf(new Float[]{1.0F, -1.0F}), GameRandom.globalRandom.getFloatBetween(0.2F, 0.2F) * (Float)GameRandom.globalRandom.getOneOf(new Float[]{1.0F, -1.0F})).sizeFades(15, 25).modify((options, lifeTime, timeAlive, lifePercent) -> options.mirror(mirror, false)).lifeTime(3000);
        }
    }

    public LootTable getLootTable(Level level, int tileX, int tileY) {
        return new LootTable(LootItem.between("crystalizedsouloreitem", 3, 5));
    }

    public void playDamageSound(Level level, int x, int y, boolean damageDone) {
        SoundManager.playSound(GameResources.crystalHit2, SoundEffect.effect((float)(x * 32 + 16), (float)(y * 32 + 16)).volume(2.0F).pitch(GameRandom.globalRandom.getFloatBetween(0.9F, 1.1F)));
    }

    public int getLightLevel() {
        return 60;
    }
    @Override
    public GameLight getLight(Level level, int x, int y) {
        return level.lightManager.newLight(240.0F, 0.2F, (float)this.getLightLevel());
    }
}
