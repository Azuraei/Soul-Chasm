package soulchasm.main.Items;

import necesse.engine.registries.ObjectRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.GameResources;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptionsEnd;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.objectItem.ObjectItem;
import necesse.level.maps.light.GameLight;

import java.awt.*;

public class fireflyjar extends ObjectItem {
    public fireflyjar() {
        super(ObjectRegistry.getObject("fireflyjarobject"), false);
        this.stackSize = 20;
        this.rarity = Rarity.UNCOMMON;

    }

    @Override
    public void loadTextures() {
        super.loadTextures();
        this.holdTexture = GameTexture.fromFile("player/holditems/fireflyjar");
    }
    @Override
    public DrawOptions getHoldItemDrawOptions(InventoryItem item, PlayerMob player, int spriteX, int spriteY, int drawX, int drawY, int width, int height, boolean mirrorX, boolean mirrorY, GameLight light, float alpha, GameTexture mask) {
        int xOffset = 0;
        int yOffset = 0;
        TextureDrawOptionsEnd options;
        if (this.holdTexture.getHeight() / 128 == 4) {
            width *= 2;
            height *= 2;
            xOffset = -32;
            yOffset = -32;
            options = this.holdTexture.initDraw().sprite(spriteX, spriteY, 128);
            if (mask != null) {
                options.addShaderState(GameResources.edgeMaskShader.addMaskOffset(xOffset, yOffset));
            }
        } else {
            options = this.holdTexture.initDraw().sprite(spriteX, spriteY, 64);
        }
        float minLight = 100;
        options = options.light(light.minLevelCopy((float)minLight)).alpha(alpha).size(width, height).mirror(mirrorX, mirrorY).addShaderTextureFit(mask, 1);
        return options.pos(drawX + xOffset, drawY + yOffset);
    }

    @Override
    public void tickHolding(InventoryItem item, PlayerMob player) {
        super.tickHolding(item, player);
        player.getLevel().lightManager.refreshParticleLightFloat(player.x - 3, player.y - 3, new Color(0xC8FF27), 0.7F, 75);
    }
}
