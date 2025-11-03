package uet.project.arkanoid.ui;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.project.arkanoid.base.*;
import uet.project.arkanoid.game.*;
import uet.project.arkanoid.utils.Basis;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import uet.project.arkanoid.utils.MapLoader;

public class PausedMenuView implements View {

    private boolean showMessage = false;
    private Timeline messageTimeline;

    public void saveMessage() {
        showMessage = true;
        if (messageTimeline != null) {
            messageTimeline.stop();
        }
        messageTimeline = new Timeline(
            new KeyFrame(
                Duration.seconds(4),
                event -> {
                    showMessage = false;
                }
            )
        );
        messageTimeline.play();
    }

    private static final Rectangle CONTINUE_BUTTON = new Rectangle(
        new Point(Basis.CONTINUE_BTN_X + Basis.PAUSE_BTN_WIDTH / 2.0,
            Basis.CONTINUE_BTN_Y + Basis.PAUSE_BTN_HEIGHT / 2.0),
        new Vector2D(Basis.PAUSE_BTN_WIDTH, Basis.PAUSE_BTN_HEIGHT),
        0
    );

    private static final Rectangle MENU_BUTTON = new Rectangle(
        new Point(Basis.MENU_BTN_X + Basis.PAUSE_BTN_WIDTH / 2.0,
            Basis.MENU_BTN_Y + Basis.PAUSE_BTN_HEIGHT / 2.0),
        new Vector2D(Basis.PAUSE_BTN_WIDTH, Basis.PAUSE_BTN_HEIGHT),
        0
    );

    private static final Rectangle OPTIONS_BUTTON = new Rectangle(
        new Point(Basis.OPTIONS_BTN_X + Basis.PAUSE_BTN_WIDTH / 2.0,
            Basis.OPTIONS_BTN_Y + Basis.PAUSE_BTN_HEIGHT / 2.0),
        new Vector2D(Basis.PAUSE_BTN_WIDTH, Basis.PAUSE_BTN_HEIGHT),
        0
    );

    private static final Rectangle SAVEGAME_BUTTON = new Rectangle(
        new Point(Basis.SAVEGAME_BTN_X + Basis.PAUSE_BTN_WIDTH / 2.0,
            Basis.SAVEGAME_BTN_Y + Basis.PAUSE_BTN_HEIGHT / 2.0),
        new Vector2D(Basis.PAUSE_BTN_WIDTH, Basis.PAUSE_BTN_HEIGHT),
        0
    );

    public void onDraw(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

        gc.drawImage(Basis.PAUSE_MENU_PANEL,
            Basis.PAUSE_MENU_X,
            Basis.PAUSE_MENU_Y,
            Basis.PAUSE_MENU_WIDTH,
            Basis.PAUSE_MENU_HEIGHT);
        gc.drawImage(Basis.PAUSE_CONTINUE_BUTTON,
            Basis.CONTINUE_BTN_X,
            Basis.CONTINUE_BTN_Y,
            Basis.PAUSE_BTN_WIDTH,
            Basis.PAUSE_BTN_HEIGHT);
        gc.drawImage(Basis.PAUSE_MENU_BUTTON,
            Basis.MENU_BTN_X,
            Basis.MENU_BTN_Y,
            Basis.PAUSE_BTN_WIDTH,
            Basis.PAUSE_BTN_HEIGHT);
        gc.drawImage(Basis.PAUSE_OPTIONS_BUTTON,
            Basis.OPTIONS_BTN_X,
            Basis.OPTIONS_BTN_Y,
            Basis.PAUSE_BTN_WIDTH,
            Basis.PAUSE_BTN_HEIGHT);
        gc.drawImage(Basis.PAUSE_SAVEGAME_BUTTON,
            Basis.SAVEGAME_BTN_X,
            Basis.SAVEGAME_BTN_Y,
            Basis.PAUSE_BTN_WIDTH,
            Basis.PAUSE_BTN_HEIGHT);

        if (showMessage) {
            gc.setFill(Color.LIMEGREEN);
            gc.setFont(new Font("Arial", 24));
            double textX = Basis.SAVEGAME_BTN_X + Basis.PAUSE_BTN_WIDTH + 15;
            double textY = Basis.SAVEGAME_BTN_Y + 35;
            gc.fillText("Đã Lưu!", textX, textY);
        }
    }

    // A new thread for saving the game
    public GameState handleClick(double mouseX, double mouseY, GameSetup stage) {
        Point click = new Point(mouseX, mouseY);

        if (CONTINUE_BUTTON.contains(click)) {
            return GameState.PLAYING;
        } else if (MENU_BUTTON.contains(click)) {
            return GameState.MENU;
        } else if (OPTIONS_BUTTON.contains(click)) {
            return GameState.SETTING;
        } else if (SAVEGAME_BUTTON.contains(click)) {
            new Thread(() -> {
                try {
                    Level levelToSave = stage.getCurrentLevel();
                    Saves saveSlot = MapLoader.levelSave(levelToSave);
                    MapLoader.saveGame(saveSlot, stage);

                    Platform.runLater(() -> saveMessage());
                } catch (IllegalArgumentException e) {
                    System.err.println("Error while saving: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "SaveGameThread").start();

            return GameState.PAUSED; // stays in paused menu while saving
        }

        return GameState.PAUSED;
    }
}

