package soulchasm.main.Misc;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameUtils;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.particle.Particle;
import soulchasm.SoulChasm;

import java.awt.*;

public class idolshieldvisualevent extends LevelEvent {
    public Mob startMob;
    public Mob endMob;
    public long startTime;
    public int particlesSpawned = 0;
    public int particleCount = 8;
    public int eventLifeTime = 800;
    ParticleTypeSwitcher particleTypeSwitcher;

    public idolshieldvisualevent() {
        this.particleTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.COSMETIC, Particle.GType.IMPORTANT_COSMETIC);
    }

    public idolshieldvisualevent(Mob startMob, Mob endMob) {
        super(true);
        this.particleTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.COSMETIC, Particle.GType.IMPORTANT_COSMETIC);
        this.startMob = startMob;
        this.endMob = endMob;
    }

    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextInt(this.startMob.getUniqueID());
        writer.putNextInt(this.endMob.getUniqueID());
    }

    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.startMob = GameUtils.getLevelMob(reader.getNextInt(), this.level);
        this.endMob = GameUtils.getLevelMob(reader.getNextInt(), this.level);
    }

    public void init() {
        super.init();
        if (this.startMob == null || this.endMob == null || this.isServer()) {
            this.over();
        }
        this.startTime = this.getLocalTime();
    }

    public void tickMovement(float delta) {
        super.tickMovement(delta);
        long timeSinceStart = this.getLocalTime() - this.startTime;
        if (timeSinceStart > (long)this.eventLifeTime) {
            this.over();
        } else {
            float lifePercent = (float)timeSinceStart / (float)this.eventLifeTime;
            float expectedParticlesSpawned = lifePercent * (float)this.particleCount;

            while((float)this.particlesSpawned < expectedParticlesSpawned) {
                ++this.particlesSpawned;
                float particleStartX = this.startMob.x;
                float particleStartY = this.startMob.y;
                this.level.entityManager.addParticle(particleStartX, particleStartY, this.particleTypeSwitcher.next()).moves((pos, particleDelta, lifeTime, timeAlive, particleLifePercent) -> {
                    pos.x = GameMath.lerp(particleLifePercent, particleStartX, this.endMob.x);
                    pos.y = GameMath.lerp(particleLifePercent, particleStartY, this.endMob.y);
                }).sprite(SoulChasm.particleWispSection.sprite(0, 0, 16)).size((options, lifeTime, timeAlive, particleLifePercent) -> options.size(20, 20)).lifeTime(400).fadesAlpha(1, 1).fadesAlphaTime(200, 200).color(new Color(102, 189, 255)).height(16.0F);
            }

        }
    }
}
