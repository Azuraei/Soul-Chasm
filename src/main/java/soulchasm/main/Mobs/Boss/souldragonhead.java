package soulchasm.main.Mobs.Boss;

import necesse.engine.Screen;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.MusicRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.ComputedObjectValue;
import necesse.engine.util.GameLinkedList;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.gameAreaSearch.GameAreaStream;
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
import soulchasm.main.Projectiles.BossProjectiles.souldragonfragmentprojectile;
import soulchasm.main.Projectiles.BossProjectiles.soulflamethrower;
import soulchasm.main.Projectiles.soulhomingprojectile;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class souldragonhead extends BossWormMobHead<souldragonbody, souldragonhead> {
    public static RotationLootItem uniqueDrops = RotationLootItem.privateLootRotation(new LootItem("meleesoulsealtrinket"), new LootItem("rangesoulsealtrinket"), new LootItem("magicsoulsealtrinket"), new LootItem("summonsoulsealtrinket"));
    public static LootTable lootTable;
    public static LootTable privateLootTable;
    public static float lengthPerBodyPart = 40.0F;
    public static float waveLength = 800.0F;
    public static final int totalBodyParts = 12;
    public static GameTexture texture;
    public static GameDamage souldragonFragmentDamage;
    public static GameDamage souldragonHomingProjectileDamage;
    public static GameDamage souldragonFlamethrowerDamage;
    public static GameDamage souldragonCollisionDamage;
    private static float dragonHeadAngle;
    private static float dragonHeadX;
    private static float dragonHeadY;
    protected MobHealthScaling scaling = new MobHealthScaling(this);
    public EmptyMobAbility roarAbility;
    public EmptyMobAbility roarAbility2;
    protected float temporarySpeed;
    protected FloatMobAbility temporarySpeedAbility;

    public souldragonhead() {
        super(50000, waveLength, 100.0F, totalBodyParts, 10.0F, -40.0F);
        this.moveAccuracy = 100;
        this.movementUpdateCooldown = 100;
        this.movePosTolerance = 400.0F;
        this.setSpeed(150F);
        this.setArmor(45);
        this.accelerationMod = 1.0F;
        this.decelerationMod = 1.0F;
        this.isHostile = true;
        this.collision = new Rectangle(-18, -15, 36, 30);
        this.hitBox = new Rectangle(-25, -20, 50, 40);
        this.selectBox = new Rectangle(-32, -80, 64, 84);
        this.roarAbility = this.registerAbility(new EmptyMobAbility() {
            protected void run() {
                if (souldragonhead.this.getLevel().isClient()) {
                    Screen.playSound(GameResources.roar, SoundEffect.globalEffect().volume(0.7F).pitch(0.7F));
                }
            }
        });
        this.roarAbility2 = this.registerAbility(new EmptyMobAbility() {
            protected void run() {
                if (souldragonhead.this.getLevel().isClient()) {
                    Screen.playSound(GameResources.magicroar, SoundEffect.globalEffect().volume(0.3F).pitch(0.5F));
                }
            }
        });
        this.temporarySpeedAbility = this.registerAbility(new FloatMobAbility() {
            protected void run(float value) {
                souldragonhead.this.temporarySpeed = value;
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

    public boolean isHealthBarVisible() {
        return false;
    }

    protected float getDistToBodyPart(souldragonbody bodyPart, int index, float lastDistance) {
        return lengthPerBodyPart;
    }

    protected void playMoveSound() {
        Screen.playSound(GameResources.shake, SoundEffect.effect(this).falloffDistance(1000));
    }

    protected souldragonbody createNewBodyPart(int index) {
        souldragonbody bodyPart = new souldragonbody();
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
        return souldragonCollisionDamage.modDamage(1.5F);
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI(this, new SoulDragonHeadAI(), new FlyingAIMover());
        if (this.getLevel().isClient()) {
            Screen.playSound(GameResources.roar, SoundEffect.globalEffect().pitch(0.9F));
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
        Screen.setMusic(MusicRegistry.Millenium, Screen.MusicPriority.EVENT, 1.5F);
        Screen.registerMobHealthStatusBar(this);
        BossNearbyBuff.applyAround(this);
        this.setSpeed(this.temporarySpeed >0 ? this.temporarySpeed : 150);
    }
    public void serverTick() {
        super.serverTick();
        this.scaling.serverTick();
        BossNearbyBuff.applyAround(this);
        this.setSpeed(this.temporarySpeed >0 ? this.temporarySpeed : 150);
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

    public void drawOnMap(TickManager tickManager, int x, int y) {
        super.drawOnMap(tickManager, x, y);
        int drawX = x - 16;
        int drawY = y - 16;
        float headAngle = GameMath.fixAngle(GameMath.getAngle(new Point2D.Float(this.dx, this.dy)));
        texture.initDraw().sprite(0, 6, 64).rotate(headAngle + 90.0F, 16, 16).size(32, 32).draw(drawX, drawY);
    }

    public Rectangle drawOnMapBox() {
        return new Rectangle(-12, -12, 24, 24);
    }

    public GameTooltips getMapTooltips() {
        return !this.isVisible() ? null : new StringTooltips(this.getDisplayName() + " " + this.getHealth() + "/" + this.getMaxHealth());
    }

    public Stream<ModifierValue<?>> getDefaultModifiers() {
        return Stream.of((new ModifierValue(BuffModifiers.SLOW, 0.0F)).max(0.0F));
    }

    protected void onDeath(Attacker attacker, HashSet<Attacker> attackers) {
        super.onDeath(attacker, attackers);
        Screen.playSound(GameResources.roar, SoundEffect.globalEffect().volume(0.7F).pitch(0.7F));
        attackers.stream().map(Attacker::getAttackOwner).filter((m) -> m != null && m.isPlayer).distinct().forEach((m) -> {
            this.getLevel().getServer().network.sendPacket(new PacketChatMessage(new LocalMessage("misc", "bossdefeat", "name", this.getLocalization())), ((PlayerMob)m).getServerClient());
        });
    }

    static {
        souldragonCollisionDamage = new GameDamage(80.0F);
        souldragonFragmentDamage = new GameDamage(65.0F);
        souldragonHomingProjectileDamage = new GameDamage(60.0F);
        souldragonFlamethrowerDamage = new GameDamage(50.0F);
        privateLootTable = new LootTable(uniqueDrops);
    }

    public static class SoulDragonHeadAI<T extends souldragonhead> extends SelectorAINode<T> {
        public SoulDragonHeadAI() {
            SequenceAINode<T> chaserSequence = new SequenceAINode();
            this.addChild(chaserSequence);
            chaserSequence.addChild(new RemoveOnNoTargetNode(100));
            final TargetFinderAINode targetFinder;
            chaserSequence.addChild(targetFinder = new TargetFinderAINode<T>(3200) {
                public GameAreaStream<? extends Mob> streamPossibleTargets(T mob, Point base, TargetFinderDistance<T> distance) {
                    return TargetFinderAINode.streamPlayers(mob, base, distance);
                }
            });
            targetFinder.moveToAttacker = false;
            ChargingCirclingChaserAINode chaserAI;
            chaserSequence.addChild(chaserAI = new ChargingCirclingChaserAINode(2000, 40));
            chaserSequence.addChild(new souldragonhead.IdleTime(2000));
            chaserSequence.addChild(new souldragonhead.FragmentAttackRotation<>());
            chaserSequence.addChild(new souldragonhead.EnragedState(6000, chaserAI));
            chaserSequence.addChild(new souldragonhead.IdleTime(500));
            chaserSequence.addChild(new souldragonhead.FlamethrowerAttackRotation(chaserAI, 6000));
            chaserSequence.addChild(new souldragonhead.IdleTime(3000));
            chaserSequence.addChild(new souldragonhead.FragmentAttackRotation<>());
            chaserSequence.addChild(new souldragonhead.EnragedState(6000, chaserAI));
            chaserSequence.addChild(new souldragonhead.HomingAttackRotation());
            this.addChild(new WandererAINode(0));
        }
    }

    public static class EnragedState<T extends souldragonhead> extends RunningAINode<T> {
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
            mob.temporarySpeedAbility.runAndSend(300);
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

        public IdleTime(int msToIdle) {
            this.msToIdle = msToIdle;
        }

        public void start(T mob, Blackboard<T> blackboard) {
            this.timer = 0;
        }

        public AINodeResult tickRunning(T mob, Blackboard<T> blackboard) {
            this.timer += 50;
            return this.timer <= this.msToIdle ? AINodeResult.RUNNING : AINodeResult.SUCCESS;
        }

        public void end(T mob, Blackboard<T> blackboard) {
        }
    }

    public static class FlamethrowerAttackRotation<T extends souldragonhead> extends RunningAINode<T> {
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
                soulflamethrower projectile = new soulflamethrower(mob.getLevel(), dragonHeadX, dragonHeadY, (float) targetX, (float) targetY, (int) (450 + mob.getCurrentSpeed()), souldragonhead.souldragonFlamethrowerDamage, mob);
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

    public static class HomingAttackRotation<T extends souldragonhead> extends RunningAINode<T> {
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
                soulhomingprojectile projectile = new soulhomingprojectile(mob.getLevel(), mob, mob.x, mob.y, (float) targetX, (float) targetY, 60, 800,  souldragonhead.souldragonHomingProjectileDamage, 40);
                projectile.setAngle(projectile.getAngle() + angle * i);
                projectile.turnSpeed = 0.05F;
                mob.getLevel().entityManager.projectiles.add(projectile);
            }
            return AINodeResult.SUCCESS;
        }
        public void end(T mob, Blackboard<T> blackboard) {
        }
    }

    public static class FragmentAttackRotation<T extends souldragonhead> extends RunningAINode<T> {
        public FragmentAttackRotation() {}
        public void start(T mob, Blackboard<T> blackboard) {}
        public AINodeResult tickRunning(T mob, Blackboard<T> blackboard) {
            souldragonfragmentprojectile projectile = new souldragonfragmentprojectile(mob.getLevel(), mob.x, mob.y, mob.x, mob.y, 0.1F, 1, souldragonhead.souldragonFragmentDamage, 50, mob);
            mob.getLevel().entityManager.projectiles.add(projectile);
            return AINodeResult.SUCCESS;
        }
        public void end(T mob, Blackboard<T> blackboard) {
        }
    }
}
