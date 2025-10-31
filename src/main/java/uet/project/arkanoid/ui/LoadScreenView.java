package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.game.Level;

public class LoadScreenView {
    public void onDraw(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

        gc.drawImage(Basis.LEVEL_1_BUTTON,
                Basis.LEVEL_1_BTN_X,
                Basis.LEVEL_1_BTN_Y,
                Basis.LEVEL_BTN_WIDTH,
                Basis.LEVEL_BTN_HEIGHT);
        gc.drawImage(Basis.LEVEL_2_BUTTON,
                Basis.LEVEL_2_BTN_X,
                Basis.LEVEL_2_BTN_Y,
                Basis.LEVEL_BTN_WIDTH,
                Basis.LEVEL_BTN_HEIGHT);
        gc.drawImage(Basis.LEVEL_3_BUTTON,
                Basis.LEVEL_3_BTN_X,
                Basis.LEVEL_3_BTN_Y,
                Basis.LEVEL_BTN_WIDTH,
                Basis.LEVEL_BTN_HEIGHT);
    }

    public Level getClickLevel(double mouseX, double mouseY) {
        if (mouseX >= Basis.LEVEL_1_BTN_X && mouseX <= Basis.LEVEL_1_BTN_X + Basis.LEVEL_BTN_WIDTH
                && mouseY >= Basis.LEVEL_1_BTN_Y && mouseY <= Basis.LEVEL_1_BTN_Y + Basis.LEVEL_BTN_HEIGHT) {
            return Level.STAGE_1;
        } else if (mouseX >= Basis.LEVEL_2_BTN_X && mouseX <= Basis.LEVEL_2_BTN_X + Basis.LEVEL_BTN_WIDTH
                && mouseY >= Basis.LEVEL_2_BTN_Y && mouseY <= Basis.LEVEL_2_BTN_Y + Basis.LEVEL_BTN_HEIGHT) {
            return Level.STAGE_2;
        } else if (mouseX >= Basis.LEVEL_3_BTN_X && mouseX <= Basis.LEVEL_3_BTN_X + Basis.LEVEL_BTN_WIDTH
                && mouseY >= Basis.LEVEL_3_BTN_Y && mouseY <= Basis.LEVEL_3_BTN_Y + Basis.LEVEL_BTN_HEIGHT) {
            return Level.STAGE_3;
        } else return null;
    }
}