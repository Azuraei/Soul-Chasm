package soulchasm.main.Projectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class SoulHomingProjectile extends FollowingProjectile {
    public SoulHomingProjectile() {
        this.height = 18.0F;
    }

    public SoulHomingProjectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
        this();
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.setDistance(distance);
        this.setDamage(damage);
        this.knockback = knockback;
    }

    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextFloat(this.height);
        writer.putNextFloat(this.turnSpeed);
    }

    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.height = reader.getNextFloat();
        this.turnSpeed = reader.getNextFloat();
    }

    public void init() {
        super.init();
        this.turnSpeed = 0.4F;
        this.givesLight = true;
        this.trailOffset = -8.0F;
        this.clearTargetPosWhenAligned = true;
    }

    public Trail getTrail() {
        Trail trail = new Trail(this, this.getLevel(), new Color(101, 188, 240), 30.0F, 250, this.height);
        trail.sprite = new GameSprite(GameResources.chains, 7, 0, 32);
        return trail;
    }

    protected Color getWallHitColor() {
        return new Color(25, 187, 245);
    }

    public void refreshParticleLight() {
        this.getLevel().lightManager.refreshParticleLightFloat(this.x, this.y, 260.0F, this.lightSaturation);
    }

    public void updateTarget() {
        if(this.getOwner() != null){
            if (this.getOwner().isPlayer || !this.getOwner().isHostile){
                if (this.traveledDistance > 30){
                    this.findTarget((m) -> m.isHostile, 100.0F, 300.0F);
                }
            } else if (this.getOwner().isHostile) {
                if (this.traveledDistance > 60){
                    this.findTarget((m) -> m.isPlayer, 100.0F, 300.0F);
                }
            }
        }
        if (this.target != null) {
            this.targetPos = null;
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y)  - (int) this.getHeight();
            final TextureDrawOptions options = this.texture.initDraw().light(light.minLevelCopy(Math.min(light.getLevel() + 100.0F, 150.0F))).rotate(this.getAngle(), this.texture.getWidth() / 2, 0).pos(drawX, drawY);
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
        }
    }
}