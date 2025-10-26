package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.project.arkanoid.utils.Basis;

public class Option {
    public void onDraw(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

        gc.setFill(Color.WHITE);               
        gc.setFont(new Font("Arial", 36));   
        gc.fillText(":))))", Basis.SCREEN_WIDTH / 2 - 50, Basis.SCREEN_HEIGHT / 2);     
    }
}
