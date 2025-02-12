package soulchasm.main.Plushies;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.NetworkClient;
import necesse.engine.network.client.Client;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.EmptyAINode;
import necesse.entity.mobs.friendly.FriendlyMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.LevelMap;
import necesse.level.maps.light.GameLight;
import soulchasm.SoulChasm;

import java.awt.*;
import java.util.List;

public class PlushieMob extends FriendlyMob {
    public GameTexture texture;
    public String name;
    private long timePressed;

    public PlushieMob(String name) {
        super(1);
        this.collision = new Rectangle(-16, -24, 32, 32);
        this.hitBox = new Rectangle(-16, -24, 32, 32);
        this.selectBox = new Rectangle(-16, -24, 32, 32);
        this.canDespawn = false;
        this.name = name;
    }

    @Override
    protected void addHoverTooltips(ListGameTooltips tooltips, boolean debug) {}

    public boolean isVisibleOnMap(Client client, LevelMap map) {
        return false;
    }

    public LootTable getLootTable() {
        return new LootTable(new LootItem(this.name + "plushieitem"));
    }

    public boolean canInteract(Mob mob) {
        return true;
    }

    public void interact(PlayerMob player) {
        super.interact(player);
        this.timePressed = player.getTime();
        if (player.getLevel().isClient()){
            float pitch = GameRandom.globalRandom.getFloatBetween(0.9F, 1.1F);
            SoundManager.playSound(SoulChasm.plushieSqueak, SoundEffect.effect(x, y).volume(0.2F).pitch(pitch));
        }
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new EmptyAINode<PlushieMob>() {});;
    }

    public boolean canTakeDamage() {
        return true;
    }
    public boolean canBeHit(Attacker attacker) {
        return true;
    }
    public boolean canBeTargeted(Mob attacker, NetworkClient attackerClient) {
        return attacker.isPlayer && !attacker.isSummoned;
    }
    public boolean canPushMob(Mob other) {
        return false;
    }
    public boolean canBePushed(Mob other) {
        return false;
    }
    public boolean isHealthBarVisible() {
        return false;
    }
    protected void playDeathSound() {}
    protected void playHitSound() {}
    public void spawnDamageText(int dmg, int size, boolean isCrit) {}

    public float getBouncyAnimation(float progress) {
        return (float)Math.abs((Math.cos(0 + Math.PI * progress)));
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x);
        int drawY = camera.getDrawY(y);

        long squishTime = 500L;

        long timePress = this.timePressed;
        long animTime = timePress + squishTime;
        long remainingTime = animTime - level.getTime();
        float progress;
        final float widthSize;
        final float heightSize;
        if (remainingTime>=0){
            progress = 1.0F - ((float)remainingTime / squishTime);
            float bouncePercent = this.getBouncyAnimation(progress);
            float bounceWidth = GameMath.lerp(bouncePercent, 1.0F, 0.4F);
            float bounceHeight = GameMath.lerp(bouncePercent, 1.0F, 0.6F);
            widthSize = GameMath.lerp(progress, bounceWidth, 1.0F);
            heightSize = GameMath.lerp(progress, bounceHeight, 1.0F);
        } else {
            widthSize = 1.0F;
            heightSize = 1.0F;
        }
        int wiggleWidth = (int)((float)this.texture.getWidth() * (1 + (1 - widthSize)));
        int wiggleHeight = (int)((float)this.texture.getHeight() * heightSize);
        int heightDiff = this.texture.getHeight() - wiggleHeight;

        TextureDrawOptions options = this.texture.initDraw().sprite(0, 0, this.texture.getWidth(), this.texture.getHeight()).size(wiggleWidth, wiggleHeight).light(light).pos(drawX - wiggleWidth / 2, drawY - this.texture.getHeight() + 8 + heightDiff);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
        this.addShadowDrawables(tileList, x, y, light, camera);
    }
}
