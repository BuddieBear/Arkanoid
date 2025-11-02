package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.powerUpVariants.*;
import uet.project.arkanoid.utils.Basis;

public class gameUI {
    public static void render(GraphicsContext gc, GameSetup stage) {
        // Draw background and HUD
        gc.drawImage(Basis.GAME_BACKGROUND, 0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);
        gc.drawImage(Basis.OBJECTIVE_BOARD_TEXTURE, Basis.OBJECTIVE_BOARD_X, Basis.OBJECTIVE_BOARD_Y,
                Basis.OBJECTIVE_BOARD_WIDTH, Basis.OBJECTIVE_BOARD_HEIGHT);

        // Lives
        gc.setFill(Color.DARKGREEN);
        gc.setFont(new Font("Comic Sans MS", 28));
        gc.fillText(String.valueOf(stage.getLives()), 1175, 305);

        // Score
        gc.setFill(Color.YELLOW);
        gc.fillText(String.valueOf(stage.getScore()), 1175, 370);
    }
}