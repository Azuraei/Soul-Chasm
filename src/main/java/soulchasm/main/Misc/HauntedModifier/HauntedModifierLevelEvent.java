package soulchasm.main.Misc.HauntedModifier;

import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.manager.MobDeathListenerEntityComponent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.gfx.GameResources;
import soulchasm.main.Misc.Events.MeleeGhostSpawnEvent;
import soulchasm.main.Mobs.Agressive.LostSoul;
import soulchasm.main.Mobs.Agressive.MeleeStatue;

import java.util.HashSet;

public class HauntedModifierLevelEvent extends LevelEvent implements MobDeathListenerEntityComponent {
    public HauntedModifierLevelEvent() {
        super(true);
        this.shouldSave = true;
    }

    public void onLevelMobDied(final Mob mob, Attacker attacker, HashSet<Attacker> attackers) {
        if (!mob.isPlayer && mob.isHostile && !mob.isSummoned && !mob.isBoss() && mob.shouldSendSpawnPacket() && !(mob instanceof MeleeStatue) && !(mob instanceof LostSoul)) {
            MeleeGhostSpawnEvent event = new MeleeGhostSpawnEvent(mob.getX(), mob.getY(), 1500, mob);
            this.getLevel().entityManager.addLevelEvent(event);
            SoundManager.playSound(GameResources.swoosh, SoundEffect.effect(mob).volume(0.8F));
        }
    }
}