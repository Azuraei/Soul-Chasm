package soulchasm.main.tiles;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.level.gameTile.PathTiledTile;
import soulchasm.SoulChasm;

public class AsphaltTile extends PathTiledTile {
    public AsphaltTile() {
        super("asphalt", SoulChasm.asphaltMapColor);
        this.stackSize = 1000;
    }

    @Override
    public ModifierValue<Float> getSpeedModifier(Mob mob) {
        return mob.isFlying() ? super.getSpeedModifier(mob) : new ModifierValue(BuffModifiers.SPEED, 1.2F);
    }
}
