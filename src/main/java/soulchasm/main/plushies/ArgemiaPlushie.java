package soulchasm.main.plushies;

import necesse.gfx.gameTexture.GameTexture;

public class ArgemiaPlushie extends PlushieMob {
    public static GameTexture gameTexture;
    public ArgemiaPlushie() {
        super("argemia");
        this.texture = gameTexture;
    }
}
