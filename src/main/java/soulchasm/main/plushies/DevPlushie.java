package soulchasm.main.plushies;

import necesse.gfx.gameTexture.GameTexture;

public class DevPlushie extends PlushieMob {
    public static GameTexture gameTexture;
    public DevPlushie() {
        super("dev");
        this.texture = gameTexture;
    }
}
