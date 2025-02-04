package soulchasm.main.Objects.Plushies;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.entity.objectEntity.interfaces.OEUsers;
import necesse.level.maps.Level;

import java.awt.*;

public class PlushieObjectEntity extends ObjectEntity {
    public static double squishDuration = 1000;
    public double time;
    public double duration;
    public float test = 1;

    public PlushieObjectEntity(Level level, int x, int y) {
        super(level, "plushieobjectentity", x, y);
    }

    public void init() {
        super.init();
    }

    public void clientTick() {
        super.clientTick();
        test = GameRandom.globalRandom.getFloatBetween(0, 1);
    }

    public void serverTick() {
        super.serverTick();
    }

    public void use() {
        if (isClient()){
            System.out.println("sex");
        }
        time = this.getWorldTime();
        duration = time + squishDuration;
    }
}
