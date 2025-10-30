package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.project.arkanoid.utils.Basis;

public class Instruction {
    public void onDraw(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

        gc.drawImage(Basis.BACK_BUTTON, Basis.BACK_X, Basis.BACK_Y, Basis.BACK_W, Basis.BACK_H);

        gc.setFill(Color.WHITE);               
        gc.setFont(new Font("Arial", 36));   
        gc.fillText(":))))", Basis.SCREEN_WIDTH / 2 - 50, Basis.SCREEN_HEIGHT / 2);     
    }

    public static boolean back(double mouseX, double mouseY) {
        return mouseX >= Basis.BACK_X && mouseX <= Basis.BACK_X + Basis.BACK_W 
        && mouseY >= Basis.BACK_Y && mouseY <= Basis.BACK_Y + Basis.BACK_H;
    }
}
