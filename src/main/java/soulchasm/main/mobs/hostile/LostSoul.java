package soulchasm.main.mobs.hostile;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionPlayerChaserWandererAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.hostile.FlyingHostileMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class LostSoul extends FlyingHostileMob {
    public static LootTable lootTable = new LootTable();
    public static GameTexture texture;

    public LostSoul() {
        super(400);
        this.setSpeed(50.0F);
        this.setFriction(0.8F);
        this.setKnockbackModifier(0.2F);
        this.setArmor(40);
        this.moveAccuracy = 10;
        this.collision = new Rectangle(-12, -12, 24, 24);
        this.hitBox = new Rectangle(-16, -16, 32, 32);
        this.selectBox = new Rectangle(-18, -40, 36, 54);
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI(this, new CollisionPlayerChaserWandererAI(null, 448, new GameDamage(65.0F), 100, 40000), new FlyingAIMover());
    }

    @Override
    public boolean isLavaImmune() {
        return true;
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for(int i = 0; i < 30; ++i) {
            this.getLevel().entityManager.addParticle(this.x, this.y, Particle.GType.IMPORTANT_COSMETIC).movesConstant((float)(GameRandom.globalRandom.getIntBetween(5, 20) * (GameRandom.globalRandom.nextBoolean() ? -1 : 1)), (float)(GameRandom.globalRandom.getIntBetween(5, 20) * (GameRandom.globalRandom.nextBoolean() ? -1 : 1))).color(new Color(0, 228, 255));
        }
    }

    public void playDeathSound() {
        this.playHitSound();
    }

    public void playHitSound() {
        float pitch = GameRandom.globalRandom.getOneOf(0.95F, 1.0F, 1.05F);
        SoundManager.playSound(GameResources.swoosh, SoundEffect.effect(this).pitch(pitch).volume(0.12F));
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int bobbing = (int)(GameUtils.getBobbing(level.getWorldEntity().getTime(), 1000) * 5.0F);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 48 + bobbing;
        int anim = Math.abs(GameUtils.getAnim(level.getWorldEntity().getTime(), 4, 1000) - 3);
        DrawOptions body = texture.initDraw().sprite(0, anim, 64).mirror(this.moveX < 0.0F, false).alpha(0.7F).light(light).pos(drawX, drawY);
        int minLight = 100;
        DrawOptions eyes = texture.initDraw().sprite(1, anim, 64).mirror(this.moveX < 0.0F, false).alpha(0.7F).light(light.minLevelCopy((float)minLight)).pos(drawX, drawY);
        this.addShadowDrawables(topList, level, x, y, light, camera);
        topList.add((tm) -> {
            body.draw();
            eyes.draw();
        });
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera) {
        GameTexture shadowTexture = MobRegistry.Textures.human_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2 + 4;
        return shadowTexture.initDraw().sprite(0, 0, res).light(light).pos(drawX, drawY);
    }
}
