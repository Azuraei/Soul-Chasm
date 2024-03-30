package soulchasm.main.Buffs;

import necesse.engine.util.GameMath;
import necesse.engine.util.GameUtils;
import necesse.engine.util.RayLinkedList;
import necesse.entity.ParticleBeamHandler;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameSprite;
import necesse.level.maps.LevelObjectHit;

import java.awt.*;
import java.awt.geom.Point2D;

public class idolshieldbuff extends Buff {
    private ParticleBeamHandler beamHandler;

    public idolshieldbuff() {
        this.isVisible = true;
        this.isImportant = true;
    }

    private void castLaser(ActiveBuff buff){
        Mob owner = buff.owner;
        Mob attacker = buff.getAttacker().getAttackOwner();
        if(owner != null && attacker != null){
            float rayDist = buff.owner.getDistance(attacker.x, attacker.y);
            Point2D.Float dir = GameMath.normalize(attacker.x - owner.x, attacker.y - owner.y);
            RayLinkedList<LevelObjectHit> rays = GameUtils.castRay(buff.owner.getLevel(), buff.owner.x, buff.owner.y, dir.x, dir.y, rayDist, 0, null);
            if(owner.getLevel().isClient()){
                this.updateTrail(rays, buff.owner.getLevel().tickManager().getDelta(), owner);
            }
        }
        if (this.beamHandler != null) {
            this.beamHandler.dispose();
        }
    }

    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        castLaser(buff);
    }

    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        castLaser(buff);
    }

    private void updateTrail(RayLinkedList<LevelObjectHit> rays, float delta, Mob owner) {
        Color color = new Color(0x008BFF);
        if (this.beamHandler == null) {
            this.beamHandler = (new ParticleBeamHandler(owner.getLevel())).color(color).thickness(80, 40).speed(100.0F).sprite(new GameSprite(GameResources.chains, 7, 0, 32));
        }
        this.beamHandler.update(rays, delta);
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 0.5F);
        buff.setModifier(BuffModifiers.KNOCKBACK_INCOMING_MOD, 0.0F);
    }
}
