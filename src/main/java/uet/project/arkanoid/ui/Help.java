package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.utils.Basis;

public class Help implements View {

  public void onDraw(GraphicsContext gc) {
    gc.setFill(Color.BURLYWOOD);
    gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);
    gc.drawImage(Basis.BACK_BUTTON, Basis.BACK_X, Basis.BACK_Y, Basis.BACK_W, Basis.BACK_H);

    gc.setFill(Color.BLACK);
    gc.setFont(new Font("Arial", 30));
    gc.fillText("* HƯỚNG DẪN CHƠI:\n", 100, 150);
    gc.fillText("- Bấm \"A\" và \"D\" để di chuyển paddle.", 150, 200);
    gc.fillText("- Bấm \"SPACE\" để bắn bóng.", 150, 250);
    gc.fillText("- Bấm \"R\" để reset bóng về vị trí paddle.", 150, 300);
    gc.fillText("- Bấm \"ESC\" để pause game.", 150, 350);
    gc.fillText("* TÍNH NĂNG TRONG GAME:\n", 100, 400);
    gc.fillText("- Bấm \"S\" để bật tính năng auto-move paddle.", 150, 450);
    gc.fillText("- Click chuột để bắn đạn khi đủ 20 điểm.", 150, 500);

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
