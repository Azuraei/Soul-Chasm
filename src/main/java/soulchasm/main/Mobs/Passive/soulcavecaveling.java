package soulchasm.main.Mobs.Passive;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.HumanTexture;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.critters.caveling.CavelingMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptionsList;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.level.gameObject.GameObject;
import necesse.level.gameObject.SingleRockSmall;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.SoulChasm;

import java.awt.*;
import java.util.List;


public class soulcavecaveling extends CavelingMob {
    public static GameTexture texture;
    public static GameTexture texture_back;
    public static GameTexture texture_front;

    public soulcavecaveling() {
        super(350, 55);
    }

    public void init() {
        super.init();
        this.popParticleColor = SoulChasm.SoulStoneColorLight;
        this.singleRockSmallStringID = "soulcaverockssmall";
        if (this.item == null) {
            this.item = GameRandom.globalRandom.getOneOf(new InventoryItem("crystalizedsouloreitem", GameRandom.globalRandom.getIntBetween(8, 12)));
        }

    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 48;
        HumanTexture texture = soulcavecaveling.texture != null ? new HumanTexture(soulcavecaveling.texture, soulcavecaveling.texture, soulcavecaveling.texture) : MobRegistry.Textures.stoneCaveling;
        Point sprite = this.getAnimSprite(x, y, this.getDir());
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        boolean asRock = false;
        int itemBobbing;
        if (this.dx == 0.0F && this.dy == 0.0F && this.singleRockSmallStringID != null) {
            GameObject gameObject = ObjectRegistry.getObject(this.singleRockSmallStringID);
            if (gameObject instanceof SingleRockSmall) {
                GameTexture rockTexture = ((SingleRockSmall)gameObject).texture;
                itemBobbing = (new GameRandom((long)this.getUniqueID())).nextInt(rockTexture.getWidth() / 32);
                final TextureDrawOptions drawOptions = rockTexture.initDraw().sprite(itemBobbing, 0, 32, rockTexture.getHeight()).light(light).pos(drawX + 16, drawY + 16 - rockTexture.getHeight() + 32);
                asRock = true;
                list.add(new MobDrawable() {
                    public void draw(TickManager tickManager) {
                        drawOptions.draw();
                    }
                });
            }
        }

        if (!asRock) {
            final DrawOptionsList drawOptions = new DrawOptionsList();
            drawOptions.add(texture_front.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY));
            drawOptions.add(texture.body.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY));
            if (this.item != null) {
                Color drawColor = this.item.item.getDrawColor(this.item, perspective);
                itemBobbing = sprite.x != 1 && sprite.x != 3 ? 0 : 2;
                drawOptions.add(this.item.item.getItemSprite(this.item, perspective).initDraw().colorLight(drawColor, light).mirror(sprite.y < 2, false).size(32).posMiddle(drawX + 32, drawY + 16 + itemBobbing));
            }

            drawOptions.add(texture_front.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY));
            list.add(new MobDrawable() {
                public void draw(TickManager tickManager) {
                    drawOptions.draw();
                }
            });
            TextureDrawOptions shadow = MobRegistry.Textures.caveling_shadow.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY);
            tileList.add((tm) -> {
                shadow.draw();
            });
        }

    }


}
