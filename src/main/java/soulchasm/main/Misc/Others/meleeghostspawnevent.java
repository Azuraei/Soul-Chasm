package soulchasm.main.Misc.Others;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.particle.Particle;
import soulchasm.SoulChasm;
import soulchasm.main.Mobs.Agressive.meleeghost;

public class meleeghostspawnevent extends LevelEvent {
    long startTime;
    float chargeUpDuration;
    int x;
    int y;
    boolean mainParticleSpawned;

    public meleeghostspawnevent() {
    }

    public meleeghostspawnevent(int x, int y, float duration) {
        this.x = x;
        this.y = y;
        this.chargeUpDuration = duration;
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
    }

    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.chargeUpDuration = reader.getNextFloat();
        this.startTime = reader.getNextLong();
        this.x = reader.getNextInt();
        this.y = reader.getNextInt();
    }

    private void spawnMob(){
        Mob mob = new meleeghost();
        mob.isSummoned = true;
        this.getLevel().entityManager.addMob(mob, this.x, this.y);
    }

    public void clientTick() {
        super.clientTick();
        if ((float)this.level.getTime() < (float)this.startTime + this.chargeUpDuration) {
            if (!this.mainParticleSpawned) {
                float height = 64.0F;
                float var10001 = (float)this.x;
                float var10002 = (float)this.y;
                this.getLevel().entityManager.addParticle(var10001, var10002 + 14 + 32, Particle.GType.CRITICAL).sprite(SoulChasm.particleGhostSpawnSection.sprite(0, 0, 32)).rotation((lifeTime, timeAlive, lifePercent) -> timeAlive * lifePercent + 0.50F).givesLight(230.0F, 0.5F).fadesAlphaTime(250, 150).minDrawLight(150).lifeTime((int) chargeUpDuration).height(height).size((options, lifeTime, timeAlive, lifePercent) -> options.size(40, 40));
                this.mainParticleSpawned = true;
            }
        } else {
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
