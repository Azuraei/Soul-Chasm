package soulchasm.main.Mobs.Agressive;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionPlayerChaserWandererAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ParticleOption;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.SoulChasm;

import java.awt.*;
import java.util.List;

public class MeleeGhost extends HostileMob {
    public static GameTexture texture;
    private float particleBuffer;

    public MeleeGhost() {
        super(500);
        this.setSpeed(120.0F);
        this.setFriction(0.8F);
        this.setKnockbackModifier(0.2F);
        this.setArmor(0);
        this.moveAccuracy = 10;
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-14, -12, 28, 24);
        this.selectBox = new Rectangle(-14, -55, 28, 48);
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI(this, new CollisionPlayerChaserWandererAI(null, 500, new GameDamage(65.0F), 100, 40000), new FlyingAIMover());
    }

    public boolean isLavaImmune() {
        return true;
    }
    public boolean canBePushed(Mob other) {
        return false;
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int bobbing = (int)(GameUtils.getBobbing(level.getWorldEntity().getTime(), 1000) * 5.0F);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 48 + bobbing;
        int anim = Math.abs(GameUtils.getAnim(level.getWorldEntity().getTime(), 4, 1000) - 3);
        int minLight = 100;
        DrawOptions body = texture.initDraw().sprite(0, anim, 64).mirror(this.moveX < 0.0F, false).alpha(0.7F).light(light).size(55).pos(drawX, drawY);
        DrawOptions eyes = texture.initDraw().sprite(1, anim, 64).mirror(this.moveX < 0.0F, false).alpha(0.7F).light(light.minLevelCopy((float)minLight)).size(55).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
            eyes.draw();
        });
    }

    public boolean dropsLoot() {
        return false;
    }

    public void tickMovement(float delta) {
        super.tickMovement(delta);
        Mob owner = this;
        if (owner.isClient() && (owner.dx != 0.0F || owner.dy != 0.0F)) {
            float speed = owner.getCurrentSpeed() * delta / 250.0F;
            float particleBuffer = this.particleBuffer + speed;
            if (particleBuffer >= 25.0F) {
                particleBuffer -= 25.0F;
                ParticleOption.DrawModifier mod = (wrapper, i, i1, v) -> wrapper.size(55);
                owner.getLevel().entityManager.addParticle(owner.x, owner.y - 16, Particle.GType.IMPORTANT_COSMETIC).sprite(SoulChasm.particleMeleeGhostParticleSection.sprite(0, this.moveX < 0.0F ? 1 : 0, 64)).size(mod).fadesAlpha(0.1F, 1.0F).fadesAlphaTime(100, 600).minDrawLight(100).dontRotate().lifeTime(700);
            }
            this.particleBuffer = particleBuffer;
        }
    }

    protected void playDeathSound() {
        SoundManager.playSound(GameResources.fadedeath3, SoundEffect.effect(this).volume(0.2F).pitch(0.9F));
    }

    protected void playHitSound() {
        SoundManager.playSound(GameResources.swoosh, SoundEffect.effect(this).pitch(0.7F).volume(0.2F));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
    }
}
