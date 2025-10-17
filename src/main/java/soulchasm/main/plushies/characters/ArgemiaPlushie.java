package soulchasm.main.plushies.characters;

import necesse.gfx.gameTexture.GameTexture;
import soulchasm.main.plushies.PlushieMob;

public class ArgemiaPlushie extends PlushieMob {
    public static GameTexture gameTexture;
    public ArgemiaPlushie() {
        super("argemia");
        this.texture = gameTexture;
    }
}
