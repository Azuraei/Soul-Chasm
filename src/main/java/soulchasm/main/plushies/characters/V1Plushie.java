package soulchasm.main.plushies.characters;

import necesse.gfx.gameTexture.GameTexture;
import soulchasm.main.plushies.PlushieMob;

public class V1Plushie extends PlushieMob {
    public static GameTexture gameTexture;
    public V1Plushie() {
        super("v1");
        this.texture = gameTexture;
    }
}
