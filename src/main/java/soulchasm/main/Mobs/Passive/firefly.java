package soulchasm.main.Mobs.Passive;

import necesse.engine.network.NetworkClient;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.critters.CritterMob;
import necesse.entity.trails.Trail;
import necesse.entity.trails.TrailVector;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class firefly extends CritterMob {
    public static GameTexture texture;
    public static LootTable lootTable = new LootTable(new LootItem("fireflyitem"));
    public Trail trail;
    private float toMove;
    public float moveAngle;
    public LootTable getLootTable() {
        return lootTable;
    }

    public firefly() {
        super(1);
        this.setSpeed(50.0F);
        this.setFriction(0.4F);
        this.accelerationMod = 0.2F;
        this.decelerationMod = 0.8F;
        this.collision = new Rectangle(-16, -16, 16, 16);
        this.hitBox = new Rectangle(-16, -16, 16, 16);
        this.selectBox = new Rectangle(-16, -16, 32, 32);
        this.canDespawn = true;
    }

    public void init() {
        super.init();
        if (this.getLevel().isClient()) {
            this.trail = new Trail(this, this.getLevel(), new Color(188, 255, 23, 255), 8.0F, 150, 0.0F);
            this.trail.drawOnTop = true;
            this.trail.removeOnFadeOut = false;
            this.getLevel().entityManager.addTrail(this.trail);
        }
    }

    public boolean canDespawn() {
        return this.canDespawn && GameUtils.streamServerClients(this.getLevel()).noneMatch((c) -> this.getDistance(c.playerMob) < (float) (CRITTER_SPAWN_AREA.minSpawnDistance));
    }

    public void tickMovement(float delta) {
        this.toMove += delta;
        while(this.toMove > 4.0F) {
            float oldX = this.x;
            float oldY = this.y;
            super.tickMovement(4.0F);
            this.toMove -= 4.0F;
            Point2D.Float d = GameMath.normalize(oldX - this.x, oldY - this.y);
            this.moveAngle = (float)Math.toDegrees(Math.atan2(d.y, d.x)) - 90.0F;
            if (this.trail != null) {
                float trailOffset = 0.1F;
                this.trail.addPoint(new TrailVector(this.x + d.x * trailOffset, this.y + d.y * trailOffset, -d.x, -d.y, this.trail.thickness, 0.0F));
            }
        }
    }

    public boolean canTakeDamage() {
        return false;
    }
    public boolean canBeTargeted(Mob attacker, NetworkClient attackerClient) {
        return true;
    }
    protected void checkCollision() {}
    public boolean canPushMob(Mob other) {
        return false;
    }
    public boolean canBePushed(Mob other) {
        return false;
    }
    public boolean isHealthBarVisible() {
        return false;
    }
    protected void playDeathSound() {}
    protected void playHitSound() {}
    public void spawnDamageText(int dmg, int size, boolean isCrit) {}

    @Override
    public boolean isValidSpawnLocation(Server server, ServerClient client, int targetX, int targetY) {
        if (!this.getLevel().getServer().world.worldEntity.isNight()){
            return false;
        } else {
            return super.isValidSpawnLocation(server, client, targetX, targetY);
        }
    }

    public void clientTick() {
        super.clientTick();
        this.getLevel().lightManager.refreshParticleLightFloat(this.x, this.y, new Color(0xEAFF69), 0.7F, 80);
    }
    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 3;
        int drawY = camera.getDrawY(y) - 3;
        DrawOptions body = texture.initDraw().sprite(GameUtils.getAnim(this.getWorldEntity().getTime(), 2, 1200), 0, 6, 6).light(light.minLevelCopy(80)).pos(drawX, drawY);
        topList.add((tm) -> body.draw());
    }

    public void dispose() {
        super.dispose();
        if (this.trail != null) {
            this.trail.removeOnFadeOut = true;
        }

    }
}
