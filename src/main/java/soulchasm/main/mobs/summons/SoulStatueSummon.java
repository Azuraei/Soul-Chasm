package soulchasm.main.mobs.summons;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.EmptyAINode;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.objectEntity.interfaces.OEVicinityBuff;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.main.mobs.hostile.ChasmWarriorStatue;

import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

public class SoulStatueSummon extends AttackingFollowingMob implements OEVicinityBuff {
    public static LootTable lootTable;
    public static GameTexture texture;
    public static GameTexture texture_ring;

    public SoulStatueSummon() {
        super(1);
        this.setSpeed(0.0F);
        this.setFriction(3.0F);
        this.setKnockbackModifier(0.0F);
        this.collision = new Rectangle(-18, -15, 36, 30);
        this.hitBox = new Rectangle(-18, -15, 36, 36);
        this.selectBox = new Rectangle(-20, -18, 40, 36);
    }

    public void init() {
        super.init();
        EmptyAINode<SoulStatueSummon> mobAI = new EmptyAINode<SoulStatueSummon>() {};
        this.ai = new BehaviourTreeAI<>(this, mobAI);
    }

    public Buff[] getBuffs() {
        return new Buff[]{BuffRegistry.getBuff("soulstatuebuff")};
    }

    public int getBuffRange() {
        return 250;
    }

    public boolean shouldBuffPlayers() {
        return true;
    }

    public boolean shouldBuffMobs() {
        return true;
    }

    public Predicate<Mob> buffMobsFilter() {
        return (m) -> m.isHuman && !m.isSummoned && !m.isHostile;
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    public void playDeathSound() {
        SoundManager.playSound(GameResources.fadedeath3, SoundEffect.effect(this).volume(0.2F).pitch(1.1F));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for (int i = 0; i < 8; i++) {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), ChasmWarriorStatue.texture, GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 20f, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void applyBuffs(Mob mob) {
        Buff[] var2 = this.getBuffs();
        for (Buff buff : var2) {
            if (buff != null) {
                ActiveBuff ab = new ActiveBuff(buff, mob, 100, this);
                mob.buffManager.addBuff(ab, false);
            }
        }
    }

    public void tickVicinityBuff(Mob mob) {
        Level level = mob.getLevel();
        int posX = (int) mob.x;
        int posY = (int) mob.y;
        this.tickVicinityBuff(level, posX, posY);
    }

    public void clientTick() {
        super.clientTick();
        this.tickVicinityBuff(this);
    }

    public void serverTick() {
        super.serverTick();
        this.tickVicinityBuff(this);
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        float alpha = GameUtils.getAnimFloatContinuous(level.lastWorldTime, 4000);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 60;
        DrawOptions body = texture.initDraw().sprite(0, 0, 64, 64).light(light.minLevelCopy(80)).pos(drawX, drawY);
        DrawOptions ring = texture_ring.initDraw().sprite(0, 0, 512, 512).alpha(0.2F + 0.3F * alpha).light(light.minLevelCopy(Math.min(light.getLevel() + 100.0F, 150.0F))).pos(camera.getDrawX(x) - 256, camera.getDrawY(y) - 256);
        tileList.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                ring.draw();
            }
        });
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                body.draw();
            }
        });
    }

    @Override
    public Mob getFirstAttackOwner() {
        return this;
    }
}
