package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.project.arkanoid.utils.Basis;

public class PausedMenuView {
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
    }
}

