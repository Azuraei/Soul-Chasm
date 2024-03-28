package soulchasm.main.Misc.Others.SpinningProjectileSpawnerEvent;

import necesse.engine.Screen;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.MobAbilityLevelEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.DeathMessageTable;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.gfx.GameResources;
import soulchasm.main.Projectiles.BossProjectiles.soulbossspikeprojectile;

import java.awt.geom.Point2D;

public class spinspawnevent extends MobAbilityLevelEvent implements Attacker {
    protected long spawnTime;
    protected int x;
    protected int y;
    protected int duration;
    protected GameDamage damage;
    protected boolean playedStartSound;
    private int shootNextAngle = 0;
    private int tickCounter;

    public spinspawnevent() {
    }

    public spinspawnevent(Mob owner, int x, int y, GameRandom uniqueIDRandom, GameDamage damage, int duration) {
        super(owner, uniqueIDRandom);
        this.spawnTime = owner.getWorldEntity().getTime();
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.duration = duration;
    }

    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.spawnTime = reader.getNextLong();
        this.x = reader.getNextInt();
        this.y = reader.getNextInt();
        this.duration = reader.getNextInt();
    }

    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextLong(this.spawnTime);
        writer.putNextInt(this.x);
        writer.putNextInt(this.y);
        writer.putNextInt(this.duration);
    }

    public void init() {
        super.init();
    }

    public void clientTick() {
        ++this.tickCounter;
        if (!this.isOver()) {
            long eventTime = this.level.getWorldEntity().getTime() - this.spawnTime;
            if (eventTime > duration && !this.playedStartSound) {
                Screen.playSound(GameResources.fireworkCrack, SoundEffect.effect((float)this.x, (float)this.y));
                this.playedStartSound = true;
            }
            if(this.tickCounter <= 18 && this.tickCounter % 2 == 0){
                Screen.playSound(GameResources.magicbolt1, SoundEffect.effect((float)this.x, (float)this.y).volume(0.2F));
            }

            if (eventTime > duration + 200) {
                this.over();
            }

        }
    }

    public void serverTick() {
        ++this.tickCounter;
        if (!this.isOver()) {
            long eventTime = this.level.getWorldEntity().getTime() - this.spawnTime;
            if(this.tickCounter <= 18){
                Point2D.Float dir = GameMath.getAngleDir(shootNextAngle);
                soulbossspikeprojectile projectile = new soulbossspikeprojectile(this.getLevel(), x, y, x + dir.x * 100.0F, y + dir.y * 100.0F, 800, damage, 40, owner);
                projectile.moveDist(30.0);
                this.getLevel().entityManager.projectiles.add(projectile);
                shootNextAngle += 20;
            }
            if (eventTime > duration + 200) {
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
