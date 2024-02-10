package soulchasm.main.Objects.OtherObjects;

import necesse.engine.Screen;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.GameObject;
import necesse.level.gameObject.ObjectHoverHitbox;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.SoulChasm;
import soulchasm.main.Mobs.Boss.souldragonhead;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class soulbossaltarobject extends GameObject {
    public GameTexture texture;
    public GameTexture textureBall;
    public GameTexture groundParticles;
    private float angle;
    private float alphaBall;

    public soulbossaltarobject() {
        super(new Rectangle(0, 2, 36, 22));
        this.toolType = ToolType.UNBREAKABLE;
        this.mapColor = new Color(4, 33, 45);
        this.drawDamage = false;
        this.isLightTransparent = false;
        this.lightLevel = 160;
        this.lightHue = 240.0F;
        this.lightSat = 0.6F;
        this.angle = 0;
        this.alphaBall = 0.8F;
    }

    @Override
    public void loadTextures() {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/soulbossaltarobject");
        textureBall = GameTexture.fromFile("particles/altarball");
        groundParticles = GameTexture.fromFile("particles/stoneParticles");
    }

    @Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        TextureDrawOptions options = texture.initDraw().light(light).pos(drawX - 16, drawY - texture.getHeight() + 32);
        final TextureDrawOptions optionsBall = textureBall.initDraw().light(light.minLevelCopy(100)).alpha(this.alphaBall).rotate(this.angle, textureBall.getWidth() / 2, textureBall.getHeight() / 2).pos(drawX - 8, drawY - 64);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            @Override
            public int getSortY() {
                return 16;
            }
            @Override
            public void draw(TickManager tickManager) {
                options.draw();
                optionsBall.draw();
            }
        });
    }
    private void spawnBreakParticles(Level level, int x, int y){
        int posX = x * 32 + 16;
        int posY = y * 32 - 32;
        for(int i = 0; i < 100; ++i) {
            int angle = GameRandom.globalRandom.nextInt(360);
            Point2D.Float dir = GameMath.getAngleDir((float)angle);
            level.entityManager.addParticle((float)posX, (float)(posY + 16), Particle.GType.COSMETIC).sprite(SoulChasm.particleWispSection).fadesAlpha(0.2F, 0.8F).size((options, lifeTime, timeAlive, lifePercent) -> {
            }).height(30.0F).movesConstant((float)GameRandom.globalRandom.getIntBetween(20, 80) * dir.x, (float)GameRandom.globalRandom.getIntBetween(20, 80) * dir.y).sizeFades(20, 10).modify((options, lifeTime, timeAlive, lifePercent) -> {
            }).lifeTime(2000);
        }
    }
    private void spawnBallParticles(Level level, int x, int y){
        int posX = x * 32 + 16 + GameRandom.globalRandom.getIntBetween(-8, 8);
        int posY = y * 32;
        boolean mirror = GameRandom.globalRandom.nextBoolean();
        level.entityManager.addParticle((float)posX, (float)(posY + 16), Particle.GType.COSMETIC).sprite(SoulChasm.particleWispSection).fadesAlpha(0.6F, 0.6F).size((options, lifeTime, timeAlive, lifePercent) -> {
        }).height(30.0F).movesConstant(GameRandom.globalRandom.getFloatBetween(0.1F, 0.5F) * GameRandom.globalRandom.getOneOf(1.0F, -1.0F), GameRandom.globalRandom.getFloatBetween(-3.0F, -5.0F)).sizeFades(10, 20).modify((options, lifeTime, timeAlive, lifePercent) -> {
            options.mirror(mirror, false);
        }).lifeTime(7500);
    }
    private void spawnGroundParticles(Level level, int x, int y) {
        int posX = x * 32 + 16;
        int posY = y * 32 + 128;
        for (int i = 0; i < 25; i++) {
            int angle = GameRandom.globalRandom.nextInt(360);
            Point2D.Float dir = GameMath.getAngleDir((float)angle);
            level.entityManager.addParticle(new FleshParticle(level, groundParticles, GameRandom.globalRandom.nextInt(5), 0, 32, posX, posY, 40f, dir.x * 10, dir.y * 10), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void tickEffect(Level level, int x, int y) {
        super.tickEffect(level, x, y);
        Rectangle bounds = GameUtils.rangeTileBounds(x * 32 + 16, y * 32 + 16, 50);
        if (level.entityManager.mobs.streamInRegionsShape(bounds, 0).noneMatch((m) -> m instanceof souldragonhead)) {
            this.angle += 5;
            if(this.alphaBall<=0.8){
                this.alphaBall += 0.005F;
            }
            if (GameRandom.globalRandom.getChance(0.60F)) {
                AtomicReference<Float> currentAngle = new AtomicReference<>(GameRandom.globalRandom.nextFloat() * 360.0F);
                float distance = 45F;
                level.entityManager.addParticle(x * 32 - 16 + GameMath.sin(currentAngle.get()) * distance, y * 32 + GameMath.cos(currentAngle.get()) * distance * 0.75F, Particle.GType.CRITICAL).color(new Color(0x42B2FD)).height(32F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
                    float angle = currentAngle.accumulateAndGet(delta * 100.0F / 250.0F, Float::sum);
                    float distY = distance * 0.75F;
                    pos.x = x * 32 + 16 + -GameMath.sin(angle) * distance;
                    pos.y = y * 32 + GameMath.cos(angle) * distY * 0.75F + (float) timeAlive/lifeTime * 16;
                }).lifeTime(3000).sizeFades(10, 15);
            }
            if (GameRandom.globalRandom.getChance(0.08F)) {
                spawnBallParticles(level, x, y);
            }
        } else {
            this.alphaBall = 0F;
        }
    }

    public List<ObjectHoverHitbox> getHoverHitboxes(Level level, int tileX, int tileY) {
        List<ObjectHoverHitbox> list = super.getHoverHitboxes(level, tileX, tileY);
        list.add(new ObjectHoverHitbox(tileX, tileY, 0, -32, 32, 32));
        return list;
    }
    public boolean canInteract(Level level, int x, int y, PlayerMob player) {
        return true;
    }
    public String getInteractTip(Level level, int x, int y, PlayerMob perspective, boolean debug) {
        return Localization.translate("controls", "senshiactivatetip");
    }

    public void interact(Level level, int x, int y, PlayerMob player) {
        super.interact(level, x, y, player);
        Item item = ItemRegistry.getItem("soulsigil");
        if (!player.isItemOnCooldown(item)) {
            if (player.getInv().removeItems(item, 1, false, false, false, "use") > 0) {
                player.startItemCooldown(item, 4000);
                if(level.isClient()){
                    spawnBreakParticles(level, x, y);
                    spawnGroundParticles(level, x, y);
                    Screen.playSound(GameResources.jingle, SoundEffect.globalEffect().volume(1.6F).pitch(1.5F));
                }
                if (level.isServer()) {
                    System.out.println("Ancient Dragon has been awakened at " + level.getIdentifier() + ".");
                    Mob mob = MobRegistry.getMob("souldragonhead", level);
                    level.entityManager.addMob(mob, (float)(x * 32 + 16), (float)(y * 32 + 16) + 128);
                    level.getServer().network.sendToClientsAt(new PacketChatMessage(new LocalMessage("misc", "bosssummon", "name", MobRegistry.getLocalization("souldragonhead"))), level);
                    if (level instanceof IncursionLevel) {
                        ((IncursionLevel)level).onBossSummoned(mob);
                    }
                }
            } else if (level.isServer() && player.isServerClient()) {
                player.getServerClient().sendChatMessage(new LocalMessage("misc", "bossmissingitem"));
            }
        }
    }

    public int getLightLevel(Level level, int x, int y) {
        return this.lightLevel;
    }
}