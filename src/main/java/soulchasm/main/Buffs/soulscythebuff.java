package soulchasm.main.Buffs;

import necesse.engine.Screen;
import necesse.engine.sound.SoundEffect;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.gfx.GameResources;

public class soulscythebuff extends Buff {
    public soulscythebuff() {
        this.isVisible = false;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {}

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event) {
        super.onHasAttacked(buff, event);
        Mob target = event.target;
        if(target.getHealthPercent()<=0.3 && target.getHealth()>=event.damage){
            target.setHealth(0, event.attacker);
            target.spawnDamageText(9999999, 25, true);
            Screen.playSound(GameResources.fadedeath1, SoundEffect.globalEffect().volume(1.6F).pitch(0.5F));
        }
    }
}