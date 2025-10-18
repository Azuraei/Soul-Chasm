package soulchasm.main.misc.levelevents;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.MobAbilityLevelEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.DeathMessageTable;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.gfx.GameResources;
import soulchasm.main.projectiles.SoulHomingProjectile;
import soulchasm.main.projectiles.bossprojectiles.SpinnerSpikeProjectile;

import java.awt.geom.Point2D;

public class HomingProjectilesEvent extends MobAbilityLevelEvent implements Attacker {
    protected int x;
    protected int y;
    protected int amount;
    protected GameDamage damage;

    public HomingProjectilesEvent() {
    }

    public HomingProjectilesEvent(Mob owner, int x, int y, GameRandom uniqueIDRandom, GameDamage damage, int amount) {
        super(owner, uniqueIDRandom);
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.amount = amount;
    }

    public void applySpawnPacket(PacketReader reader) {
        super.applySpawnPacket(reader);
        this.x = reader.getNextInt();
        this.y = reader.getNextInt();
        this.amount = reader.getNextInt();
    }

    public void setupSpawnPacket(PacketWriter writer) {
        super.setupSpawnPacket(writer);
        writer.putNextInt(this.x);
        writer.putNextInt(this.y);
        writer.putNextInt(this.amount);
    }

    public void init() {
        super.init();
    }

    public void clientTick() {}

    public void serverTick() {
        if (!this.isOver()) {
            for(int i = 0; i<3; i++) {
                float angle = GameRandom.globalRandom.getIntBetween(0, 360);
                SoulHomingProjectile projectile = new SoulHomingProjectile(this.getLevel(), owner, owner.x, owner.y, owner.x, owner.y, 40, 600, this.damage, 20);
                projectile.setAngle(projectile.getAngle() + angle * i);
                projectile.turnSpeed = 0.02F;
                this.getLevel().entityManager.projectiles.add(projectile);
            }
        }
        this.over();
    }

    public GameMessage getAttackerName() {
        return this.owner != null ? this.owner.getAttackerName() : new StaticMessage("error");
    }

    public DeathMessageTable getDeathMessages() {
        return this.owner != null ? this.owner.getDeathMessages() : DeathMessageTable.fromRange("generic", 8);
    }

    public Mob getFirstAttackOwner() {
        return this.owner;
    }
}
