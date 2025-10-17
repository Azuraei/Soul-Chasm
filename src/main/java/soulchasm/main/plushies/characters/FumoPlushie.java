package soulchasm.main.plushies.characters;

import necesse.gfx.gameTexture.GameTexture;
import soulchasm.main.plushies.PlushieMob;

public class FumoPlushie extends PlushieMob {
    public static GameTexture gameTexture;
    public FumoPlushie() {
        super("fumo");
        this.texture = gameTexture;
    }
}
