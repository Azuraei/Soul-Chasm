package soulchasm.main.plushies.characters;

import necesse.gfx.gameTexture.GameTexture;
import soulchasm.main.plushies.PlushieMob;

public class DevPlushie extends PlushieMob {
    public static GameTexture gameTexture;
    public DevPlushie() {
        super("dev");
        this.texture = gameTexture;
    }
}
