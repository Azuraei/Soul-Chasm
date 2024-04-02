package soulchasm.main.Misc.Events.GroundEruptionEvent;

import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.util.List;

import static soulchasm.SoulChasm.eruption_shadow;

public class dragongrounderuptionparticle extends Particle {
    protected static final int[] frameTimes = new int[]{30, 30, 30, 30, 30, 60, 60, 60, 60, 60, 60, 120, 120, 120, 120};
    private final long spawnTime;
    private final long delay;
    private final boolean mirror;

    public dragongrounderuptionparticle(Level level, float x, float y, long spawnTime, long delay) {
        super(level, x, y, delay + 1500L);
        this.spawnTime = spawnTime;
        this.delay = delay;
        this.mirror = GameRandom.globalRandom.nextBoolean();
    }

    public void clientTick() {
        super.clientTick();
        long eventTime = this.getWorldEntity().getTime() - this.spawnTime;
        if (eventTime >= this.delay) {
            long frameTime = eventTime - this.delay;
            int frame = GameUtils.getAnim(frameTime, frameTimes);
            if (frame == -1) {
                this.remove();
            } else if (frame < 20) {
                this.getLevel().lightManager.refreshParticleLightFloat(this.x, this.y, 230.0F, 0.5F);
            }
        }

    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(this.getX() / 32, this.getY() / 32);
        int drawX = camera.getDrawX(this.x);
        int drawY = camera.getDrawY(this.y);
        long eventTime = this.getWorldEntity().getTime() - this.spawnTime;
        int frame;
        if (eventTime >= this.delay) {
            long frameTime = eventTime - this.delay;
            frame = GameUtils.getAnim(frameTime, frameTimes);
            if (frame == -1) {
                return;
            }
        } else {
            frame = 0;
        }
        frame = Math.max(frame - 5, 0);
        if (frame < 5) {
            float rotation = 0.0F;
            float sizeMod = 1.0F;
            if (frame == 0) {
                rotation = (float)((double)eventTime / 4.0);
                sizeMod += (float)(Math.sin((double)eventTime / 240.0) / 10.0);
            }
            TextureDrawOptions shadowOptions = eruption_shadow.initDraw().sprite(frame, 0, 128, 192).light(light.minLevelCopy(Math.min(light.getLevel() + 100.0F, 150.0F))).mirror(this.mirror, false).rotate(rotation, (int)(64.0F * sizeMod), (int)(96.0F * sizeMod)).size((int)(128.0F * sizeMod), (int)(192.0F * sizeMod)).posMiddle(drawX, drawY);
            tileList.add((tm) -> shadowOptions.draw());
        }
    }
}
