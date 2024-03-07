package soulchasm.main.Mobs.Summon;

import necesse.engine.tickManager.TickManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.CooldownAttackTargetAINode;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import soulchasm.main.Misc.AI.PlayerFlyingFollowerShooterAI;
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
        this.damage = new GameDamage(35);
        this.isSummoned = true;
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI(this, new PlayerFlyingFollowerShooterAI(576, CooldownAttackTargetAINode.CooldownTimer.TICK, 600, 500, 600, 5) {
            @Override
            protected boolean shootAtTarget(Mob mob, Mob target) {
                float angle = 15;
                for (int i = -1; i <= 1; ++i) {
                    soulhomingprojectile entity = new soulhomingprojectile(mob.getLevel(), mob, mob.x, mob.y + 20, target.x, target.y, 150, 1200, smallsoulsummon.this.damage, 20);
                    entity.setAngle(entity.getAngle() + angle * i);
                    entity.piercing = 0;
                    mob.getLevel().entityManager.projectiles.add(entity);
                }
                return true;
            }
        }, new FlyingAIMover());
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        Point p = new Point(texture.getWidth() / 2, texture.getHeight() / 2);
        int drawX = camera.getDrawX(x) - p.x;
        int drawY = camera.getDrawY(y) - p.y;
        DrawOptions body = texture.initDraw().light(light.minLevelCopy(60)).pos(drawX, drawY).alpha(0.8F);
        topList.add((tm) -> {
            body.draw();
        });
    }
    @Override
    protected void playDeathSound() {
    }
}
