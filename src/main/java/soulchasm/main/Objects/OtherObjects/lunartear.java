package soulchasm.main.Objects.OtherObjects;

import necesse.engine.Screen;
import necesse.engine.Settings;
import necesse.engine.network.packet.PacketHitObject;
import necesse.engine.registries.TileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.gfx.GameResources;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.GrassObject;
import necesse.level.maps.Level;

public class lunartear extends GrassObject {
    public static double spreadChance = GameMath.getAverageSuccessRuns(700.0D);

    public lunartear() {
        super("lunartears", 3);
        this.weaveTime = 2000;
        this.weaveAmount = 0.15F;
        this.weaveHeight = 1.0F;
        this.randomXOffset = 8.0F;
        this.randomYOffset = 8.0F;
        this.drawDamage = false;
        this.objectHealth = 1;
        this.toolType = ToolType.PICKAXE;
        this.isLightTransparent = true;
        this.attackThrough = true;
        this.lightLevel = 40;
        this.lightHue = 240.0F;
        this.lightSat = 0.05F;
    }
    public void attackThrough(Level level, int x, int y, GameDamage damage, Attacker attacker) {
        level.getServer().network.sendToClientsWithTile(new PacketHitObject(level, x, y, this, damage), level, x, y);
    }

    public void attackThrough(Level level, int x, int y, GameDamage damage) {
        super.attackThrough(level, x, y, damage);
        level.makeGrassWeave(x, y, 1000, false);
    }
    @Override
    public void playDamageSound(Level level, int x, int y, boolean damageDone) {
        Screen.playSound(GameResources.grass, SoundEffect.effect((float)(x * 32 + 16), (float)(y * 32 + 16)));
    }
    @Override
    public void tick(Mob mob, Level level, int x, int y) {
        super.tick(mob, level, x, y);
        if (Settings.wavyGrass && mob.getFlyingHeight() < 10 && (mob.dx != 0.0F || mob.dy != 0.0F)) {
            level.makeGrassWeave(x, y, 1000, false);
        }
        if (level.isServer() && GameRandom.globalRandom.getChance(spreadChance)) {
            this.tickSpread(level, x, y, 2, 8, 1);
        }

    }

    @Override
    public String canPlace(Level level, int x, int y, int rotation) {
        String error = super.canPlace(level, x, y, rotation);
        if (error != null) {
            return error;
        } else {
            return level.getTileID(x, y) != TileRegistry.getTileID("soulcavegrass") ? "wrongtile" : null;
        }
    }
    @Override
    public boolean isValid(Level level, int x, int y) {
        return super.isValid(level, x, y) && level.getTileID(x, y) == TileRegistry.getTileID("soulcavegrass");
    }
}