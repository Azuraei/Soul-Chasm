package soulchasm.main.Misc.CarColorInterface;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.client.Client;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.drawOptions.texture.TextureDrawOptionsEnd;
import necesse.gfx.fairType.FairType;
import necesse.gfx.forms.components.*;
import necesse.gfx.forms.presets.containerComponent.ContainerFormSwitcher;
import necesse.gfx.gameTexture.GameSprite;
import soulchasm.SoulChasm;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class CarColorContainerForm extends ContainerFormSwitcher<CarColorContainer> {

    public CarColorContainerForm(Client client, CarColorContainer container) {
        super(client, container);
        ArrayList<Color> colors = SoulChasm.carColors;
        Point2D central_point = new Point(0, 0);
        float angle_per_color = (float) 360.0 / colors.size();
        float circle_range = 100;
        for (int i = 0; i < colors.size(); i++){
            int index = i;
            final int pos_x = (int) (central_point.getX() + (circle_range * GameMath.cos(index * angle_per_color - 90)));
            final int pos_y = (int) (central_point.getY() + (circle_range * GameMath.sin(index * angle_per_color - 90)));

            GameSprite sprite_base = new GameSprite(SoulChasm.carColorContainerIcon, 0, 0, 64);

            final int button_size = 48;

            this.addComponent(new FormTextureButton(pos_x, pos_y, () -> sprite_base, button_size, button_size, FairType.TextAlign.CENTER, FairType.TextAlign.CENTER) {
                @Override
                public void draw(TickManager tickManager, PlayerMob perspective, Rectangle renderBox) {
                    super.draw(tickManager, perspective, renderBox);
                    TextureDrawOptionsEnd drawOptions2 = SoulChasm.carColorContainerIcon.initDraw().sprite(1, 0, 64).color(colors.get(index)).shrinkWidth(button_size, false).shrinkHeight(button_size, false);
                    drawOptions2.draw(this.getX() - button_size/2, this.getY() - button_size/2);
                }
            }).onClicked((formButtonFormInputEvent -> {
                container.getColorIndex.runAndSend(String.valueOf(index));
            }));
        }
    }

    @Override
    public boolean shouldOpenInventory() {
        return false;
    }

    @Override
    public boolean shouldShowToolbar() {
        return false;
    }

    @Override
    public void onContainerClosed() {
        super.onContainerClosed();
    }
}
