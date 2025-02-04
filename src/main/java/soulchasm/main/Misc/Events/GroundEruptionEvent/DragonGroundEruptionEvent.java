package soulchasm.main.Misc.Events.GroundEruptionEvent;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.levelEvent.mobAbilityLevelEvent.MobAbilityLevelEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.DeathMessageTable;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ParticleOption;
import necesse.gfx.GameResources;
import soulchasm.SoulChasm;

public class DragonGroundEruptionEvent extends MobAbilityLevelEvent implements Attacker {
    protected long spawnTime;
    protected int x;
    protected int y;
    protected GameDamage damage;
    protected boolean playedStartSound;

    public DragonGroundEruptionEvent() {
    }

    public DragonGroundEruptionEvent(Mob owner, int x, int y, GameRandom uniqueIDRandom, GameDamage damage) {
        super(owner, uniqueIDRandom);
        this.spawnTime = owner.getWorldEntity().getTime();
        this.x = x;
        this.y = y;
        this.damage = damage;
    }

    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.spawnTime = reader.getNextLong();
        this.x = reader.getNextInt();
        this.y = reader.getNextInt();
    }

    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextLong(this.spawnTime);
        writer.putNextInt(this.x);
        writer.putNextInt(this.y);
    }

    public void init() {
        super.init();
        if (this.isClient()) {
            this.level.entityManager.addParticle(new DragonGroundEruptionParticle(this.level, (float)this.x, (float)this.y, this.spawnTime, 1000L), Particle.GType.CRITICAL);
        }
    }

    public void spawnSprayParticles(int amount) {
        ParticleTypeSwitcher particleTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);
        for (int i = 0; i < amount; ++i) {
            float posX = this.x + GameRandom.globalRandom.floatGaussian() * 24.0F;
            float posY = this.y + GameRandom.globalRandom.floatGaussian() * 24.0F;
            float projectileHeight = 0;
            float startHeight = GameRandom.globalRandom.getFloatBetween(projectileHeight - 2.0F, projectileHeight + 4.0F);
            float startHeightSpeed = GameRandom.globalRandom.getFloatBetween(0.0F, 60.0F);
            float endHeight = GameRandom.globalRandom.getFloatBetween(-10.0F, -5.0F);
            float gravity = GameRandom.globalRandom.getFloatBetween(0.1F, 0.2F);
            float floatPower = GameRandom.globalRandom.getFloatBetween(0.2F, 0.8F);
            float power = floatPower * (300.0F);
            float friction = 0.8F;
            int lifeAdded = (int)(350.0F * floatPower);
            int timeToLive = GameRandom.globalRandom.getIntBetween(250 + lifeAdded, 750 + lifeAdded);
            int timeToFadeOut = GameRandom.globalRandom.getIntBetween(800, 1200);
            int totalTime = timeToLive + timeToFadeOut;
            ParticleOption.HeightMover heightMover = new ParticleOption.HeightMover(startHeight, startHeightSpeed, gravity, 1.0F, endHeight, 0.0F);
            ParticleOption.FrictionMover frictionMover = new ParticleOption.FrictionMover(GameRandom.globalRandom.getFloatBetween(-0.1F, 0.1F) * power, -1 * power, friction);
            ParticleOption.CollisionMover mover = new ParticleOption.CollisionMover(this.getLevel(), frictionMover);
            this.getLevel().entityManager.addParticle(posX, posY, particleTypeSwitcher.next()).fadesAlphaTime(10, timeToFadeOut).sizeFadesInAndOut(20, 40, 100, timeToFadeOut).height(heightMover).rotates().moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
                if (heightMover.currentHeight > endHeight && (!this.removed())) {
                    mover.tick(pos, delta, lifeTime, timeAlive, lifePercent);
                }

            }).sprite(SoulChasm.particleFlamethrowerSection.sprite(GameRandom.globalRandom.nextInt(6), 0, 20, 20)).lifeTime(totalTime);
        }
    }

    public void clientTick() {
        if (!this.isOver()) {
            long eventTime = this.level.getWorldEntity().getTime() - this.spawnTime;
            if (eventTime > 1000L && !this.playedStartSound) {
                SoundManager.playSound(GameResources.fireworkCrack, SoundEffect.effect((float)this.x, (float)this.y));
                this.playedStartSound = true;
            }

            if (eventTime > 1200L) {
                SoundManager.playSound(GameResources.firespell1, SoundEffect.effect((float)this.x, (float)this.y).volume(0.5F));
                spawnSprayParticles(120);
                this.over();
            }

        }
    }

    public void serverTick() {
        if (!this.isOver()) {
            long eventTime = this.level.getWorldEntity().getTime() - this.spawnTime;
            if (eventTime > 1200L) {
                DragonExplosionEvent event = new DragonExplosionEvent(x, y, 100, damage, false, 0, owner);
                this.getLevel().entityManager.addLevelEvent(event);
                this.over();
            }

        }
    }

    public GameMessage getAttackerName() {
        return this.owner != null ? this.owner.getAttackerName() : new StaticMessage("AD_GROUND_ER");
    }

    public DeathMessageTable getDeathMessages() {
        return this.owner != null ? this.owner.getDeathMessages() : DeathMessageTable.fromRange("generic", 8);
    }

    public Mob getFirstAttackOwner() {
        return this.owner;
    }
}
