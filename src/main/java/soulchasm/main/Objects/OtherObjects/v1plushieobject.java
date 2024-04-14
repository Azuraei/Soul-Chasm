package soulchasm.main.Objects.OtherObjects;

import necesse.engine.Screen;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.level.maps.Level;
import soulchasm.SoulChasm;
import soulchasm.main.Misc.Others.plushieobject;

import java.awt.*;

public class v1plushieobject extends plushieobject {
    public v1plushieobject() {
        super("v1plushieobject", new Color(0, 81, 255));
    }
    public void interact(Level level, int x, int y, PlayerMob player) {
        super.interact(level, x, y, player);
        if(GameRandom.globalRandom.getChance(0.1F)){
            if(level.isClient()){
                Screen.playSound(SoulChasm.ultra_parry, SoundEffect.effect(x * 32, y * 32).volume(1.0F));
            } else {
                float x1 = player.getX() - x * 32;
                float y1 = player.getY() - y * 32;
                player.isServerHit(new GameDamage(30), x1, y1, 250, player);
            }
        }
    }
}