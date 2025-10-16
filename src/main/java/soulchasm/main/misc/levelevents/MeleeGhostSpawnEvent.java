package soulchasm.main.misc.levelevents;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import soulchasm.SoulChasm;
import soulchasm.main.mobs.hostile.MeleeGhost;

import java.awt.*;

public class MeleeGhostSpawnEvent extends LevelEvent {
    public long startTime;
    public float chargeUpDuration;
    public int x;
    public int y;
    public boolean mainParticleSpawned;
    public Mob sourceMob;

    public MeleeGhostSpawnEvent() {
    }

    public MeleeGhostSpawnEvent(int x, int y, float duration, Mob sourceMob) {
        this.x = x;
        this.y = y;
        this.chargeUpDuration = duration;
        this.sourceMob = sourceMob;
    }

    public void init() {
        super.init();
        this.startTime = this.level.getTime();
        this.mainParticleSpawned = false;
    }

    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextFloat(this.chargeUpDuration);
        writer.putNextLong(this.startTime);
        writer.putNextInt(this.x);
        writer.putNextInt(this.y);
        writer.putNextInt(this.sourceMob.getUniqueID());
    }

    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.chargeUpDuration = reader.getNextFloat();
        this.startTime = reader.getNextLong();
        this.x = reader.getNextInt();
        this.y = reader.getNextInt();
        this.sourceMob = GameUtils.getLevelMob(reader.getNextInt(), this.level);
    }

    private void spawnMob(){
        Mob mob = new MeleeGhost();
        Mob source = sourceMob;
        mob.isSummoned = true;
        mob.setMaxHealth(source.getMaxHealthFlat());
        this.getLevel().entityManager.addMob(mob, this.x, this.y);
        mob.setHealth(mob.getMaxHealth());
    }

    public void clientTick() {
        super.clientTick();
        if ((float)this.level.getTime() < (float)this.startTime + this.chargeUpDuration) {
            if (!this.mainParticleSpawned) {
                float height = 64.0F;
                float var10001 = (float)this.x;
                float var10002 = (float)this.y;
                this.getLevel().entityManager.addParticle(var10001, var10002 + 14 + 32, Particle.GType.CRITICAL).sprite(SoulChasm.particleGhostSpawnSection.sprite(0, 0, 32)).rotation((lifeTime, timeAlive, lifePercent) -> timeAlive * lifePercent + 0.50F).givesLight(230.0F, 0.5F).fadesAlphaTime(250, 150).minDrawLight(150).lifeTime((int) chargeUpDuration).height(height).size((options, lifeTime, timeAlive, lifePercent) -> options.size(30, 30));
                this.mainParticleSpawned = true;
            }
        } else {
            for(int i = 0; i < 30; ++i) {
                this.getLevel().entityManager.addParticle(this.x, this.y, Particle.GType.COSMETIC).movesConstant((float)(GameRandom.globalRandom.getIntBetween(5, 20) * (GameRandom.globalRandom.nextBoolean() ? -1 : 1)), (float)(GameRandom.globalRandom.getIntBetween(5, 20) * (GameRandom.globalRandom.nextBoolean() ? -1 : 1))).color(new Color(0, 170, 242));
            }
            SoundManager.playSound(GameResources.swoosh2, SoundEffect.effect(this.x, this.y).pitch(0.5F).volume(0.6F));
            this.over();
        }
    }

    public void serverTick() {
        super.serverTick();
        if (!((float)this.level.getTime() < (float)this.startTime + this.chargeUpDuration)) {
            spawnMob();
            this.over();
        }
    }
}
