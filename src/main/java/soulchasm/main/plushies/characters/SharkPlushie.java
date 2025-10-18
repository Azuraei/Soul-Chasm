package soulchasm.main.plushies.characters;

import necesse.gfx.gameTexture.GameTexture;
import soulchasm.main.plushies.PlushieMob;

public class SharkPlushie extends PlushieMob {
    public static GameTexture gameTexture;
    public SharkPlushie() {
        super("shark");
        this.texture = gameTexture;
    }
}
