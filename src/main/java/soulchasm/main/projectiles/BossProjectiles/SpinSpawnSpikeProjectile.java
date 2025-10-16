package soulchasm.main.projectiles.BossProjectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
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

import java.awt.*;
import java.util.List;

public class SpinSpawnSpikeProjectile extends Projectile {
    private int tickCounter;

    public SpinSpawnSpikeProjectile() {
    }

    public SpinSpawnSpikeProjectile(Level level, float x, float y, float targetX, float targetY, int distance, GameDamage damage, int knockback, Mob owner) {
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setDistance(distance);
        this.setOwner(owner);
    }

    public void init() {
        super.init();
        this.height = 18.0F;
        this.piercing = 0;
        this.trailOffset = -20;
        this.tickCounter = 0;
        this.speed = 0.1F;
    }

    public void clientTick() {
        super.clientTick();
        ++this.tickCounter;
        if (this.tickCounter == 40) {
            this.speed = 100;
        }
    }

    public void serverTick() {
        super.serverTick();
        ++this.tickCounter;
        if (this.tickCounter == 40) {
            this.speed = 100;
        }
    }

    public Color getParticleColor() {
        return null;
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(0x65BCF0), 12.0F, 700, this.getHeight()) {
            public Color getColor() {
                return new Color(0x65BCF0);
            }
        };
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y);
            final TextureDrawOptions options = this.texture.initDraw().rotate(this.getAngle(), this.texture.getWidth() / 2, 0).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
        }
    }

}
