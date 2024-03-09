package soulchasm.main.Buffs.ToolBuffs;

import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import soulchasm.main.Projectiles.SealProjectiles.soularrowprojectile;

import java.awt.geom.Point2D;

public class soulbowbuff extends Buff {
    private int stackAmount;
    private int arrowCooldown;
    public soulbowbuff() {
        this.isVisible = false;
        this.isImportant = true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.RANGED_ATTACK_SPEED, 0F);
    }

    public void shootProjectile(MobWasHitEvent event, ActiveBuff buff){
        float velocity = 200.0F;
        Mob owner = buff.owner;
        Mob target = event.target;
        for(int i = 0; i < 3; i++){
            GameRandom random = new GameRandom();
            GameDamage finalDamage = new GameDamage(event.damage).modFinalMultiplier(0.11F);
            Point2D.Float dir = GameMath.normalize(owner.x - target.x, owner.y - target.y);
            int offsetDistance = random.getIntBetween(50, 120);
            Point2D.Float offset = new Point2D.Float(dir.x * (float) offsetDistance, dir.y * (float) offsetDistance);
            offset = GameMath.getPerpendicularPoint(offset, (float) random.getIntBetween(-50, 50), dir);
            soularrowprojectile projectile = new soularrowprojectile(owner.getLevel(), owner, owner.x + offset.x, owner.y + offset.y, target.x, target.y, velocity, 800, finalDamage, 20);
            projectile.moveDist(10);
            projectile.piercing = 0;
            projectile.showEffects = false;
            owner.getLevel().entityManager.projectiles.add(projectile);
        }
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event) {
        super.onHasAttacked(buff, event);
        if(!event.wasPrevented && event.target != null){
            if(stackAmount<100){
                stackAmount = stackAmount + 2;
            } else {
                arrowCooldown++;
            }
            if (arrowCooldown>=10){
                shootProjectile(event, buff);
                arrowCooldown=0;
            }
        }
    }

    public void updateBuff(ActiveBuff buff){
        if(buff.getModifier(BuffModifiers.RANGED_ATTACK_SPEED)!=0.01F * stackAmount){
            buff.setModifier(BuffModifiers.RANGED_ATTACK_SPEED, 0.01F * stackAmount);
            buff.forceManagerUpdate();
        }
        if(!buff.owner.isInCombat() && stackAmount!=0){
            stackAmount=0;
            arrowCooldown=0;
        }
    }

    @Override
    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        updateBuff(buff);
    }

    @Override
    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        updateBuff(buff);
    }
}