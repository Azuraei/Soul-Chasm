package soulchasm.main.Plushies;

import necesse.gfx.gameTexture.GameTexture;

public class FumoPlushie extends PlushieMob {
    public static GameTexture gameTexture;
    public FumoPlushie() {
        super("fumo");
        this.texture = gameTexture;
    }
}
