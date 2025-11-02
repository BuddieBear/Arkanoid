package uet.project.arkanoid.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.utils.Basis;

public class Power_Up_View implements View{
    private int widthImage = 400;
    private int heightImage = 300;
    private int space = 50;
    private int scrollY = 0;
    private int contentHeight = 100 + 3 * heightImage + 2 * space;
    private int viewHeight = Basis.SCREEN_HEIGHT;
    private int x = (Basis.SCREEN_WIDTH - 2 * widthImage) / 3;

    public void onDraw(GraphicsContext gc) {
        gc.setFill(Color.BURLYWOOD);
        gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);
    }

    @Override
    public GameState handleClick(double mouseX, double mouseY, GameSetup stage) {
        if (mouseX >= Basis.BACK_X && mouseX <= Basis.BACK_X + Basis.BACK_W 
        && mouseY >= Basis.BACK_Y && mouseY <= Basis.BACK_Y + Basis.BACK_H) {
            return GameState.INSTRUCTION;
        }
        return GameState.POWER_UP;
    }

    public void onDraw(GraphicsContext gc, Canvas canvas) {
        canvas.setOnScroll((ScrollEvent e) -> {
            scrollY += e.getDeltaY();
            if (scrollY > 0) {
                scrollY = 0;
            } else if (scrollY < viewHeight - contentHeight) {
                scrollY = viewHeight - contentHeight;
            }
        });

        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Aria", 40));

        gc.drawImage(Basis.BACK_BUTTON, Basis.BACK_X, scrollY, Basis.BACK_W, Basis.BACK_H);
        gc.drawImage(Basis.MULTI_BALL_PU, x, scrollY + 100, widthImage, heightImage);
        gc.fillText("Nhân 3 số bóng hiện tại đang có.", x + 450, scrollY + 100 + heightImage / 2);
        gc.drawImage(Basis.SUPER_BALL_PU, x, scrollY + 100 + heightImage + space, widthImage, heightImage);
        gc.fillText("Tăng kích thước bóng 1.5 lần,\n tăng tốc độ bóng.", x + 450, scrollY + 100 
        + heightImage + space + heightImage / 2);
        gc.drawImage(Basis.POWERUP_TEMP, x, scrollY + 100 + 2 * (heightImage + space), widthImage, heightImage);
        gc.fillText("Nốt mấy power up đi.", x + 450, scrollY + 100 + 2 * (heightImage + space) + heightImage / 2);
    }
}
