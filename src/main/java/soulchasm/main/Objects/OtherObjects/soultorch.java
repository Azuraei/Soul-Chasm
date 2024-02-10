package soulchasm.main.Objects.OtherObjects;

import necesse.entity.particle.ParticleOption;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.TorchObject;
import necesse.level.maps.Level;

import java.awt.*;

public class soultorch extends TorchObject {
    public soultorch() {
        this.flameHue = 240F;
        this.smokeHue = ParticleOption.defaultSmokeHue;
        this.mapColor = new Color(0x00A7FF);
        this.lightLevel = 120;
        this.lightHue = 240.0F;
        this.lightSat = 0.3F;

    }
    public void loadTextures() {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/soultorch");
    }
    public int getLightLevel(Level level, int x, int y) {
        return this.isActive(level, x, y) ? this.lightLevel : 0;
    }
}
