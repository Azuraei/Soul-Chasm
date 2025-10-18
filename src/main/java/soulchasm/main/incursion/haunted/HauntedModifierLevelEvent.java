package soulchasm.main.incursion.haunted;

import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.manager.MobDeathListenerEntityComponent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.gfx.GameResources;
import soulchasm.main.misc.levelevents.MeleeGhostSpawnEvent;
import soulchasm.main.mobs.hostile.LostSoul;
import soulchasm.main.mobs.hostile.ChasmWarriorStatue;

import java.util.HashSet;

public class HauntedModifierLevelEvent extends LevelEvent implements MobDeathListenerEntityComponent {
    public HauntedModifierLevelEvent() {
        super(true);
        this.shouldSave = true;
    }

    public void onLevelMobDied(final Mob mob, Attacker attacker, HashSet<Attacker> attackers) {
        if (!mob.isPlayer && mob.isHostile && !mob.isSummoned && !mob.isBoss() && mob.shouldSendSpawnPacket() && !(mob instanceof ChasmWarriorStatue) && !(mob instanceof LostSoul)) {
            MeleeGhostSpawnEvent event = new MeleeGhostSpawnEvent(mob.getX(), mob.getY(), 1500, mob);
            this.getLevel().entityManager.events.add(event);
            SoundManager.playSound(GameResources.swoosh, SoundEffect.effect(mob).volume(0.8F));
        }
    }
}