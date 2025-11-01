package uet.project.arkanoid.ui;

import java.util.Objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.base.*;

public class MenuView implements View {
    private static final Rectangle PLAY_BUTTON = new Rectangle(
            new Point(Basis.PLAY_X + Basis.PLAY_W / 2.0, Basis.PLAY_Y + Basis.PLAY_H / 2.0),
            new Vector2D(Basis.PLAY_W, Basis.PLAY_H),
            0
    );

    private static final Rectangle OPTION_BUTTON = new Rectangle(
            new Point(Basis.INSTRUCTION_X + Basis.INSTRUCTION_W / 2.0, Basis.INSTRUCTION_Y + Basis.INSTRUCTION_H / 2.0),
            new Vector2D(Basis.INSTRUCTION_W, Basis.INSTRUCTION_H),
            0
    );

    private static final Rectangle SETTING_BUTTON = new Rectangle(
            new Point(Basis.SETTING_X + Basis.SETTING_W / 2.0, Basis.SETTING_Y + Basis.SETTING_H / 2.0),
            new Vector2D(Basis.SETTING_W, Basis.SETTING_H),
            0
    );

    private static final Rectangle LOAD_GAME_BUTTON = new Rectangle(
            new Point(Basis.LOAD_GAME_BTN_X + Basis.LOAD_GAME_BTN_W / 2.0, Basis.LOAD_GAME_BTN_Y + Basis.LOAD_GAME_BTN_H / 2.0),
            new Vector2D(Basis.LOAD_GAME_BTN_W, Basis.LOAD_GAME_BTN_H),
            0
    );

    public GameState handleClick(double mouseX, double mouseY, GameSetup stage) {
        Point click = new Point(mouseX, mouseY);

        if (PLAY_BUTTON.contains(click)) {
            return GameState.LEVEL;
        } else if (SETTING_BUTTON.contains(click)) {
            return GameState.SETTING;
        } else if (OPTION_BUTTON.contains(click)) {
            return GameState.OPTION;
        } else if (LOAD_GAME_BUTTON.contains(click)) {
            return GameState.LOAD_GAME;
        }

        return GameState.MENU;
    }

    public void onDraw(GraphicsContext gc) {
        // Menu
        gc.drawImage(Basis.MENU, 0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

        // play button
        gc.drawImage(Basis.PLAY_BUTTON, Basis.PLAY_X, Basis.PLAY_Y, Basis.PLAY_W, Basis.PLAY_H);

        // option button
        gc.drawImage(Basis.OPTION_BUTTON, Basis.INSTRUCTION_X, Basis.INSTRUCTION_Y, Basis.INSTRUCTION_W, Basis.INSTRUCTION_H);

        // setting button
        gc.drawImage(Basis.SETTING_BUTTON, Basis.SETTING_X, Basis.SETTING_Y, Basis.SETTING_W, Basis.SETTING_H);

        // load game button
        gc.drawImage(Basis.LOAD_GAME_BUTTON, Basis.LOAD_GAME_BTN_X, Basis.LOAD_GAME_BTN_Y, Basis.LOAD_GAME_BTN_W, Basis.LOAD_GAME_BTN_H);
    }
}
