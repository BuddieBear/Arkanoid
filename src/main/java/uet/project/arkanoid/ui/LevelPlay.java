package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uet.project.arkanoid.utils.Basis;

public class LevelPlay {
    private static final int radius = 50;
    public void onDraw(GraphicsContext gc) {
        gc.setFill(Color.BURLYWOOD);
        gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);
        
        drawRoundedImage(gc, Basis.LEVEL_1, Basis.LEVEL_1_IMAGE_X, Basis.LEVEL_IMAGE_Y, Basis.LEVEL_IMAGE_W, Basis.LEVEL_IMAGE_H, radius);
        drawRoundedImage(gc, Basis.LEVEL_2, Basis.LEVEL_2_IMAGE_X, Basis.LEVEL_IMAGE_Y, Basis.LEVEL_IMAGE_W, Basis.LEVEL_IMAGE_H, radius);
        drawRoundedImage(gc, Basis.LEVEL_3, Basis.LEVEL_3_IMAGE_X, Basis.LEVEL_IMAGE_Y, Basis.LEVEL_IMAGE_W, Basis.LEVEL_IMAGE_H, radius);

        gc.drawImage(Basis.BACK_BUTTON, Basis.BACK_X, Basis.BACK_Y, Basis.BACK_W, Basis.BACK_H);

        gc.drawImage(Basis.TEXT_LEVEL_1, Basis.TEXT_LEVEL_1_X, Basis.TEXT_LEVEL_Y, Basis.TEXT_LEVEL_W, Basis.TEXT_LEVEL_H);
        gc.drawImage(Basis.TEXT_LEVEL_2, Basis.TEXT_LEVEL_2_X, Basis.TEXT_LEVEL_Y, Basis.TEXT_LEVEL_W, Basis.TEXT_LEVEL_H);
        gc.drawImage(Basis.TEXT_LEVEL_3, Basis.TEXT_LEVEL_3_X, Basis.TEXT_LEVEL_Y, Basis.TEXT_LEVEL_W, Basis.TEXT_LEVEL_H);

        // gc.setFill(Color.BLACK);
        // gc.fillText("LEVEL 1", space + widthImage / 2 - 50, 70 + 400 + 30);

    }

    public static int selectLevel(double mouseX, double mouseY) {
        if (mouseX >= Basis.LEVEL_1_IMAGE_X && mouseX <= Basis.LEVEL_1_IMAGE_X + Basis.LEVEL_IMAGE_W
         && mouseY >= Basis.LEVEL_IMAGE_Y && mouseY <= Basis.LEVEL_IMAGE_Y + Basis.LEVEL_IMAGE_H) {
            return 1;
        } else if (mouseX >= Basis.LEVEL_2_IMAGE_X && mouseX <= Basis.LEVEL_2_IMAGE_X + Basis.LEVEL_IMAGE_W
         && mouseY >= Basis.LEVEL_IMAGE_Y && mouseY <= Basis.LEVEL_IMAGE_Y + Basis.LEVEL_IMAGE_H) {
            return 2;
        } else if (mouseX >= Basis.LEVEL_3_IMAGE_X && mouseX <= Basis.LEVEL_3_IMAGE_X + Basis.LEVEL_IMAGE_W
         && mouseY >= Basis.LEVEL_IMAGE_Y && mouseY <= Basis.LEVEL_IMAGE_Y + Basis.LEVEL_IMAGE_H) {
            return 3;
        } else if (mouseX >= Basis.BACK_X && mouseX <= Basis.BACK_X + Basis.BACK_W 
        && mouseY >= Basis.BACK_Y && mouseY <= Basis.BACK_Y + Basis.BACK_H) {
            return 4;
        }
        return 0;
    }

    private void drawRoundedImage(GraphicsContext gc, Image img,
                                  double x, double y, double w, double h, double radius) {
        gc.save();

        gc.beginPath();
        gc.moveTo(x + radius, y);
        gc.lineTo(x + w - radius, y);
        gc.quadraticCurveTo(x + w, y, x + w, y + radius);
        gc.lineTo(x + w, y + h - radius);
        gc.quadraticCurveTo(x + w, y + h, x + w - radius, y + h);
        gc.lineTo(x + radius, y + h);
        gc.quadraticCurveTo(x, y + h, x, y + h - radius);
        gc.lineTo(x, y + radius);
        gc.quadraticCurveTo(x, y, x + radius, y);
        gc.closePath();

        gc.clip();
        gc.drawImage(img, x, y, w, h);
        gc.restore();
    }
}
