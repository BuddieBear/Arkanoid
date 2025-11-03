package uet.project.arkanoid.ui;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.project.arkanoid.base.*;
import uet.project.arkanoid.game.*;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.utils.MapLoader;

public class LoadScreenView implements View {

    private static final Rectangle LEVEL_1_HITBOX = new Rectangle(
        new Point(Basis.LEVEL_1_BTN_X + Basis.LEVEL_BTN_WIDTH / 2.0,
            Basis.LEVEL_1_BTN_Y + Basis.LEVEL_BTN_HEIGHT / 2.0),
        new Vector2D(Basis.LEVEL_BTN_WIDTH, Basis.LEVEL_BTN_HEIGHT),
        0
    );

    private static final Rectangle LEVEL_2_HITBOX = new Rectangle(
        new Point(Basis.LEVEL_2_BTN_X + Basis.LEVEL_BTN_WIDTH / 2.0,
            Basis.LEVEL_2_BTN_Y + Basis.LEVEL_BTN_HEIGHT / 2.0),
        new Vector2D(Basis.LEVEL_BTN_WIDTH, Basis.LEVEL_BTN_HEIGHT),
        0
    );

    private static final Rectangle LEVEL_3_HITBOX = new Rectangle(
        new Point(Basis.LEVEL_3_BTN_X + Basis.LEVEL_BTN_WIDTH / 2.0,
            Basis.LEVEL_3_BTN_Y + Basis.LEVEL_BTN_HEIGHT / 2.0),
        new Vector2D(Basis.LEVEL_BTN_WIDTH, Basis.LEVEL_BTN_HEIGHT),
        0
    );

    public void onDraw(GraphicsContext gc) {
        gc.drawImage(Basis.MENU, 0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

        gc.drawImage(Basis.LEVEL_1_BUTTON,
            Basis.LEVEL_1_BTN_X,
            Basis.LEVEL_1_BTN_Y,
            Basis.LEVEL_BTN_WIDTH,
            Basis.LEVEL_BTN_HEIGHT);

        if (MapLoader.isSaveFile(Saves.SLOT_1) == false) {
            gc.setFill(Color.rgb(0, 0, 0, 0.6));
            gc.fillRect(Basis.LEVEL_1_BTN_X,
                Basis.LEVEL_1_BTN_Y,
                Basis.LEVEL_BTN_WIDTH,
                Basis.LEVEL_BTN_HEIGHT);
        }

        gc.drawImage(Basis.LEVEL_2_BUTTON,
            Basis.LEVEL_2_BTN_X,
            Basis.LEVEL_2_BTN_Y,
            Basis.LEVEL_BTN_WIDTH,
            Basis.LEVEL_BTN_HEIGHT);

        if (MapLoader.isSaveFile(Saves.SLOT_2) == false) {
            gc.setFill(Color.rgb(0, 0, 0, 0.6));
            gc.fillRect(Basis.LEVEL_2_BTN_X,
                Basis.LEVEL_2_BTN_Y,
                Basis.LEVEL_BTN_WIDTH,
                Basis.LEVEL_BTN_HEIGHT);
        }

        gc.drawImage(Basis.LEVEL_3_BUTTON,
            Basis.LEVEL_3_BTN_X,
            Basis.LEVEL_3_BTN_Y,
            Basis.LEVEL_BTN_WIDTH,
            Basis.LEVEL_BTN_HEIGHT);

        if (MapLoader.isSaveFile(Saves.SLOT_3) == false) {
            gc.setFill(Color.rgb(0, 0, 0, 0.6));
            gc.fillRect(Basis.LEVEL_3_BTN_X,
                Basis.LEVEL_3_BTN_Y,
                Basis.LEVEL_BTN_WIDTH,
                Basis.LEVEL_BTN_HEIGHT);
        }

        gc.drawImage(Basis.BACK_BUTTON, Basis.BACK_X, Basis.BACK_Y, Basis.BACK_W, Basis.BACK_H);
    }

    public static Level getClickLevel(double mouseX, double mouseY) {
        Point clickPoint = new Point(mouseX, mouseY);

        if (LEVEL_1_HITBOX.contains(clickPoint)) {
            return Level.STAGE_1;
        } else if (LEVEL_2_HITBOX.contains(clickPoint)) {
            return Level.STAGE_2;
        } else if (LEVEL_3_HITBOX.contains(clickPoint)) {
            return Level.STAGE_3;
        }
        return null;
    }

    // A new thread for loading the game
    public GameState handleClick(double mouseX, double mouseY, GameSetup stage) {
        Level clickedLevel = LoadScreenView.getClickLevel(mouseX, mouseY);
        if (clickedLevel != null) {
            try {
                Saves slotToLoad = MapLoader.levelSave(clickedLevel);
                if (MapLoader.isSaveFile(slotToLoad)) {
                    new Thread(() -> {
                        stage.clearLevel();
                        MapLoader.loadGame(slotToLoad, stage);
                        Platform.runLater(() -> stage.setCurrentState(GameState.PLAYING));
                    }, "SaveLoaderThread").start();
                    return GameState.PAUSED;
                }
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }

        if (mouseX >= Basis.BACK_X && mouseX <= Basis.BACK_X + Basis.BACK_W &&
            mouseY >= Basis.BACK_Y && mouseY <= Basis.BACK_Y + Basis.BACK_H) {
            return GameState.MENU;
        }

        return GameState.LOAD_GAME;
    }
}