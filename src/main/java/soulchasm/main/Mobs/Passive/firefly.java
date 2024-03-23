package soulchasm.main.Mobs.Passive;

import necesse.engine.network.NetworkClient;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.MobSpawnLocation;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.EmptyAINode;
import necesse.entity.mobs.friendly.critters.CritterMob;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.level.maps.Level;
import soulchasm.SoulChasm;

import java.awt.*;
import java.util.List;

public class firefly extends CritterMob {
    public static GameTexture texture;
    public static LootTable lootTable = new LootTable(new LootItemList(LootItem.between("fireflyitem", 2, 6)));
    public LootTable getLootTable() {
        return lootTable;
    }

    public firefly() {
        super(1);
        this.collision = new Rectangle(-32, -32, 64, 64);
        this.hitBox = new Rectangle(-32, -32, 64, 64);
        this.selectBox = new Rectangle(-32, -32, 64, 64);
        this.canDespawn = true;
        this.isStatic = true;
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new EmptyAINode<firefly>() {});
    }

    public boolean canDespawn() {
        return this.canDespawn && GameUtils.streamServerClients(this.getLevel()).noneMatch((c) -> this.getDistance(c.playerMob) < (float) (CRITTER_SPAWN_AREA.minSpawnDistance));
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
    public MobSpawnLocation checkSpawnLocation(MobSpawnLocation location) {
        return super.checkSpawnLocation(location).checkLocation((x, y) -> getLevel().getWorldEntity().isNight());
    }

    public void clientTick() {
        super.clientTick();
        if (GameRandom.globalRandom.getChance(0.08F)) {
            boolean mirror = GameRandom.globalRandom.nextBoolean();
            this.getLevel().entityManager.addParticle(this.x + GameRandom.globalRandom.getIntBetween(128, -128), this.y + GameRandom.globalRandom.getIntBetween(128, -128), Particle.GType.COSMETIC).sprite(SoulChasm.particleFireflySection).fadesAlpha(0.6F, 0.6F).size((options, lifeTime, timeAlive, lifePercent) -> {
            }).height(30.0F).movesConstant(GameRandom.globalRandom.getFloatBetween(0.5F, 2.5F) * GameRandom.globalRandom.getOneOf(1.0F, -1.0F), GameRandom.globalRandom.getFloatBetween(0.5F, 2.5F) * GameRandom.globalRandom.getOneOf(1.0F, -1.0F)).sizeFades(15, 20).givesLight(68, 100).givesLight(55).modify((options, lifeTime, timeAlive, lifePercent) -> {
                options.mirror(mirror, false);
            }).lifeTime(6000);
        }
        if (GameRandom.globalRandom.getChance(0.06F)) {
            boolean mirror = GameRandom.globalRandom.nextBoolean();
            this.getLevel().entityManager.addParticle(this.x + GameRandom.globalRandom.getIntBetween(32, -32), this.y + GameRandom.globalRandom.getIntBetween(32, -32), Particle.GType.COSMETIC).sprite(SoulChasm.particleFireflySection).fadesAlpha(0.6F, 0.6F).size((options, lifeTime, timeAlive, lifePercent) -> {
            }).height(30.0F).movesConstant(GameRandom.globalRandom.getFloatBetween(0.1F, 1.0F) * GameRandom.globalRandom.getOneOf(1.0F, -1.0F), GameRandom.globalRandom.getFloatBetween(0.1F, 1.0F) * GameRandom.globalRandom.getOneOf(1.0F, -1.0F)).sizeFades(15, 20).givesLight(68, 100).givesLight(65).modify((options, lifeTime, timeAlive, lifePercent) -> {
                options.mirror(mirror, false);
            }).lifeTime(6000);
        }
    }

    public void serverTick() {
        super.serverTick();
        if(this.getLevel() != null){
            if(this.getLevel().getServer() != null){
                if(!this.getLevel().getServer().world.worldEntity.isNight()){
                    this.dispose();
                    this.remove();
                }
            }
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
    }
}
