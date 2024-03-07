package soulchasm.main.Projectiles.WeaponProjectiles;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class soulscytheprojectile extends Projectile {
    private long spawnTime;
    private float startSpeed;

    public soulscytheprojectile() {
    }

    public soulscytheprojectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
        this();
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(damage);
        this.knockback = knockback;
    }

    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextFloat(this.startSpeed);
    }

    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.startSpeed = reader.getNextFloat();
    }
    public void init() {
        super.init();
        this.setWidth(90.0F, true);
        this.isCircularHitbox = true;
        this.isSolid = false;
        this.height = 18.0F;
        this.piercing = 10;
        this.spawnTime = this.getLevel().getWorldEntity().getTime();
        this.startSpeed = this.speed;
    }

    public void onMoveTick(Point2D.Float startPos, double movedDist) {
        super.onMoveTick(startPos, movedDist);
        float perc = Math.abs(GameMath.limit(this.traveledDistance / (float)this.distance, 0.0F, 1.0F) - 1.0F);
        this.speed = Math.max(10.0F, perc * this.startSpeed);
    }

    public Color getParticleColor() {
        return null;
    }

    public Trail getTrail() {
        return null;
    }
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y - this.getHeight()) - this.texture.getHeight() / 2;
            GameLight light = level.getLightLevel(this);
            boolean mirror = this.dx < 0.0F;
            float angle = this.getAngle() * (float)(mirror ? -1 : 1);
            int minLight = 100;
            final TextureDrawOptions options = this.texture.initDraw().sprite(0, 0, 100).mirror(false, !mirror).light(light.minLevelCopy((float)minLight)).rotate(angle, 50, 50).pos(drawX, drawY);
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
        }
    }

    public float getAngle() {
        return (float)(this.getWorldEntity().getTime() - this.spawnTime);
    }
}
