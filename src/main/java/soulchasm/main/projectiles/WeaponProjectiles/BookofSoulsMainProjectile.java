package soulchasm.main.projectiles.WeaponProjectiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
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
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;
import soulchasm.SoulChasm;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class BookofSoulsMainProjectile extends FollowingProjectile {
    public BookofSoulsMainProjectile() {
        this.height = 18.0F;
    }

    public BookofSoulsMainProjectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
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
        this.turnSpeed = 0.2F;
        this.givesLight = true;
        this.trailOffset = -8.0F;
        this.clearTargetPosWhenAligned = true;
    }

    private void spawnBreakParticles(Level level, float x, float y){
        for(int i = 0; i < 35; ++i) {
            int angle = GameRandom.globalRandom.nextInt(360);
            Point2D.Float dir = GameMath.getAngleDir((float)angle);
            level.entityManager.addParticle(x, y, Particle.GType.COSMETIC).sprite(SoulChasm.particleBookSection).fadesAlpha(1.0F, 0.8F).fadesAlphaTime(0, 800).size((options, lifeTime, timeAlive, lifePercent) -> {
            }).height(12.0F).movesConstant((float)GameRandom.globalRandom.getIntBetween(15, 30) * dir.x, (float)GameRandom.globalRandom.getIntBetween(15, 30) * dir.y).sizeFades(22, 12).modify((options, lifeTime, timeAlive, lifePercent) -> {
            }).lifeTime(850);
        }
    }

    public Trail getTrail() {
        Trail trail = new Trail(this, this.getLevel(), new Color(0, 170, 242), 40.0F, 350, this.height);
        trail.sprite = new GameSprite(GameResources.chains, 7, 0, 32);
        return trail;
    }

    protected Color getWallHitColor() {
        return new Color(0, 170, 242);
    }

    public void refreshParticleLight() {
        this.getLevel().lightManager.refreshParticleLightFloat(this.x, this.y, 260.0F, this.lightSaturation);
    }

    public void updateTarget() {
        this.findTarget((m) -> m.isHostile, 100.0F, 300.0F);
        if (this.target != null) {
            this.targetPos = null;
        }
    }

    @Override
    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) {
        super.doHitLogic(mob, object, x, y);
        if(this.getLevel().isClient()){
            if (mob != null || object != null) {
                spawnBreakParticles(this.getLevel(), x, y);
            }
        } else {
            int range = 50;
            Rectangle targetBox = new Rectangle((int)targetX - range, (int)targetY - range, range * 2, range * 2);
            this.streamTargets(this.getOwner(), targetBox).filter((m) -> this.canHit(m) && m.getDistance(targetX, targetY) <= (float)range).forEach((m) -> m.isServerHit(this.getDamage().modDamage(0.5F), m.x - x, m.y - y, (float)this.knockback, this));
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        if (!this.removed()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - this.texture.getWidth() / 2;
            int drawY = camera.getDrawY(this.y);
            final TextureDrawOptions options = this.texture.initDraw().light(light.minLevelCopy(Math.min(light.getLevel() + 100.0F, 150.0F))).rotate(this.getAngle(), this.texture.getWidth() / 2, 0).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this) {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
        }
    }
}