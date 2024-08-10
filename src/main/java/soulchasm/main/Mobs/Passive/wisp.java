package soulchasm.main.Mobs.Passive;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.NetworkClient;
import necesse.engine.util.GameMath;
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

public class wisp extends CritterMob {
    public static GameTexture texture;
    public static LootTable lootTable = new LootTable(new LootItem("wispitem"));
    public Trail trail;
    private float toMove;
    public float moveAngle;
    public LootTable getLootTable() {
        return lootTable;
    }

    public wisp() {
        super(1);
        this.setSpeed(50.0F);
        this.setFriction(0.4F);
        this.accelerationMod = 0.2F;
        this.decelerationMod = 0.8F;
        this.collision = new Rectangle(-16, -16, 16, 16);
        this.hitBox = new Rectangle(-16, -16, 16, 16);
        this.selectBox = new Rectangle(-16, -16, 32, 32);
    }

    public void init() {
        super.init();
        if (this.getLevel().isClient()) {
            this.trail = new Trail(this, this.getLevel(), new Color(55, 183, 255, 255), 8.0F, 250, 0.0F);
            this.trail.drawOnTop = true;
            this.trail.removeOnFadeOut = false;
            this.getLevel().entityManager.addTrail(this.trail);
        }
    }

    public void tickMovement(float delta) {
        this.toMove += delta;
        while(this.toMove > 4.0F) {
            float oldX = this.x;
            float oldY = this.y;
            super.tickMovement(4.0F);
            this.toMove -= 4.0F;
            Point2D.Float d = GameMath.normalize(oldX - this.x, oldY - this.y);
            this.moveAngle = (float)Math.toDegrees(Math.atan2((double)d.y, (double)d.x)) - 90.0F;
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

    public void clientTick() {
        super.clientTick();
        this.getLevel().lightManager.refreshParticleLightFloat(this.x, this.y, new Color(0x36C2FF), 0.6F, 80);
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 3;
        int drawY = camera.getDrawY(y) - 3;
        DrawOptions body = texture.initDraw().sprite(0, 0, 6, 6).light(light.minLevelCopy(80)).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }

    public void dispose() {
        super.dispose();
        if (this.trail != null) {
            this.trail.removeOnFadeOut = true;
        }

    }
}
