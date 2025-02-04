package soulchasm.main.Objects.Plushies;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.network.NetworkClient;
import necesse.engine.network.client.Client;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.EmptyAINode;
import necesse.entity.mobs.friendly.FriendlyMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.inventory.lootTable.LootList;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.LevelMap;
import necesse.level.maps.light.GameLight;
import soulchasm.main.Mobs.Agressive.MeleeStatue;

import java.awt.*;
import java.util.List;

public class PlushieMob extends FriendlyMob {
    public static GameTexture texture;
    public String name;

    public PlushieMob(String name) {
        super(1);
        this.collision = new Rectangle(-16, -16 - 8, 32, 32);
        this.hitBox = new Rectangle(-16, -16 - 8, 32, 32);
        this.selectBox = new Rectangle(-16, -16 - 8, 32, 32);
        this.canDespawn = false;
        this.name = name;
        this.setTeam(-2);
    }

    public boolean isVisibleOnMap(Client client, LevelMap map) {
        return false;
    }

    public String getMapInteractTooltip() {
        return Localization.translate("ui", "plushie_tip");
    }

    public GameMessage getLocalization() {
        String message;
        message = Localization.translate("ui", name + "plushieitem") + " " + Localization.translate("ui", "plushie");
        return new StaticMessage(message);
    }

    public LootTable getLootTable() {
        return new LootTable(new LootItem(name+"item"));
    }

    public boolean canInteract(Mob mob) {
        return true;
    }

    public void interact(PlayerMob player) {
        super.interact(player);
        if (getLevel().isClient()){
            System.out.println("e");
        } else {
            System.out.println("a");
        }
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new EmptyAINode<PlushieMob>() {});;
    }

    public boolean canTakeDamage() {
        return true;
    }
    public boolean canBeTargeted(Mob attacker, NetworkClient attackerClient) {
        return false;
    }
    public boolean canPushMob(Mob other) {
        return false;
    }
    public boolean canBePushed(Mob other) {
        return true;
    }
    public boolean isHealthBarVisible() {
        return false;
    }
    protected void playDeathSound() {}
    protected void playHitSound() {}
    public void spawnDamageText(int dmg, int size, boolean isCrit) {}

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x, y);
        int drawX = camera.getDrawX(x);
        int drawY = camera.getDrawY(y);
        TextureDrawOptions options = texture.initDraw().sprite(0, 0, texture.getWidth(), texture.getHeight()).light(light).pos(drawX - texture.getWidth() / 2, drawY - texture.getHeight() + 8);
        topList.add((tm) -> options.draw());
    }
}
