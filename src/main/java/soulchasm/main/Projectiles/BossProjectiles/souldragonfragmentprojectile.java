package soulchasm.main.Projectiles.BossProjectiles;

import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;



public class souldragonfragmentprojectile extends FollowingProjectile {
    private long spawnTime;

    public souldragonfragmentprojectile() {
    }

    public souldragonfragmentprojectile(Level level,float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, Mob owner) {
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.setDistance(distance);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setOwner(owner);
    }

    public void init() {
        super.init();
        this.height = 18.0F;
        this.setWidth(25.0F);
        this.spawnTime = this.getLevel().getWorldEntity().getTime();
        this.givesLight = true;
    }

    @Override
    public Color getParticleColor() {
        return new Color(0, 187, 255);
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) {
        if (this.getLevel().isServer()) {
            byte projectiles;
            if (mob == null) {
                projectiles = 12;
            } else {
                projectiles = 6;
                mob.buffManager.addBuff(new ActiveBuff("soulbleedstackbuff", mob, 10.0F, this.getOwner()), mob.getLevel().isServerLevel());
            }

            float startX = x - this.dx * 2.0F;
            float startY = y - this.dy * 2.0F;
            float angle = (float)GameRandom.globalRandom.nextInt(360);

            for(int i = 0; i < projectiles; ++i) {
                Point2D.Float dir = GameMath.getAngleDir(angle + (float)i * 360.0F / (float)projectiles);
                soulbossspikeprojectile projectile = new soulbossspikeprojectile(this.getLevel(), startX, startY, startX + dir.x * 100.0F, startY + dir.y * 100.0F, 80, 1200, this.getDamage().modFinalMultiplier(0.5F), this.knockback, this.getOwner());
                if (mob != null) {
                    projectile.startHitCooldown(mob);
                }
                this.getLevel().entityManager.projectiles.add(projectile);


            }

        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y) - this.texture.getHeight() / 2;
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle(), this.texture.getWidth() / 2, this.texture.getHeight() / 2).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            TextureDrawOptions shadowOptions = this.shadowTexture.initDraw().light(light).rotate(this.getAngle(), this.shadowTexture.getWidth() / 2, this.shadowTexture.getHeight() / 2).pos(drawX, drawY);
            tileList.add((tm) -> {
                shadowOptions.draw();
            });
        }
    }

    public float getAngle() {
        return (float)(this.getWorldEntity().getTime() - this.spawnTime) / 4.0F;
    }
}
