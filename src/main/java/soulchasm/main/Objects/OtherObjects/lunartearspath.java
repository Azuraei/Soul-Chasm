package soulchasm.main.Objects.OtherObjects;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptionsEnd;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.GrassObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.util.List;

public class lunartearspath extends GrassObject {
    private final GameRandom drawRandom;

    public lunartearspath() {
        super("lunartearspath", 2);
        this.drawRandom = new GameRandom();
        this.toolType = ToolType.PICKAXE;
        this.isLightTransparent = true;
        this.attackThrough = true;
        this.lightLevel = 30;
        this.lightHue = 240.0F;
        this.lightSat = 0.05F;
    }
    @Override
    public void attackThrough(Level level, int x, int y, GameDamage damage, Attacker attacker) {
    }

    public String canPlace(Level level, int layerID, int x, int y, int rotation, boolean byPlayer, boolean ignoreOtherLayers) {
        String error = super.canPlace(level, x, y, rotation, byPlayer);
        if (error != null) {
            return error;
        } else {
            return !level.getTile(x, y).getStringID().equals("soulcavegrass") ? "wrongtile" : null;
        }
    }

    public boolean isValid(Level level, int layerI, int x, int y) {
        if (!super.isValid(level, layerI, x, y)) {
            return false;
        } else {
            int tileID = level.getTileID(x, y);
            return tileID == TileRegistry.getTileID("soulcavegrass");
        }
    }

    @Override
    public void addGrassDrawable(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, int drawX, int drawY, GameLight light, int yOffset, int sortYOffset, int primeIndex, int minTextureIndex, int maxTextureIndex, float alpha) {
        double yGaussian;
        double xGaussian;
        boolean mirror;
        int textureIndex;
        synchronized(this.drawRandom) {
            yGaussian = this.drawRandom.seeded(this.getTileSeed(tileX, tileY, primeIndex)).nextFloat() * 2.0F - 1.0F;
            xGaussian = this.drawRandom.nextFloat() * 2.0F - 1.0F;
            mirror = this.drawRandom.nextBoolean();
            textureIndex = this.drawRandom.getIntBetween(minTextureIndex, maxTextureIndex);
        }
        GameTexture texture = this.textures[textureIndex];
        int offset = yOffset + (int)(yGaussian * (double)this.randomYOffset);
        TextureDrawOptionsEnd next = texture.initDraw().alpha(alpha).mirror(mirror, false);
        if (light != null) {
            next = next.light(light);
        }
        final TextureDrawOptions options = next.pos(drawX + (int)(xGaussian * (double)this.randomXOffset) - this.extraWeaveSpace / 2, drawY - texture.getHeight() + offset + 15);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 0;
            }
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }
}