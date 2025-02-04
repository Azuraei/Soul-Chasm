package soulchasm.main.Misc.HauntedModifier;

import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.manager.MobDeathListenerEntityComponent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.gfx.GameResources;
import soulchasm.main.Misc.Events.meleeghostspawnevent;
import soulchasm.main.Mobs.Agressive.lostsoul;
import soulchasm.main.Mobs.Agressive.meleestatue;

import java.util.HashSet;

public class hauntedmodifierlevelevent extends LevelEvent implements MobDeathListenerEntityComponent {
    public hauntedmodifierlevelevent() {
        super(true);
        this.shouldSave = true;
    }

    public void onLevelMobDied(final Mob mob, Attacker attacker, HashSet<Attacker> attackers) {
        if (!mob.isPlayer && mob.isHostile && !mob.isSummoned && !mob.isBoss() && mob.shouldSendSpawnPacket() && !(mob instanceof meleestatue) && !(mob instanceof lostsoul)) {
            meleeghostspawnevent event = new meleeghostspawnevent(mob.getX(), mob.getY(), 1500, mob);
            this.getLevel().entityManager.addLevelEvent(event);
            SoundManager.playSound(GameResources.swoosh, SoundEffect.effect(mob).volume(0.8F));
        }
    }
}