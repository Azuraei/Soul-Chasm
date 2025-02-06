package soulchasm.main.Mobs.Boss;

import necesse.engine.eventStatusBars.EventStatusBarManager;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.MusicRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.ComputedObjectValue;
import necesse.engine.util.GameLinkedList;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.gameAreaSearch.GameAreaStream;
import necesse.entity.levelEvent.WaitForSecondsEvent;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ability.EmptyMobAbility;
import necesse.entity.mobs.ability.FloatMobAbility;
import necesse.entity.mobs.ai.behaviourTree.AINodeResult;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.ai.behaviourTree.composites.SelectorAINode;
import necesse.entity.mobs.ai.behaviourTree.composites.SequenceAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.*;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.ai.behaviourTree.util.TargetFinderDistance;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.BossNearbyBuff;
import necesse.entity.mobs.hostile.bosses.BossWormMobHead;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.RotationLootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.main.Misc.Events.GroundEruptionEvent.DragonGroundEruptionEvent;
import soulchasm.main.Misc.Events.SpinningProjectileSpawnerEvent.SpinSpawnEvent;
import soulchasm.main.Misc.Events.SpinningProjectileSpawnerEvent.SpinSpawnVisualEvent;
import soulchasm.main.Projectiles.BossProjectiles.SoulFlamethrowerProjectile;
import soulchasm.main.Projectiles.SoulHomingProjectile;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class SoulDragon extends BossWormMobHead<SoulDragonBody, SoulDragon> {
    public static RotationLootItem uniqueDrops = RotationLootItem.privateLootRotation(new LootItem("meleesoulsealtrinket"), new LootItem("rangesoulsealtrinket"), new LootItem("magicsoulsealtrinket"), new LootItem("summonsoulsealtrinket"));
    public static LootTable lootTable = new LootTable();
    public static LootTable privateLootTable;
    public static float lengthPerBodyPart = 40.0F;
    public static float waveLength = 800.0F;
    public static final int totalBodyParts = 12;
    public static GameTexture texture;
    public static GameDamage soulDragonFragmentDamage;
    public static GameDamage soulDragonHomingProjectileDamage;
    public static GameDamage soulDragonFlamethrowerDamage;
    public static GameDamage soulDragonCollisionDamage;
    public static GameDamage soulDragonEruptionDamage;
    private static float dragonHeadAngle;
    private static float dragonHeadX;
    private static float dragonHeadY;
    public static MaxHealthGetter MAX_HEALTH;
    protected MobHealthScaling scaling = new MobHealthScaling(this);
    public EmptyMobAbility roarAbility;
    public EmptyMobAbility roarAbility2;
    protected float temporarySpeed;
    protected FloatMobAbility temporarySpeedAbility;

    public SoulDragon() {
        super(100, waveLength, 100.0F, totalBodyParts, 10.0F, -40.0F);
        this.difficultyChanges.setMaxHealth(MAX_HEALTH);
        this.moveAccuracy = 100;
        this.movementUpdateCooldown = 100;
        this.movePosTolerance = 400.0F;
        this.setSpeed(115F);
        this.setArmor(45);
        this.accelerationMod = 1.0F;
        this.decelerationMod = 1.0F;
        this.isHostile = true;
        this.collision = new Rectangle(-18, -15, 36, 30);
        this.hitBox = new Rectangle(-25, -20, 50, 40);
        this.selectBox = new Rectangle(-32, -80, 64, 84);
        this.roarAbility = this.registerAbility(new EmptyMobAbility() {
            protected void run() {
                if (SoulDragon.this.getLevel().isClient()) {
                    SoundManager.playSound(GameResources.roar, SoundEffect.globalEffect().volume(0.7F).pitch(0.7F));
                }
            }
        });
        this.roarAbility2 = this.registerAbility(new EmptyMobAbility() {
            protected void run() {
                if (SoulDragon.this.getLevel().isClient()) {
                    SoundManager.playSound(GameResources.magicroar, SoundEffect.globalEffect().volume(0.3F).pitch(0.5F));
                }
            }
        });
        this.temporarySpeedAbility = this.registerAbility(new FloatMobAbility() {
            protected void run(float value) {
                SoulDragon.this.temporarySpeed = value;
            }
        });
    }

    public void setupMovementPacket(PacketWriter writer) {
        super.setupMovementPacket(writer);
        writer.putNextFloat(this.temporarySpeed);
    }

    public void applyMovementPacket(PacketReader reader, boolean isDirect) {
        super.applyMovementPacket(reader, isDirect);
        this.temporarySpeed = reader.getNextFloat();
    }

    public void setupHealthPacket(PacketWriter writer, boolean isFull) {
        this.scaling.setupHealthPacket(writer, isFull);
        super.setupHealthPacket(writer, isFull);
    }

    public void applyHealthPacket(PacketReader reader, boolean isFull) {
        this.scaling.applyHealthPacket(reader, isFull);
        super.applyHealthPacket(reader, isFull);
    }

    public void setMaxHealth(int maxHealth) {
        super.setMaxHealth(maxHealth);
        if (this.scaling != null) {
            this.scaling.updatedMaxHealth();
        }
    }

    public boolean isHealthBarVisible() {
        return false;
    }

    protected float getDistToBodyPart(SoulDragonBody bodyPart, int index, float lastDistance) {
        return lengthPerBodyPart;
    }

    protected void playMoveSound() {
        SoundManager.playSound(GameResources.shake, SoundEffect.effect(this).falloffDistance(1000));
    }

    protected SoulDragonBody createNewBodyPart(int index) {
        SoulDragonBody bodyPart = new SoulDragonBody();
        int tailParts = 3;
        if (index == 1) {
            bodyPart.spriteY = 1;
        } else if (index == totalBodyParts - tailParts - 1) {
            bodyPart.spriteY = 1;
        } else if (index >= totalBodyParts - tailParts) {
            int tailPart = Math.abs(totalBodyParts - index - tailParts);
            bodyPart.spriteY = 3 + tailPart;
        } else {
            bodyPart.spriteY = 2;
        }
        bodyPart.spawnsParticles = true;
        return bodyPart;
    }

    public GameDamage getCollisionDamage(Mob target) {
        return soulDragonCollisionDamage.modDamage(1.5F);
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI(this, new SoulDragonAI(), new FlyingAIMover());
        if (this.getLevel().isClient()) {
            SoundManager.playSound(GameResources.roar, SoundEffect.globalEffect().pitch(0.9F));
        }
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public LootTable getPrivateLootTable() {
        return privateLootTable;
    }

    public int getMaxHealth() {
        return super.getMaxHealth() + (int)((float)(this.scaling == null ? 0 : this.scaling.getHealthIncrease()) * this.getMaxHealthModifier());
    }

    public void clientTick() {
        super.clientTick();
        SoundManager.setMusic(MusicRegistry.Millenium, SoundManager.MusicPriority.EVENT, 1.5F);
        EventStatusBarManager.registerMobHealthStatusBar(this);
        BossNearbyBuff.applyAround(this);
        this.setSpeed(this.temporarySpeed > 0 ? this.temporarySpeed : 115F);
    }
    public void serverTick() {
        super.serverTick();
        this.scaling.serverTick();
        BossNearbyBuff.applyAround(this);
        this.setSpeed(this.temporarySpeed > 0 ? this.temporarySpeed : 115F);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, 2,  GameRandom.globalRandom.nextInt(6), 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
    }

    public int getFlyingHeight() {
        return 25;
    }

    @Override
    public boolean isBoss() {
        return true;
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        if (this.isVisible()) {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - 32;
            int drawY = camera.getDrawY(this.y);
            float headAngle = GameMath.fixAngle(GameMath.getAngle(new Point2D.Float(this.dx, this.dy)));
            final MobDrawable headDrawable = WormMobHead.getAngledDrawable(new GameSprite(texture, 0, 0, 64), null, light, (int)this.height, headAngle, drawX, drawY, 96);
            dragonHeadAngle = headAngle;
            dragonHeadX = x;
            dragonHeadY = y;
            new ComputedObjectValue(null, () -> 0.0);
            ComputedObjectValue<GameLinkedList<WormMoveLine>.Element, Double> shoulderLine = WormMobHead.moveDistance(this.moveLines.getFirstElement(), 35.0);
            final MobDrawable shoulderDrawable;
            if (shoulderLine.object != null) {
                Point2D.Double shoulderPos = WormMobHead.linePos(shoulderLine);
                GameLight shoulderLight = level.getLightLevel((int)(shoulderPos.x / 32.0), (int)(shoulderPos.y / 32.0));
                int shoulderDrawX = camera.getDrawX((float)shoulderPos.x) - 32;
                int shoulderDrawY = camera.getDrawY((float)shoulderPos.y);
                float shoulderHeight = this.getWaveHeight(shoulderLine.object.object.movedDist + ((Double)shoulderLine.get()).floatValue());
                float shoulderAngle = GameMath.fixAngle((float)GameMath.getAngle(new Point2D.Double((double)this.x - shoulderPos.x, (double)(this.y - this.height) - (shoulderPos.y - (double)shoulderHeight))));
                shoulderDrawable = WormMobHead.getAngledDrawable(new GameSprite(texture, 0, 2, 64), (GameTexture)null, shoulderLight, (int)shoulderHeight, shoulderAngle, shoulderDrawX, shoulderDrawY, 96);
            } else {
                shoulderDrawable = null;
            }
            topList.add(new MobDrawable() {
                public void draw(TickManager tickManager) {
                    if (shoulderDrawable != null) {
                        shoulderDrawable.draw(tickManager);
                    }
                    headDrawable.draw(tickManager);
                }
            });
            this.addShadowDrawables(tileList, x, y, light, camera);
        }
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera) {
        GameTexture shadowTexture = MobRegistry.Textures.swampGuardian_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        drawY += this.getBobbing(x, y);
        return shadowTexture.initDraw().sprite(2, 0, res).light(light).pos(drawX, drawY);
    }

    public boolean shouldDrawOnMap() {
        return this.isVisible();
    }

    public void drawOnMap(TickManager tickManager, Client client, int x, int y, double tileScale, Rectangle drawBounds, boolean isMinimap) {
        super.drawOnMap(tickManager, client, x, y, tileScale, drawBounds, isMinimap);
        int drawX = x - 32;
        int drawY = y - 32;
        float headAngle = GameMath.fixAngle(GameMath.getAngle(new Point2D.Float(this.dx, this.dy)));
        texture.initDraw().sprite(0, 0, 64).rotate(headAngle + 90.0F, 32, 32).size(48, 48).draw(drawX, drawY);
    }

    public Rectangle drawOnMapBox(double tileScale, boolean isMinimap) {
        return new Rectangle(-15, -15, 30, 30);
    }

    public GameTooltips getMapTooltips() {
        return !this.isVisible() ? null : new StringTooltips(this.getDisplayName() + " " + this.getHealth() + "/" + this.getMaxHealth());
    }

    public Stream<ModifierValue<?>> getDefaultModifiers() {
        return Stream.of((new ModifierValue(BuffModifiers.SLOW, 0.0F)).max(0.2F));
    }

    protected void onDeath(Attacker attacker, HashSet<Attacker> attackers) {
        super.onDeath(attacker, attackers);
        SoundManager.playSound(GameResources.roar, SoundEffect.globalEffect().volume(0.7F).pitch(0.7F));
        attackers.stream().map(Attacker::getAttackOwner).filter((m) -> m != null && m.isPlayer).distinct().forEach((m) -> {
            this.getLevel().getServer().network.sendPacket(new PacketChatMessage(new LocalMessage("misc", "bossdefeat", "name", this.getLocalization())), ((PlayerMob)m).getServerClient());
        });
    }

    static {
        MAX_HEALTH = new MaxHealthGetter(40000, 45000, 50000, 55000, 60000);
        soulDragonCollisionDamage = new GameDamage(75.0F);
        soulDragonFragmentDamage = new GameDamage(50.0F);
        soulDragonHomingProjectileDamage = new GameDamage(45.0F);
        soulDragonFlamethrowerDamage = new GameDamage(45.0F);
        soulDragonEruptionDamage = new GameDamage(65.0F);
        privateLootTable = new LootTable(uniqueDrops);
    }

    public static class SoulDragonAI<T extends SoulDragon> extends SelectorAINode<T> {
        public SoulDragonAI() {
            SequenceAINode<T> chaserSequence = new SequenceAINode<>();
            this.addChild(chaserSequence);
            chaserSequence.addChild(new RemoveOnNoTargetNode<>(100));
            final TargetFinderAINode<T> targetFinder;
            chaserSequence.addChild(targetFinder = new TargetFinderAINode<T>(3200) {
                public GameAreaStream<? extends Mob> streamPossibleTargets(T mob, Point base, TargetFinderDistance<T> distance) {
                    return TargetFinderAINode.streamPlayers(mob, base, distance);
                }
            });
            targetFinder.moveToAttacker = false;
            ChargingCirclingChaserAINode chaserAI;
            chaserSequence.addChild(chaserAI = new ChargingCirclingChaserAINode(300, 40));
            chaserSequence.addChild(new SoulDragon.IdleTime(4000, chaserAI));
            chaserSequence.addChild(new SoulDragon.FragmentAttackRotation<>());
            chaserSequence.addChild(new SoulDragon.EruptionAttackRotation<>(chaserAI,4));
            chaserSequence.addChild(new SoulDragon.IdleTime(2000, chaserAI));
            chaserSequence.addChild(new SoulDragon.EnragedState(3000, chaserAI));
            chaserSequence.addChild(new SoulDragon.IdleTime(1000, chaserAI));
            chaserSequence.addChild(new SoulDragon.FlamethrowerAttackRotation(chaserAI, 6000));
            chaserSequence.addChild(new SoulDragon.IdleTime(1000, chaserAI));
            chaserSequence.addChild(new SoulDragon.EruptionAttackRotation<>(chaserAI,6));
            chaserSequence.addChild(new SoulDragon.FragmentAttackRotation<>());
            chaserSequence.addChild(new SoulDragon.IdleTime(3000, chaserAI));
            chaserSequence.addChild(new SoulDragon.EnragedState(4000, chaserAI));
            chaserSequence.addChild(new SoulDragon.HomingAttackRotation());

            this.addChild(new WandererAINode(0));
        }
    }

    public static class EnragedState<T extends SoulDragon> extends RunningAINode<T> {
        public int msToIdle;
        private int timer;
        private ChargingCirclingChaserAINode<T> chaserAI;

        public EnragedState(int msToIdle, ChargingCirclingChaserAINode<T> chaserAI) {
            this.msToIdle = msToIdle;
            this.chaserAI = chaserAI;
        }

        public void start(T mob, Blackboard<T> blackboard) {
            this.timer = 0;
            mob.roarAbility.runAndSend();
            mob.temporarySpeedAbility.runAndSend(250);
            Mob target = blackboard.getObject(Mob.class, "currentTarget");
            if (target != null) {
                chaserAI.startCharge(mob, blackboard, target);
            }
        }
        public AINodeResult tickRunning(T mob, Blackboard<T> blackboard) {
            this.timer += 50;
            return this.timer <= this.msToIdle ? AINodeResult.RUNNING : AINodeResult.SUCCESS;
        }
        public void end(T mob, Blackboard<T> blackboard) {
            mob.temporarySpeedAbility.runAndSend(0);
        }
    }

    public static class IdleTime<T extends Mob> extends RunningAINode<T> {
        public int msToIdle;
        private int timer;
        private ChargingCirclingChaserAINode<T> chaserAI;

        public IdleTime(int msToIdle, ChargingCirclingChaserAINode<T> chaserAI) {
            this.msToIdle = msToIdle;
            this.chaserAI = chaserAI;
        }

        public void start(T mob, Blackboard<T> blackboard) {
            this.timer = 0;
            Mob target = blackboard.getObject(Mob.class, "currentTarget");
            if (target != null) {
                chaserAI.startCircling(mob, blackboard, target, msToIdle/50);
            }
        }

        public AINodeResult tickRunning(T mob, Blackboard<T> blackboard) {
            this.timer += 50;
            return this.timer <= this.msToIdle ? AINodeResult.RUNNING : AINodeResult.SUCCESS;
        }

        public void end(T mob, Blackboard<T> blackboard) {
        }
    }

    public static class FlamethrowerAttackRotation<T extends SoulDragon> extends RunningAINode<T> {
        private ChargingCirclingChaserAINode<T> chaserAI;
        public int msToIdle;
        private int timer;
        public FlamethrowerAttackRotation(ChargingCirclingChaserAINode<T> chaserAI, int msToIdle) {
            this.chaserAI = chaserAI;
            this.msToIdle = msToIdle;
        }
        public void start(T mob, Blackboard<T> blackboard) {
            mob.temporarySpeedAbility.runAndSend(40);
            mob.roarAbility.runAndSend();
            this.timer = 0;
            Mob target = blackboard.getObject(Mob.class, "currentTarget");
            if (target != null) {
                chaserAI.startCharge(mob, blackboard, target);
            }
        }

        public void attack(T mob){
            float distance = 400;
            float selfAngle = dragonHeadAngle;
            double targetX = mob().x + distance*Math.cos(Math.toRadians(selfAngle));
            double targetY = mob().y + distance*Math.sin(Math.toRadians(selfAngle));
            for(int i = -1; i < 1; i++){
                SoulFlamethrowerProjectile projectile = new SoulFlamethrowerProjectile(mob.getLevel(), dragonHeadX, dragonHeadY, (float) targetX, (float) targetY, (int) (450 + mob.getCurrentSpeed()), SoulDragon.soulDragonFlamethrowerDamage, mob);
                projectile.setAngle(projectile.getAngle() + 10 * i);
                mob.getLevel().entityManager.projectiles.add(projectile);
            }
        }

        public AINodeResult tickRunning(T mob, Blackboard<T> blackboard) {
            this.timer += 50;
            if(this.timer % 200 == 0){
                attack(mob);
                mob.roarAbility2.runAndSend();
            }
            return this.timer <= this.msToIdle ? AINodeResult.RUNNING : AINodeResult.SUCCESS;
        }
        public void end(T mob, Blackboard<T> blackboard) {
            mob.temporarySpeedAbility.runAndSend(0);
        }
    }

    public static class HomingAttackRotation<T extends SoulDragon> extends RunningAINode<T> {
        public HomingAttackRotation() {
        }
        public void start(T mob, Blackboard<T> blackboard) {}
        public AINodeResult tickRunning(T mob, Blackboard<T> blackboard) {
            float distance = 800;
            float selfAngle = dragonHeadAngle;
            double targetX = mob().x + distance*Math.cos(Math.toRadians(selfAngle));
            double targetY = mob().y + distance*Math.sin(Math.toRadians(selfAngle));
            float angle = 40;
            for(int i = -4; i<4; i++){
                SoulHomingProjectile projectile = new SoulHomingProjectile(mob.getLevel(), mob, mob.x, mob.y, (float) targetX, (float) targetY, 40, 600,  SoulDragon.soulDragonHomingProjectileDamage, 20);
                projectile.setAngle(projectile.getAngle() + angle * i);
                projectile.turnSpeed = 0.025F;
                mob.getLevel().entityManager.projectiles.add(projectile);
            }
            return AINodeResult.SUCCESS;
        }
        public void end(T mob, Blackboard<T> blackboard) {
        }
    }

    public static class EruptionAttackRotation<T extends SoulDragon> extends RunningAINode<T> {
        public int numberOfAttacks;
        public float delay = 1.0F;
        private int timer;
        private ChargingCirclingChaserAINode<T> chaserAI;
        public EruptionAttackRotation(ChargingCirclingChaserAINode<T> chaserAI, int numberOfAttacks) {
            this.numberOfAttacks = numberOfAttacks;
            this.chaserAI = chaserAI;
        }
        public void start(T mob, Blackboard<T> blackboard) {
            for(int i = 0; i<this.numberOfAttacks; i++){
                Level level = mob.getLevel();
                int x;
                int y;
                Mob target = blackboard.getObject(Mob.class, "currentTarget");
                if (target != null) {
                    chaserAI.startCircling(mob, blackboard, target, 300);
                    x = (int) target.x;
                    y = (int) target.y;
                } else {
                    x = (int) mob.x;
                    y = (int) mob.y;
                }
                level.entityManager.addLevelEventHidden(new WaitForSecondsEvent(delay * i) {
                    public void onWaitOver() {
                        int range = 300;
                        int pos_x = x + GameRandom.globalRandom.getIntBetween(-range, range);
                        int pos_y = y + GameRandom.globalRandom.getIntBetween(-range, range);
                        DragonGroundEruptionEvent event = new DragonGroundEruptionEvent(mob, pos_x, pos_y, GameRandom.globalRandom.nextSeeded(), soulDragonEruptionDamage);
                        level.entityManager.addLevelEvent(event);
                    }
                });
            }
        }
        public AINodeResult tickRunning(T mob, Blackboard<T> blackboard) {
            this.timer += 50;
            return this.timer <= 1000 * this.delay * this.numberOfAttacks/50 ? AINodeResult.RUNNING : AINodeResult.SUCCESS;
        }
        public void end(T mob, Blackboard<T> blackboard) {
        }
    }

    public static class FragmentAttackRotation<T extends SoulDragon> extends RunningAINode<T> {
        public FragmentAttackRotation() {}
        public void start(T mob, Blackboard<T> blackboard) {}
        public AINodeResult tickRunning(T mob, Blackboard<T> blackboard) {
            Level level = mob.getLevel();
            int duration = 1000;
            int x = (int) mob.x;
            int y = (int) mob.y;
            SpinSpawnVisualEvent event = new SpinSpawnVisualEvent(x, y, duration * 3.5F);
            level.entityManager.addLevelEvent(event);
            SpinSpawnEvent event2 = new SpinSpawnEvent(mob, x, y, GameRandom.globalRandom.nextSeeded(), soulDragonFragmentDamage, duration);
            level.entityManager.addLevelEvent(event2);
            return AINodeResult.SUCCESS;
        }
        public void end(T mob, Blackboard<T> blackboard) {
        }
    }
}
