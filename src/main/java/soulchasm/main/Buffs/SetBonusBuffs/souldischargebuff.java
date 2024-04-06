package soulchasm.main.Buffs.SetBonusBuffs;

import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.HumanDrawBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.level.maps.light.GameLight;

public class souldischargebuff extends Buff implements HumanDrawBuff {

    public souldischargebuff() {
        this.canCancel = false;
        this.isVisible = true;
        this.shouldSave = false;
        this.isImportant =  true;
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        buff.setModifier(BuffModifiers.SPEED, 1.0F);
        buff.setModifier(BuffModifiers.ATTACK_SPEED, 0.8F);
        buff.getGndData().setBoolean("tryDebuffApply", false);
    }

    public void addHumanDraw(ActiveBuff buff, HumanDrawOptions drawOptions) {
        float alpha = GameUtils.getAnimFloatContinuous(buff.getDurationLeft(), 1500);
        GameLight gameLight = new GameLight(125);
        gameLight.getGLColorSetter(0.1F, 0.1F, 1.0F, 1.0F);
        drawOptions.light(gameLight);
        drawOptions.alpha(alpha);
    }

    private void applyNextBuff(ActiveBuff buff){
        if(buff.getDurationLeft() <= 40 && !buff.getGndData().getBoolean("tryDebuffApply") && GameRandom.globalRandom.getChance(0.5)){
            buff.getGndData().setBoolean("tryDebuffApply", true);
            buff.owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.getBuff("souldischargesicknessdebuff"), buff.owner, 25F, null), false);
        }
    }

    public void clientTick(ActiveBuff buff) {
        super.clientTick(buff);
        applyNextBuff(buff);
    }

    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
        applyNextBuff(buff);
    }
}
