package soulchasm.main.Buffs.TrinketsBuffs.PhantomDashersBuffs;

import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.MovementTickBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import soulchasm.SoulChasm;

public class phantomdashersactivebuff extends Buff implements MovementTickBuff {
    public phantomdashersactivebuff() {
        this.shouldSave = false;
        this.isVisible = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.SPEED_FLAT, 10.0F);
        buff.setModifier(BuffModifiers.SPEED, 1.2F);
        buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 1.2F);
        buff.setModifier(BuffModifiers.WATER_WALKING, true);
    }

    public void tickMovement(ActiveBuff buff, float delta) {
        Mob owner = buff.owner;
        if (owner.isClient() && (owner.dx != 0.0F || owner.dy != 0.0F)) {
            float speed = owner.getCurrentSpeed() * delta / 250.0F;
            GNDItemMap gndData = buff.getGndData();
            float particleBuffer = gndData.getFloat("particleBuffer") + speed;
            if (particleBuffer >= 15.0F) {
                particleBuffer -= 15.0F;
                owner.getLevel().entityManager.addParticle(owner.x, owner.y - 20, Particle.GType.IMPORTANT_COSMETIC).sprite(SoulChasm.particlePhantomBodySection.sprite(0, owner.getDir(), 64)).size((wrapper, i, i1, v) -> wrapper.size(64)).fadesAlpha(0.1F, 1.0F).fadesAlphaTime(100, 1000).minDrawLight(100).dontRotate().lifeTime(800);
            }
            gndData.setFloat("particleBuffer", particleBuffer);
            float xOffset;
            xOffset = gndData.getFloat("soundBuffer") + Math.min(speed, 80.0F * delta / 250.0F);
            if (xOffset >= 125.0F) {
                xOffset -= 125.0F;
                SoundManager.playSound(GameResources.swoosh, SoundEffect.effect(owner).volume(0.1F).pitch(0.8F));
            }
            gndData.setFloat("soundBuffer", xOffset);
        }

    }
}
