package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Vector2D;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.utils.Basis;

public class GameOverView {

    // Button size
    private static final double BUTTON_WIDTH = 180;
    private static final double BUTTON_HEIGHT = 70;

    // Button positions
    private static final double CENTER_X = Basis.SCREEN_WIDTH / 2.0;
    private static final double MENU_Y = 400;
    private static final double REPLAY_Y = 500;
    private static final double EXIT_Y = 600;

    // Hitboxes
    private static final Rectangle MENU_BUTTON_HITBOX = new Rectangle(
            CENTER_X, MENU_Y + BUTTON_HEIGHT / 2.0,
            BUTTON_WIDTH, BUTTON_HEIGHT, 0
    );
    private static final Rectangle REPLAY_BUTTON_HITBOX = new Rectangle(
            CENTER_X, REPLAY_Y + BUTTON_HEIGHT / 2.0,
            BUTTON_WIDTH, BUTTON_HEIGHT, 0
    );
    private static final Rectangle EXIT_BUTTON_HITBOX = new Rectangle(
            CENTER_X, EXIT_Y + BUTTON_HEIGHT / 2.0,
            BUTTON_WIDTH, BUTTON_HEIGHT, 0
    );

    public static void OnDraw(GraphicsContext gc, GameSetup stage) {
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

        // Win or Lose visuals
        if (stage.gameWin()) {
            gc.drawImage(Basis.GAME_WIN_TEXT, Basis.SCREEN_WIDTH / 2.0 - 200, 150);
            gc.drawImage(Basis.GAME_WIN_TROPHY, Basis.SCREEN_WIDTH / 2.0 - 100, 250);
        } else {
            gc.drawImage(Basis.GAME_LOSE_TEXT, Basis.SCREEN_WIDTH / 2.0 - 200, 150);
            gc.drawImage(Basis.GAME_LOSE_HEART, Basis.SCREEN_WIDTH / 2.0 - 100, 250);
        }

        // Buttons
        gc.drawImage(Basis.GAME_OVER_MENU_BUTTON, CENTER_X - BUTTON_WIDTH / 2.0, MENU_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        gc.drawImage(Basis.GAME_OVER_REPLAY_BUTTON, CENTER_X - BUTTON_WIDTH / 2.0, REPLAY_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        gc.drawImage(Basis.GAME_OVER_EXIT_BUTTON, CENTER_X - BUTTON_WIDTH / 2.0, EXIT_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public static void handleClick(double mouseX, double mouseY, GameSetup stage, GameState currentState) {
        Point clickPoint = new Point(mouseX, mouseY);

        if (MENU_BUTTON_HITBOX.contains(clickPoint)) {
            currentState = GameState.MENU;
        } else if (REPLAY_BUTTON_HITBOX.contains(clickPoint)) {
            // stage.restartGame();
            // stage.setGameState(GameSetup.GameState.PLAYING);
        } else if (EXIT_BUTTON_HITBOX.contains(clickPoint)) {
            currentState = GameState.EXIT;
        }
    }
}