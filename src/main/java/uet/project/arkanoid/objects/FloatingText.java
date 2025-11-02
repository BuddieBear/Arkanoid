package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.project.arkanoid.utils.Basis;

import java.awt.*;

public class FloatingText {
    private double elapsed = 0;

    private final String text;
    private final Color color;
    private double x;
    private double y;

    public FloatingText(String text, double x, double y, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public boolean isExpired() {
        return elapsed >= Basis.DURATION;
    }

    public void update(double deltaTime) {
        elapsed += deltaTime;

        y -= 120 * deltaTime;
    }

    public void render(GraphicsContext gc) {
        gc.setGlobalAlpha(1.0 - (elapsed / Basis.DURATION)); // becoming more transparent over time
        gc.setFill(color);
        gc.setFont(new Font("Impact", 24));
        gc.fillText(text, x, y);
        gc.setGlobalAlpha(1.0);
    }
}