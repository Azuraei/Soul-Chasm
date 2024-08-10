package soulchasm.main.Mobs.Agressive;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.EmptyAINode;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionPlayerChaserAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.main.Misc.Events.meleeghostspawnevent;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class meleestatue extends HostileMob {
    public static LootTable lootTable = new LootTable();
    public static GameTexture texture;
    public static GameTexture glowtexture;
    private boolean showGlowEyes;

    public meleestatue() {
        super(500);
        this.setSpeed(110.0F);
        this.setFriction(6.0F);
        this.setArmor(100);
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-14, -12, 28, 24);
        this.selectBox = new Rectangle(-14, -55, 28, 48);
        this.isHostile = false;
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new EmptyAINode<meleestatue>() {});
        this.isHostile = false;
        showGlowEyes = false;
    }

    protected void changeAI() {
        CollisionPlayerChaserAI<meleestatue> wickedsoulAI = new CollisionPlayerChaserAI(800, new GameDamage(80.0F), 25);
        this.ai = new BehaviourTreeAI<>(this, wickedsoulAI);
        this.isHostile = true;
    }

    public boolean isLavaImmune() {
        return true;
    }
    public boolean canBePushed(Mob other) {
        return false;
    }
    public LootTable getLootTable() {
        return lootTable;
    }

    public void setupMovementPacket(PacketWriter writer) {
        super.setupMovementPacket(writer);
        writer.putNextBoolean(this.showGlowEyes);
    }

    public void applyMovementPacket(PacketReader reader, boolean isDirect) {
        super.applyMovementPacket(reader, isDirect);
        this.showGlowEyes = reader.getNextBoolean();
    }

    public MobWasHitEvent isHit(MobWasHitEvent event, Attacker attacker) {
        if(!event.wasPrevented && attacker != null){
            changeAI();
            SoundManager.playSound(GameResources.fadedeath3, SoundEffect.effect(this).pitch(0.5F).volume(0.6F));
        }
        return super.isHit(event, attacker);
    }

    private void spawnSoul(){
        meleeghostspawnevent event = new meleeghostspawnevent((int) this.x, (int)this.y, 2000, this);
        this.getLevel().entityManager.addLevelEvent(event);
    }

    public void serverTick() {
        super.serverTick();
        showGlowEyes = ai.blackboard.mover.hasMobTarget();
        if(!this.isInCombat() && !ai.blackboard.mover.hasMobTarget()){
            this.ai = new BehaviourTreeAI<>(this, new EmptyAINode<meleestatue>() {});
        }
    }

    protected void onDeath(Attacker attacker, HashSet<Attacker> attackers) {
        spawnSoul();
        super.onDeath(attacker, attackers);
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(this.getTileX(), this.getTileY());
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 55;
        Point sprite = this.getAnimSprite(x, y, this.getDir());
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(this.getTileX(), this.getTileY()).getMobSinkingAmount(this);
        DrawOptions drawOptions = texture.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY);
        DrawOptions glow = glowtexture.initDraw().sprite(sprite.x, sprite.y, 64).light(light.minLevelCopy(100)).pos(drawX, drawY).alpha(this.showGlowEyes?1:0);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
                glow.draw();
            }
        });
        this.addShadowDrawables(tileList, x, y, light, camera);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for (int i = 0; i < 16; i++) {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), texture, GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 20f, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public int getRockSpeed() {
        return 20;
    }
}
