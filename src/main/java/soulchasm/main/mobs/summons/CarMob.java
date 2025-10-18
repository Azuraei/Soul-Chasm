package soulchasm.main.mobs.summons;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.gameLoop.tickManager.TicksPerSecond;
import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.summon.summonFollowingMob.mountFollowingMob.MountFollowingMob;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.GameObject;
import necesse.level.gameTile.GameTile;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.SoulChasm;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static soulchasm.SoulChasm.carMask;

public class CarMob extends MountFollowingMob {
    protected TicksPerSecond particleTicks = TicksPerSecond.ticksPerSecond(30);
    public static GameTexture texture_body;
    public static GameTexture texture_top;
    public static GameTexture texture_mask;
    public int colorIndex;
    private final ArrayList<Color> colors = SoulChasm.carColors;

    public CarMob() {
        super(100);
        this.setSpeed(175.0F);
        this.setFriction(3.0F);
        this.setSwimSpeed(0.2F);
        this.accelerationMod = 0.15F;
        this.decelerationMod = 0.4F;
        this.collision = new Rectangle(-16, -16, 32, 32);
        this.hitBox = new Rectangle(-15, -15, 30, 30);
        this.selectBox = new Rectangle(-15, -15, 30, 30);
    }

    @Override
    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextInt(colorIndex);
    }

    @Override
    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.colorIndex = reader.getNextInt();
    }

    public void tickMovement(float delta) {
        super.tickMovement(delta);
        if (this.getLevel().isClient() && (this.moveX != 0.0F || this.moveY != 0.0F)) {
            this.particleTicks.tick(delta);
            Color particleColor = new Color(0x040A23);
            float dirX;
            float dirY;
            float dirX2;
            float dirY2;
            while (this.particleTicks.shouldTick()) {
                switch (this.getDir()){
                    case 0:
                        dirY = -2.0F;
                        dirY2 = -2.0F;
                        dirX = 0.5F;
                        dirX2 = -0.5F;
                        break;
                    case 1:
                        dirX = 1.5F;
                        dirY = 0.0F;
                        dirX2 = 1.5F;
                        dirY2 = 1.0F;
                        break;
                    case 2:
                        dirY = 2.0F;
                        dirY2 = 2.0F;
                        dirX = -0.5F;
                        dirX2 = 0.5F;
                        break;
                    case 3:
                        dirX = -1.5F;
                        dirY = 0.0F;
                        dirX2 = -1.5F;
                        dirY2 = 1.0F;
                        break;
                    default:
                        dirX = 0.0F;
                        dirY = 0.0F;
                        dirX2 = 0.0F;
                        dirY2 = 0.0F;
                        break;
                }
                this.getLevel().entityManager.addParticle(this.x - dirX * 20.0F + GameRandom.globalRandom.floatGaussian() * 3.0F, this.y - dirY * 12.0F + GameRandom.globalRandom.floatGaussian() * 3.0F, Particle.GType.COSMETIC).movesConstant(this.dx / 5.0F, this.dy / 5.0F).color(particleColor).sizeFades(6, 12).lifeTime(1000);
                this.getLevel().entityManager.addParticle(this.x - dirX2 * 20.0F + GameRandom.globalRandom.floatGaussian() * 3.0F, this.y - dirY2 * 12.0F + GameRandom.globalRandom.floatGaussian() * 3.0F, Particle.GType.COSMETIC).movesConstant(this.dx / 5.0F, this.dy / 5.0F).color(particleColor).sizeFades(6, 12).lifeTime(1000);
            }
        }
    }

    public boolean canCollisionHit(Mob target) {
        return this.currentSpeed > this.getSpeed() * 0.5 && super.canCollisionHit(target);
    }

    public float getSpeedCarMult(){
        return this.getCurrentSpeed()/this.getSpeed();
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) {
        return new GameDamage(100 * getSpeedCarMult(), 10, 100.0F);
    }

    public int getCollisionKnockback(Mob target) {
        return (int) (300 * getSpeedCarMult());
    }

    public void serverTick() {
        super.serverTick();
        if (!this.isMounted()) {
            this.moveX = 0.0F;
            this.moveY = 0.0F;
        }
    }

    public void clientTick() {
        super.clientTick();
        if (!this.isMounted()) {
            this.moveX = 0.0F;
            this.moveY = 0.0F;
        }
    }

    protected String getInteractTip(PlayerMob perspective, boolean debug) {
        return this.isMounted() ? null : Localization.translate("controls", "usetip");
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 31;
        int drawY = camera.getDrawY(y) - 40;
        Point sprite = this.getAnimSprite(x, y, this.getDir());
        drawY += this.getBobbing(x, y);
        Color current_color = colors.get(colorIndex);
        final DrawOptions options_body = texture_body.initDraw().sprite(0, sprite.y, 64).colorMult(current_color).light(light).pos(drawX , drawY);
        final DrawOptions options_top = texture_top.initDraw().sprite(0, sprite.y, 64).light(light).pos(drawX , drawY);
        final DrawOptions options_mask = texture_mask.initDraw().sprite(0, sprite.y, 64).light(light).pos(drawX , drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options_mask.draw();
            }
            public void drawBehindRider(TickManager tickManager) {
                options_body.draw();
                options_top.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    @Override
    public boolean canBePushed(Mob other) {
        return other instanceof CarMob;
    }

    @Override
    public float getSpeedModifier() {
        float swimMod = this.inLiquid() && !this.isFlying() ? this.getSwimSpeed() : 1.0F;
        GameTile tile = this.getLevel().getTile(this.getTileX(), this.getTileY());
        GameObject object = this.getLevel().getObject(this.getTileX(), this.getTileY());
        float buffMod = this.buffManager.getAndApplyModifiers(BuffModifiers.SPEED, tile.getSpeedModifier(this).max(1.4F), object.getSpeedModifier(this));
        float slowMod = 1.0F - this.buffManager.getAndApplyModifiers(BuffModifiers.SLOW, tile.getSlowModifier(this), object.getSlowModifier(this));
        float attackMod = 1.0F;
        Mob mounted = this.getRider();
        if (mounted != null && mounted.isAttacking) {
            attackMod = mounted.getAttackingMovementModifier();
        } else if (this.isAttacking) {
            attackMod = this.getAttackingMovementModifier();
        }
        return buffMod * attackMod * slowMod * swimMod;
    }

    public int getRockSpeed() {
        return 50;
    }
    public int getWaterRockSpeed() {
        return 50;
    }
    public int getRiderDrawXOffset(){ return 1; }
    public int getRiderDrawYOffset() {
        return -4;
    }
    public int getRiderArmSpriteX() {
        return 0;
    }
    public int getRiderMaskXOffset() {
        return 0;
    }
    public int getRiderMaskYOffset() {
        return -14;
    }
    public GameTexture getRiderMask() {
        return carMask[GameMath.limit(this.getDir(), 0, carMask.length - 1)];
    }
}
