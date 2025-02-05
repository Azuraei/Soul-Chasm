package soulchasm.main.Plushies;

import necesse.gfx.gameTexture.GameTexture;

public class V1Plushie extends PlushieMob {
    public static GameTexture gameTexture;
    public V1Plushie() {
        super("v1");
        this.texture = gameTexture;
    }
}
