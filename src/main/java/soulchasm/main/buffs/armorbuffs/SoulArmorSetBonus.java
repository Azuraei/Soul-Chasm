package soulchasm.main.buffs.armorbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.level.maps.Level;

import java.awt.*;

public class SoulArmorSetBonus extends SetBonusBuff implements BuffAbility {
    public SoulArmorSetBonus() {
        this.canCancel = false;
        this.isVisible = false;
        this.shouldSave = false;
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
    }

    public void tickEffect(ActiveBuff buff, Mob owner) {
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content) {
        Mob owner = buff.owner;
        float active = 10.0F;
        float cooldown = 5.0F;
        owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("souldischargebuff"), owner, active, null), false);
        owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("soularmorcooldown"), owner, cooldown, null), false);
        spawnProjectiles(player);
        if(GameRandom.globalRandom.getChance(0.5) && owner.buffManager.hasBuff("souldischargebuff")){
            buff.owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("souldischargesicknessdebuff"), buff.owner, 30F, null), false);
        }
    }

    private void spawnProjectiles(PlayerMob playerMob){
        Level level = playerMob.getLevel();
        Color color = new Color(41, 137, 255);
        if (level.isClient()) {
            int particles = 35;
            float anglePerParticle = 360.0F / (float)particles;
            ParticleTypeSwitcher typeSwitcher = new ParticleTypeSwitcher(Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);
            for(int i = 0; i < particles; ++i) {
                int angle = (int)((float)i * anglePerParticle + GameRandom.globalRandom.nextFloat() * anglePerParticle);
                float dx = (float)Math.sin(Math.toRadians(angle)) * (float)GameRandom.globalRandom.getIntBetween(40, 60);
                float dy = (float)Math.cos(Math.toRadians(angle)) * (float)GameRandom.globalRandom.getIntBetween(40, 60) * 0.8F;
                level.entityManager.addParticle(playerMob, typeSwitcher.next()).movesFriction(dx, dy, 0.2F).color(color).heightMoves(0.0F, 10.0F).lifeTime(500);
            }
            SoundManager.playSound(GameResources.magicbolt4, SoundEffect.effect(playerMob).volume(0.1F).pitch(2.5F));
        }
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content) {
        return !buff.owner.buffManager.hasBuff("soularmorcooldown");
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "soularmorsetbonus"));
        return tooltips;
    }
}
