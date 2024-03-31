package soulchasm.main.Mobs.Summon;

import necesse.engine.Screen;
import necesse.engine.sound.SoundEffect;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.CooldownAttackTargetAINode;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.main.Misc.Others.playerflyingfollowershooterAI;
import soulchasm.main.Projectiles.soulhomingprojectile;

import java.awt.*;
import java.util.List;

public class smallsoulsummon extends FlyingAttackingFollowingMob {
    public static GameTexture texture;
    public smallsoulsummon() {
        super(10);
        this.accelerationMod = 1.0F;
        this.decelerationMod = 2.0F;
        this.moveAccuracy = 5;
        this.setSpeed(160.0F);
        this.setFriction(1.0F);
        this.collision = new Rectangle(-18, -15, 36, 30);
        this.hitBox = new Rectangle(-18, -15, 36, 36);
        this.selectBox = new Rectangle(-20, -18, 40, 36);
        this.isSummoned = true;
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI(this, new playerflyingfollowershooterAI(576, CooldownAttackTargetAINode.CooldownTimer.TICK, 600, 500, 600, 5) {
            protected boolean shootAtTarget(Mob mob, Mob target) {
                float angle = 15;
                for (int i = -1; i <= 1; ++i) {
                    soulhomingprojectile entity = new soulhomingprojectile(mob.getLevel(), mob, mob.x, mob.y + 20, target.x, target.y, 150, 1200, smallsoulsummon.this.damage, 75);
                    entity.setAngle(entity.getAngle() + angle * i);
                    entity.piercing = 0;
                    mob.getLevel().entityManager.projectiles.add(entity);
                }
                return true;
            }
        }, new FlyingAIMover());
    }

    public boolean canBePushed(Mob other) {return false;}
    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for(int i = 0; i < 30; ++i) {
            this.getLevel().entityManager.addParticle(this.x, this.y, Particle.GType.IMPORTANT_COSMETIC).movesConstant((float)(GameRandom.globalRandom.getIntBetween(5, 20) * (GameRandom.globalRandom.nextBoolean() ? -1 : 1)), (float)(GameRandom.globalRandom.getIntBetween(5, 20) * (GameRandom.globalRandom.nextBoolean() ? -1 : 1))).color(new Color(0, 170, 242));
        }
    }
    protected void playDeathSound() {
        Screen.playSound(GameResources.fadedeath3, SoundEffect.effect(this).volume(0.2F).pitch(1.1F));
    }


    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int bobbing = (int)(GameUtils.getBobbing(level.getWorldEntity().getTime(), 1000) * 5.0F);
        int anim = Math.abs(GameUtils.getAnim(level.getWorldEntity().getTime(), 4, 1000) - 3);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) + bobbing - 26;
        DrawOptions body = texture.initDraw().sprite(anim, 0, 32, 46).light(light.minLevelCopy(10)).pos(drawX, drawY).alpha(0.7F);
        topList.add((tm) -> {
            body.draw();
        });
    }

}
