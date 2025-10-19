package soulchasm.main.plushies.characters;

import necesse.gfx.gameTexture.GameTexture;
import soulchasm.main.plushies.PlushieMob;

public class RockPlushie extends PlushieMob {
    public static GameTexture gameTexture;
    public RockPlushie() {
        super("rock");
        this.texture = gameTexture;
    }
}
