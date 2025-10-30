package uet.project.arkanoid.ui;

import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.utils.Basis;

public class MenuView {
    public void onDraw(GraphicsContext gc) {
        // Menu
        gc.drawImage(Basis.MENU, 0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

        // play button
        gc.drawImage(Basis.PLAY_BUTTON, Basis.PLAY_X, Basis.PLAY_Y, Basis.PLAY_W, Basis.PLAY_H);

        // option button
        gc.drawImage(Basis.OPTION_BUTTON, Basis.INSTRUCTION_X, Basis.INSTRUCTION_Y, Basis.INSTRUCTION_W, Basis.INSTRUCTION_H);

        // setting button
        gc.drawImage(Basis.SETTING_BUTTON, Basis.SETTING_X, Basis.SETTING_Y, Basis.SETTING_W, Basis.SETTING_H);
    }
}
