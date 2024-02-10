package soulchasm.main.Objects.OtherObjects;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.level.gameTile.PathTiledTile;

import java.awt.*;

public class asphalttile extends PathTiledTile {
    public asphalttile() {
        super("asphalttile", new Color(0x171717));
        this.stackSize = 1000;
    }

    @Override
    public ModifierValue<Float> getSpeedModifier(Mob mob) {
        return mob.isFlying() ? super.getSpeedModifier(mob) : new ModifierValue(BuffModifiers.SPEED, 1.2F);
    }
}
