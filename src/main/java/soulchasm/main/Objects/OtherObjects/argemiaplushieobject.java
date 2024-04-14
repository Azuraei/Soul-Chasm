package soulchasm.main.Objects.OtherObjects;

import necesse.engine.Screen;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.level.maps.Level;
import soulchasm.SoulChasm;
import soulchasm.main.Misc.Others.plushieobject;

import java.awt.*;

public class argemiaplushieobject extends plushieobject {
    public argemiaplushieobject() {
        super("argemiaplushieobject", new Color(255, 0, 92));
    }
    @Override
    public void interact(Level level, int x, int y, PlayerMob player) {
        if(level.isClient()){
            float pitch = GameRandom.globalRandom.getFloatBetween(0.8F, 1.6F);
            if(GameRandom.globalRandom.getChance(0.9F)){
                Screen.playSound(SoulChasm.plushie_squeak, SoundEffect.effect(x * 32, y * 32).volume(0.5F).pitch(pitch));
            } else {
                Screen.playSound(SoulChasm.argemiaplushie_meow, SoundEffect.effect(x * 32, y * 32).volume(3.0F).pitch(pitch));
            }
        }
    }
}