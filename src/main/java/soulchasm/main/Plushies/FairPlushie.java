package soulchasm.main.Plushies;

import necesse.gfx.gameTexture.GameTexture;

public class FairPlushie extends PlushieMob {
    public static GameTexture gameTexture;
    public FairPlushie() {
        super("fair");
        this.texture = gameTexture;
    }
}
