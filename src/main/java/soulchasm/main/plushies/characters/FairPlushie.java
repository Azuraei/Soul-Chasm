package soulchasm.main.plushies.characters;

import necesse.gfx.gameTexture.GameTexture;
import soulchasm.main.plushies.PlushieMob;

public class FairPlushie extends PlushieMob {
    public static GameTexture gameTexture;
    public FairPlushie() {
        super("fair");
        this.texture = gameTexture;
    }
}
