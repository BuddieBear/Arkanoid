package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.utils.Basis;

public class Help implements View{
    public void onDraw(GraphicsContext gc) {
        gc.setFill(Color.BURLYWOOD);
        gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);
        gc.drawImage(Basis.BACK_BUTTON, Basis.BACK_X, Basis.BACK_Y, Basis.BACK_W, Basis.BACK_H);
    }

    @Override
    public GameState handleClick(double mouseX, double mouseY, GameSetup stage) {
        if (mouseX >= Basis.BACK_X && mouseX <= Basis.BACK_X + Basis.BACK_W 
        && mouseY >= Basis.BACK_Y && mouseY <= Basis.BACK_Y + Basis.BACK_H) {
            return GameState.INSTRUCTION;
        }
        return GameState.HELP;
    }


}
