package soulchasm.main.Objects.OtherObjects;

import necesse.level.gameObject.TorchObject;
import necesse.level.maps.Level;

import java.awt.*;

public class soultorch extends TorchObject {
    public soultorch() {
        super("soultorch", new Color(0x00A7FF), 240F, 0.3F);
        this.flameHue = 240;
        this.lightLevel = 120;
    }
    public int getLightLevel(Level level, int x, int y) {
        return this.isActive(level, x, y) ? this.lightLevel : 0;
    }
}
