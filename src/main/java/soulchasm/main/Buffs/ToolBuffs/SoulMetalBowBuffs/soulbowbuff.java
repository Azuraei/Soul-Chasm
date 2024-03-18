package soulchasm.main.Buffs.ToolBuffs.SoulMetalBowBuffs;

import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;

import java.awt.*;

public class soulbowbuff extends Buff {
    public Color particleColor = new Color(0, 111, 190);

    public soulbowbuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        this.canCancel = false;
        this.isImportant = true;
        this.isVisible = true;
        this.isPassive = false;
        this.shouldSave = true;
    }
    private void removeSelf(ActiveBuff buff){
        if(buff.getGndData().getInt("stacks")>=3){
            buff.remove();
        }
    }

    @Override
    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        removeSelf(buff);
    }

    @Override
    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        removeSelf(buff);
        int particles = 2;
        float anglePerParticle = 360.0F / (float)particles;
        ParticleTypeSwitcher typeSwitcher = new ParticleTypeSwitcher(Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);
        for(int i = 0; i < particles; ++i) {
            int angle = (int)((float)i * anglePerParticle + GameRandom.globalRandom.nextFloat() * anglePerParticle);
            float dx = (float)Math.sin(Math.toRadians(angle)) * (float)GameRandom.globalRandom.getIntBetween(30, 50);
            float dy = (float)Math.cos(Math.toRadians(angle)) * (float)GameRandom.globalRandom.getIntBetween(30, 50) * 0.8F;
            buff.owner.getLevel().entityManager.addParticle(buff.owner, typeSwitcher.next()).movesFriction(dx, dy, 0.8F).color(particleColor).lifeTime(400);
        }
    }
}