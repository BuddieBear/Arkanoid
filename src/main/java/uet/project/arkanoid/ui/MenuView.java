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
        gc.drawImage(Basis.OPTION_BUTTON, Basis.OPTION_X, Basis.OPTION_Y, Basis.OPTION_W, Basis.OPTION_H);

        // setting button
        gc.drawImage(Basis.SETTING_BUTTON, Basis.SETTING_X, Basis.SETTING_Y, Basis.SETTING_W, Basis.SETTING_H);

        // load game button
        gc.drawImage(Basis.LOAD_GAME_BUTTON, Basis.LOAD_GAME_BTN_X, Basis.LOAD_GAME_BTN_Y, Basis.LOAD_GAME_BTN_W, Basis.LOAD_GAME_BTN_H);
    }
}
