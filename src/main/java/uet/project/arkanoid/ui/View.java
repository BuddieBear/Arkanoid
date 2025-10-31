package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;

public interface View {
    void onDraw(GraphicsContext gc);
    GameState handleClick(double mouseX, double mouseY, GameSetup stage);
}
