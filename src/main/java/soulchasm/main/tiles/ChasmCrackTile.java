package soulchasm.main.tiles;

import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.gameTile.TerrainSplatterTile;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.Arrays;

import static necesse.level.gameTile.LiquidTile.LAVA_ATTACKER;
import static soulchasm.SoulChasm.chasmMagmaMapColor;

public class ChasmCrackTile extends TerrainSplatterTile {
    private final GameRandom drawRandom;
    private GameTextureSection particleSection;

    public ChasmCrackTile() {
        super(false, "chasmcracktile");
        this.mapColor = chasmMagmaMapColor;
        this.canBeMined = true;
        this.drawRandom = new GameRandom();
        this.tileHealth = 100;
    }

    public int getLightLevel() {
        return 50;
    }

    public GameLight getLight(Level level) {
        return level.lightManager.newLight(240.0F, 0.3F, (float)this.getLightLevel());
    }

    @Override
    public String canPlace(Level level, int x, int y, boolean byPlayer) {
        String canPlace = super.canPlace(level, x, y, byPlayer);
        if (canPlace != null) {
            return canPlace;
        } else {
            return Arrays.stream(level.getAdjacentObjects(x, y)).anyMatch((o) -> o.isWall || o.isRock) ? "objectadjacent" : null;
        }
    }

    @Override
    public void placeTile(Level level, int x, int y, boolean byPlayer) {
        super.placeTile(level, x, y, byPlayer);
        if (level.isLoadingComplete()) {
            for(int i = -1; i <= 1; ++i) {
                int tileX = x + i;

                for(int j = -1; j <= 1; ++j) {
                    int tileY = y + j;
                    level.getTile(tileX, tileY).tickValid(level, tileX, tileY, false);
                }
            }
        }
    }

    protected void loadTextures() {
        super.loadTextures();
        GameTexture particle = GameTexture.fromFile("particles/soulfiresparks");
        particleSection = GameResources.particlesTextureGenerator.addTexture(particle);
    }

    public double getPathCost(Level level, int tileX, int tileY, Mob mob) {
        return !mob.isLavaImmune() ? 1000.0 : super.getPathCost(level, tileX, tileY, mob);
    }

    @Override
    public LootTable getLootTable(Level level, int tileX, int tileY) {
        return new LootTable(new LootItem("chasmrockitem", 1), new ChanceLootItem(0.1F,"soulcore"));
    }

    public void tick(Mob mob, Level level, int x, int y) {
        if (mob.canLevelInteract() && !mob.isFlying() && level.isServer() && mob.canTakeDamage() && !mob.isLavaImmune() && !mob.isOnGenericCooldown("lavadmg")) {
            int maxHealth = mob.getMaxHealth();
            float dmg = Math.max((float)Math.pow(maxHealth, 0.5) + (float)maxHealth / 20.0F, 20.0F);
            dmg *= mob.buffManager.getModifier(BuffModifiers.FIRE_DAMAGE);
            if (dmg != 0.0F) {
                mob.isServerHit(new GameDamage(DamageTypeRegistry.TRUE, dmg), 0.0F, 0.0F, 0.0F, LAVA_ATTACKER);
            }
            mob.startGenericCooldown("lavadmg", 500);
            mob.addBuff(new ActiveBuff(BuffRegistry.getBuffID("soulfirebuff"), mob, 5.0F, null), true);
        }
    }

    @Override
    public int getDestroyedTile() {
        return TileRegistry.getTileID("chasmrocktile");
    }

    public void tickEffect(Level level, int x, int y) {
        super.tickEffect(level, x, y);
        if (GameRandom.globalRandom.getChance(0.025F) && !level.getObject(x, y).drawsFullTile() && level.getLightLevel(x, y).getLevel() > 0.0F) {
            int posX = x * 32 + GameRandom.globalRandom.nextInt(32);
            int posY = y * 32 + GameRandom.globalRandom.nextInt(32);
            boolean mirror = GameRandom.globalRandom.nextBoolean();
            level.entityManager.addParticle((float)posX, (float)(posY + 30), Particle.GType.COSMETIC).sprite(particleSection.sprite(GameRandom.globalRandom.nextInt(6), 0, 20, 20)).givesLight(30).fadesAlpha(0.4F, 0.4F).size((options, lifeTime, timeAlive, lifePercent) -> {
            }).height(30.0F).movesConstant(GameRandom.globalRandom.getFloatBetween(0.3F, 0.8F) * GameRandom.globalRandom.getOneOf(1.0F, -1.0F), GameRandom.globalRandom.getFloatBetween(-1.0F, -4.0F)).sizeFades(15, 25).modify((options, lifeTime, timeAlive, lifePercent) -> {
                options.mirror(mirror, false);
            }).lifeTime(4000);
        }
    }

    public Point getTerrainSprite(GameTextureSection terrainTexture, Level level, int tileX, int tileY) {
        int tile;
        synchronized(this.drawRandom) {
            tile = this.drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(terrainTexture.getHeight() / 32);
        }
        return new Point(0, tile);
    }

    public int getTerrainPriority() {
        return 1;
    }
}
