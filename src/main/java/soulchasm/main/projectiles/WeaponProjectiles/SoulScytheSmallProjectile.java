package soulchasm.main.projectiles.WeaponProjectiles;

import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.level.maps.Level;

public class SoulScytheSmallProjectile extends SoulScytheProjectile {
    public SoulScytheSmallProjectile() {
    }

    public SoulScytheSmallProjectile(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback) {
        this();
        this.setLevel(level);
        this.setOwner(owner);
        this.x = x;
        this.y = y;
        this.setTarget(targetX, targetY);
        this.speed = speed;
        this.distance = distance;
        this.setDamage(damage);
        this.knockback = knockback;
    }
    public void init() {
        super.init();
        this.setWidth(120.0F, true);
        this.piercing = 5;
        this.particleRandomOffset = 20.0F;
    }
}
