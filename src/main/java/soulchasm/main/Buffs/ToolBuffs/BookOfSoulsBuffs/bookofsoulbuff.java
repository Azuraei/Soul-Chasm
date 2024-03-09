package soulchasm.main.Buffs.ToolBuffs.BookOfSoulsBuffs;

import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.gfx.gameFont.FontManager;

public class bookofsoulbuff extends Buff {
    public bookofsoulbuff() {
    }
    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber) {
        this.shouldSave = false;
        this.overrideSync = true;
        this.canCancel = false;
        this.isImportant = true;
        this.isVisible = true;
    }

    public void drawIcon(int x, int y, ActiveBuff buff) {
        this.iconTexture.initDraw().size(32, 32).draw(x, y);
        GNDItemMap gndData = buff.getGndData();
        int energy = (int) (gndData.getFloat("energy", 0) * 100);
        String text = energy + "%";
        int width = FontManager.bit.getWidthCeil(text, durationFontOptions);
        FontManager.bit.drawString((float)(x + 16 - width / 2), (float)(y + 30), text, durationFontOptions);
    }
}
