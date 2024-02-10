package soulchasm.main.Objects.BiomeEnviroment;

import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ParticleOption;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.LevelTileDrawOptions;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.gameTile.LiquidTile;
import necesse.level.maps.Level;

import java.awt.*;
import java.util.List;

public class meltedsouls extends LiquidTile {
    public GameTextureSection texture;
    protected final GameRandom drawRandom;

    public meltedsouls() {
        super(new Color(21, 193, 238));
        this.lightLevel = 100;
        this.lightHue = 220.0F;
        this.lightSat = 0.4F;
        this.drawRandom = new GameRandom();

    }

    protected void loadTextures() {
        super.loadTextures();
        this.texture = tileTextures.addTexture(GameTexture.fromFile("tiles/meltedsouls"));
    }

    @Override
    public Color getLiquidColor(Level level, int i, int i1) {
        return new Color(21, 193, 238);
    }

    public double getPathCost(Level level, int tileX, int tileY, Mob mob) {
        return !mob.isLavaImmune() ? 1000.0 : super.getPathCost(level, tileX, tileY, mob);
    }

    public float getItemSinkingRate(float currentSinking) {
        return TickManager.getTickDelta(60.0F);
    }

    public float getItemMaxSinking() {
        return 1.0F;
    }

    public void tick(Mob mob, Level level, int x, int y) {
        if (mob.canLevelInteract() && !mob.isFlying() && !mob.isWaterWalking() && level.inLiquid(mob.getX(), mob.getY()) && level.isServerLevel() && mob.canTakeDamage() && !mob.isLavaImmune() && !mob.isOnGenericCooldown("lavadmg")) {
            int maxHealth = mob.getMaxHealth();
            float dmg = Math.max((float)Math.pow(maxHealth, 0.5) + (float)maxHealth / 20.0F, 20.0F);
            dmg *= mob.buffManager.getModifier(BuffModifiers.FIRE_DAMAGE);
            if (dmg != 0.0F) {
                mob.isServerHit(new GameDamage(DamageTypeRegistry.TRUE, dmg), 0.0F, 0.0F, 0.0F, LAVA_ATTACKER);
            }
            mob.startGenericCooldown("lavadmg", 500);
            mob.addBuff(new ActiveBuff(BuffRegistry.getBuffID("soulfirebuff"), mob, 10.0F, (Attacker)null), true);
        }
    }

    public void tickEffect(Level level, int x, int y) {
        if (GameRandom.globalRandom.getChance(200) && level.getObjectID(x, y) == 0) {
            int spriteRes = 12;
            Color particleColor = GameUtils.getBrighterColor(this.getLiquidColor(level, x, y), 0.85F);
            level.entityManager.addParticle(ParticleOption.base((float)(x * 32 + GameRandom.globalRandom.nextInt(32 - spriteRes)), (float)(y * 32 + GameRandom.globalRandom.nextInt(32 - spriteRes))), Particle.GType.COSMETIC).lifeTime(1000).sprite((options, lifeTime, timeAlive, lifePercent) -> {
                int frames = GameResources.liquidBlobParticle.getWidth() / spriteRes;
                return options.add(GameResources.liquidBlobParticle.sprite((int)(lifePercent * (float)frames), 0, spriteRes));
            }).color(particleColor);
        }

    }

    protected void addLiquidTopDrawables(LevelTileDrawOptions list, List<LevelSortedDrawable> sortedList, Level level, int tileX, int tileY, GameCamera camera, TickManager tickManager) {
        boolean addBobbing;
        synchronized(this.drawRandom) {
            addBobbing = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).getChance(0.15F);
        }
        if (addBobbing) {
            int drawX = camera.getTileDrawX(tileX);
            int drawY = camera.getTileDrawY(tileY);
            int offset = this.getLiquidBobbing(level, tileX, tileY);
            int tile;
            synchronized(this.drawRandom) {
                tile = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).nextInt(this.texture.getHeight() / 32);
            }
            list.add(this.texture.sprite(0, tile, 32)).color(GameUtils.getBrighterColor(this.getLiquidColor(level, tileX, tileY), 0.85F)).pos(drawX, drawY + offset - 2);
        }
    }

    public int getLiquidBobbing(Level level, int tileX, int tileY) {
        return super.getLiquidBobbing(level, tileX, tileY) / 2;
    }
}
