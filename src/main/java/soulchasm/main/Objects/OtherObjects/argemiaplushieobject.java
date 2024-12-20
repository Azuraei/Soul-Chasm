package soulchasm.main.Objects.OtherObjects;

import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import soulchasm.SoulChasm;
import soulchasm.main.Misc.Others.plushieobject;

import java.awt.*;

public class argemiaplushieobject extends plushieobject {
    public argemiaplushieobject() {
        super("argemiaplushieobject", new Color(255, 0, 92));
        this.texts = new String[]{"pet", "meow", "shrimp", ">^-^<"};
    }
    public void playSqueak(int x, int y) {
        if(GameRandom.globalRandom.getChance(0.1F)){
            float pitch = GameRandom.globalRandom.getFloatBetween(0.8F, 1.6F);
            SoundManager.playSound(SoulChasm.argemiaplushie_meow, SoundEffect.effect(x, y).volume(2.0F).pitch(pitch));
        } else {
            super.playSqueak(x, y);
        }
    }
}