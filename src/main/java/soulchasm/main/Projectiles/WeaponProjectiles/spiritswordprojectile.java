package soulchasm.main.Projectiles.WeaponProjectiles;

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
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class spiritswordprojectile extends Projectile {
    public float fadeInAndOutMod;

    public spiritswordprojectile(){

    }

    public spiritswordprojectile(Level level, float x, float y, float targetX, float targetY, float targetSpeed, int distance, GameDamage damage, int knockback, Mob owner) {
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setDistance(distance);
        this.setOwner(owner);
        this.moveDist(20.0);
        this.speed = targetSpeed;
    }

    public void init() {
        super.init();
        this.height = 18.0F;
        this.setWidth(10.0F);
        this.trailOffset = -50.0F;
        this.heightBasedOnDistance = true;
        this.doesImpactDamage = true;
        this.piercing = 3;
        this.fadeInAndOutMod = 0.01F;
    }

    protected CollisionFilter getLevelCollisionFilter() {
        return null;
    }

    @Override
    public void clientTick() {
        super.clientTick();
        if(this.traveledDistance < this.distance/3F && fadeInAndOutMod<=1){
            fadeInAndOutMod += 0.30F;
        } else if (this.traveledDistance > 2*this.distance/3F && fadeInAndOutMod>0) {
            fadeInAndOutMod -= 0.30F;
        }
    }

    public Trail getTrail() {
        return new Trail(this, this.getLevel(), new Color(19, 127, 241), 10.0F, 100, 18.0F);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - 2;
            int drawY = camera.getDrawY(this.y) - 2;
            final TextureDrawOptions options = spiritswordprojectile.this.texture.initDraw().light(light.minLevelCopy(Math.min(light.getLevel() + 100.0F, 150.0F))).rotate(this.getAngle() + 45.0F, 2, 2).pos(drawX, drawY - (int)this.getHeight()).alpha(fadeInAndOutMod);
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
        }
    }

    protected void playHitSound(float x, float y) {
    }
}
