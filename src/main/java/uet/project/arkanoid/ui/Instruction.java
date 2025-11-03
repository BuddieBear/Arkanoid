package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.utils.Basis;

public class Instruction implements View {

  public void onDraw(GraphicsContext gc) {
    gc.setFill(javafx.scene.paint.Color.BURLYWOOD);
    gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

    gc.drawImage(Basis.BACK_BUTTON, Basis.BACK_X, Basis.BACK_Y, Basis.BACK_W, Basis.BACK_H);
    gc.drawImage(Basis.HELP, Basis.HELP_X, Basis.HELP_Y, Basis.HELP_W, Basis.HELP_H);
    gc.drawImage(Basis.POWER_UP, Basis.POWER_UP_X, Basis.POWER_UP_Y, Basis.POWER_UP_W,
        Basis.POWER_UP_H);
    gc.drawImage(Basis.BRICK_VIEW, Basis.BRICK_VIEW_X, Basis.BRICK_VIEW_Y, Basis.BRICK_VIEW_W,
        Basis.BRICK_VIEW_H);
  }

  @Override
  public GameState handleClick(double mouseX, double mouseY, GameSetup stage) {
    if (mouseX >= Basis.HELP_X && mouseX <= Basis.HELP_X + Basis.HELP_W
        && mouseY >= Basis.HELP_Y && mouseY <= Basis.HELP_Y + Basis.HELP_H) {
      return GameState.HELP;
    } else if (mouseX >= Basis.POWER_UP_X && mouseX <= Basis.POWER_UP_X + Basis.POWER_UP_W
        && mouseY >= Basis.POWER_UP_Y && mouseY <= Basis.POWER_UP_Y + Basis.POWER_UP_H) {
      return GameState.POWER_UP;
    } else if (mouseX >= Basis.BACK_X && mouseX <= Basis.BACK_X + Basis.BACK_W
        && mouseY >= Basis.BACK_Y && mouseY <= Basis.BACK_Y + Basis.BACK_H) {
      return GameState.MENU;
    } else if (mouseX >= Basis.BRICK_VIEW_X && mouseX <= Basis.BRICK_VIEW_X + Basis.BRICK_VIEW_W
        && mouseY >= Basis.BRICK_VIEW_Y && mouseY <= Basis.BRICK_VIEW_H + Basis.BRICK_VIEW_Y) {
      return GameState.BRICK_VIEW;
    }
    return GameState.INSTRUCTION;
  }

}
