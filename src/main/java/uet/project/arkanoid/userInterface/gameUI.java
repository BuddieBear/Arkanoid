package uet.project.arkanoid.userInterface;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.Basis;

public class gameUI {

    public static void render(GraphicsContext gc) {
        gc.drawImage(Basis.GAME_BACKGROUND, 0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);
    }
}
