package soulchasm.main.Mobs.Agressive;

import necesse.engine.Screen;
import necesse.engine.sound.SoundEffect;
import necesse.engine.tickManager.TickManager;
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

public class meleeghost extends HostileMob {
    public static GameTexture texture;
    private float particleBuffer;

    public meleeghost() {
        super(550);
        this.setSpeed(90.0F);
        this.setFriction(3.0F);
        this.setArmor(20);
        this.moveAccuracy = 10;
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-14, -12, 28, 24);
        this.selectBox = new Rectangle(-14, -55, 28, 48);
        this.isHostile = true;
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI(this, new CollisionPlayerChaserWandererAI(null, 800, new GameDamage(70.0F), 100, 40000), new FlyingAIMover());
    }

    public boolean isLavaImmune() {
        return true;
    }
    public boolean canBePushed(Mob other) {
        return false;
    }
    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(this.getTileX(), this.getTileY());
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 55;
        Point sprite = this.getAnimSprite(x, y, this.dir);
        drawY += this.getLevel().getTile(this.getTileX(), this.getTileY()).getMobSinkingAmount(this);
        DrawOptions drawOptions = texture.initDraw().sprite(0, sprite.y, 64).light(light.minLevelCopy(100)).alpha(0.5F).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
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
                ParticleOption.DrawModifier mod = (wrapper, i, i1, v) -> wrapper.size(75);
                owner.getLevel().entityManager.addParticle(owner.x, owner.y - 16, Particle.GType.IMPORTANT_COSMETIC).sprite(SoulChasm.particlePhantomBodySection.sprite(0, owner.dir, 64)).size(mod).fadesAlpha(0.1F, 1.0F).fadesAlphaTime(100, 800).minDrawLight(100).dontRotate().lifeTime(900);
            }
            this.particleBuffer = particleBuffer;
        }
    }

    protected void playDeathSound() {
        Screen.playSound(GameResources.fadedeath3, SoundEffect.effect(this).volume(0.2F).pitch(0.9F));
    }

    protected void playHitSound() {
        Screen.playSound(GameResources.swoosh, SoundEffect.effect(this).pitch(0.7F).volume(0.2F));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
    }
}
