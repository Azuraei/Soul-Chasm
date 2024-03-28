package soulchasm.main.Misc.Others.SpinningProjectileSpawnerEvent;

import necesse.engine.Screen;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.MobAbilityLevelEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.DeathMessageTable;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.gfx.GameResources;

public class spinspawnevent extends MobAbilityLevelEvent implements Attacker {
    protected long spawnTime;
    protected int x;
    protected int y;
    protected GameDamage damage;
    protected boolean playedStartSound;

    public spinspawnevent() {
    }

    public spinspawnevent(Mob owner, int x, int y, GameRandom uniqueIDRandom, GameDamage damage) {
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
    }

    public void clientTick() {
        if (!this.isOver()) {
            long eventTime = this.level.getWorldEntity().getTime() - this.spawnTime;
            if (eventTime > 1000L && !this.playedStartSound) {
                Screen.playSound(GameResources.fireworkCrack, SoundEffect.effect((float)this.x, (float)this.y));
                this.playedStartSound = true;
            }

            if (eventTime > 1200L) {
                Screen.playSound(GameResources.firespell1, SoundEffect.effect((float)this.x, (float)this.y).volume(0.5F));
                this.over();
            }

        }
    }

    public void serverTick() {
        if (!this.isOver()) {
            long eventTime = this.level.getWorldEntity().getTime() - this.spawnTime;
            if (eventTime > 1200L) {
                this.over();
            }

        }
    }

    public GameMessage getAttackerName() {
        return this.owner != null ? this.owner.getAttackerName() : new StaticMessage("AD_SPIN_ER");
    }

    public DeathMessageTable getDeathMessages() {
        return this.owner != null ? this.owner.getDeathMessages() : DeathMessageTable.fromRange("generic", 8);
    }

    public Mob getFirstAttackOwner() {
        return this.owner;
    }
}
