package soulchasm.main.Misc.AI;

import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.composites.SelectorAINode;
import necesse.entity.mobs.ai.behaviourTree.composites.SequenceAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.*;

public abstract class PlayerFlyingFollowerShooterAI<T extends Mob> extends SelectorAINode<T> {
    public PlayerFlyingFollowerShooterAI(int searchDistance, CooldownAttackTargetAINode.CooldownTimer cooldownTimer, int shootCooldown, int shootDistance, int teleportDistance, int stoppingDistance) {

        SequenceAINode<T> chaserSequence = new SequenceAINode();
        chaserSequence.addChild(new FollowerBaseSetterAINode());
        chaserSequence.addChild(new FollowerFocusTargetSetterAINode());
        final SummonTargetFinderAINode<T> targetFinder = new SummonTargetFinderAINode(searchDistance);
        chaserSequence.addChild(targetFinder);
        chaserSequence.addChild(new CooldownAttackTargetAINode<T>(cooldownTimer, shootCooldown, shootDistance) {
            public boolean attackTarget(T mob, Mob target) {
                return PlayerFlyingFollowerShooterAI.this.shootAtTarget(mob, target);
            }
        });
        this.addChild(chaserSequence);
        this.addChild(new PlayerFlyingFollowerAINode(teleportDistance, stoppingDistance));
    }
    protected abstract boolean shootAtTarget(T var1, Mob var2);
}
