package soulchasm.main.Projectiles;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.projectile.bulletProjectile.BulletProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameSprite;
import necesse.level.maps.LevelObjectHit;

import java.awt.*;

public class soulrevolverprojectile extends BulletProjectile {

    public soulrevolverprojectile() {
        this.height = 18.0F;
    }

    public soulrevolverprojectile(float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback, Mob owner) {
        this();
        this.setLevel(owner.getLevel());
        this.applyData(x, y, targetX, targetY, speed, distance, damage, knockback, owner);
    }

    public void init() {
        super.init();
        this.givesLight = true;
        this.trailOffset = 0.0F;
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y) {
        if (this.getLevel().isServer()) {
            if (mob != null) {
                mob.buffManager.addBuff(new ActiveBuff("souldeathmarkstackbuff", mob, 10F, this.getAttackOwner()), true);
            }

        }
    }

    public Trail getTrail() {
        Trail trail = new Trail(this, this.getLevel(), new Color(0, 116, 176), 22.0F, 100, this.getHeight());
        trail.sprite = new GameSprite(GameResources.chains, 7, 0, 32);
        return trail;
    }

    protected Color getWallHitColor() {
        return new Color(2, 177, 246);
    }
}
