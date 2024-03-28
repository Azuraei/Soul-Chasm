package soulchasm.main.Misc.Others.SpinningProjectileSpawnerEvent;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ParticleOption;
import necesse.gfx.GameResources;
import necesse.gfx.drawOptions.texture.SharedTextureDrawOptions;

import java.awt.*;
import java.awt.geom.Point2D;

public class spinspawnvisualevent extends LevelEvent {
    long startTime;
    float chargeUpDuration;
    int x;
    int y;
    boolean mainParticleSpawned;

    public spinspawnvisualevent() {
    }

    public spinspawnvisualevent(int x, int y, float duration) {
        this.x = x;
        this.y = y;
        this.chargeUpDuration = duration;
    }

    public void init() {
        super.init();
        this.startTime = this.level.getTime();
        this.mainParticleSpawned = false;
        if (this.isServer()) {
            this.over();
        }

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

    public void clientTick() {
        super.clientTick();
        if ((float)this.level.getTime() < (float)this.startTime + this.chargeUpDuration) {
            if (!this.mainParticleSpawned) {
                float height = 25.0F;
                float var10001 = (float)this.x;
                float var10002 = (float)this.y;
                this.getLevel().entityManager.addParticle(var10001, var10002, Particle.GType.CRITICAL).sprite(GameResources.explosiveModifierChargeUp.sprite(0, 0, 32)).rotation(new ParticleOption.FloatGetter() {
                    public float get(int lifeTime, int timeAlive, float lifePercent) {
                        return (float)timeAlive * lifePercent + 0.25F;
                    }
                }).givesLight(75.0F, 0.5F).fadesAlphaTime(1500, 250).lifeTime(1750).height(height).size(new ParticleOption.DrawModifier() {
                    public void modify(SharedTextureDrawOptions.Wrapper options, int lifeTime, int timeAlive, float lifePercent) {
                        options.size(32, 32);
                    }
                });
                this.mainParticleSpawned = true;
            }

            for(int i = 0; i < 3; ++i) {
                int angle = GameRandom.globalRandom.nextInt(360);
                Point2D.Float dir = GameMath.getAngleDir((float)angle);
                float range = GameRandom.globalRandom.getFloatBetween(25.0F, 40.0F);
                float startX = (float)this.x + dir.x * range;
                float startY = (float)(this.y + 4);
                float endHeight = 29.0F;
                float startHeight = endHeight + dir.y * range;
                int lifeTime = GameRandom.globalRandom.getIntBetween(200, 500);
                float speed = dir.x * range * 250.0F / (float)lifeTime;
                Color color1 = new Color(39, 111, 156);
                Color color2 = new Color(62, 114, 191);
                Color color3 = new Color(39, 159, 233);
                Color color = GameRandom.globalRandom.getOneOf(color1, color2, color3);
                this.getLevel().entityManager.addParticle(startX, startY, Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.puffParticles.sprite(GameRandom.globalRandom.nextInt(5), 0, 12)).sizeFades(10, 16).rotates().givesLight(75.0F, 0.5F).heightMoves(startHeight, endHeight).movesConstant(-speed, 0.0F).color(color).fadesAlphaTime(100, 50).lifeTime(lifeTime);
            }
        } else {
            this.over();
        }

    }
}
