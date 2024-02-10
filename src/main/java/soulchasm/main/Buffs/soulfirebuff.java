package soulchasm.main.Buffs;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.OnFireBuff;
import necesse.entity.particle.Particle;

import java.awt.*;


public class soulfirebuff extends Buff {
    public soulfirebuff() {
        this.canCancel = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.FIRE_DAMAGE_FLAT, 7F);
    }

    public void clientTick(ActiveBuff buff) {
        Mob owner = buff.owner;

        if (buff.owner.isVisible()) {
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 6.0D), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0D), Particle.GType.IMPORTANT_COSMETIC).movesConstant(owner.dx / 10.0F, owner.dy / 10.0F).color(new Color(0, 139, 225)).givesLight(0.0F, 0.5F).height(16.0F);
        }

    }

    public Attacker getSource(Attacker source) {
        return new OnFireBuff.FireAttacker(source);
    }
}
