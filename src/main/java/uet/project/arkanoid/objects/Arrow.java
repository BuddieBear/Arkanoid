package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.project.arkanoid.Basis;
import uet.project.arkanoid.GameManager;

public class Arrow extends MovableObject{
    private int speed = 0;
    private int angle = 0;
    private boolean back = false;

    public Arrow(int x, int y, int width, int height, int speed, int dx, int dy) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setUpArrow(int x, int y, int width, int height, int speed, int dx, int dy) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setSpeed(speed);
        this.setDx(dx);
        this.setDy(dy);
    }

    public void move() {
        Ball ballMain = GameManager.getBall();
        setX(ballMain.getX() - 10);
    }

    public void render(GraphicsContext gc) {
        gc.save();

        gc.translate(getX() + getWidth() / 2, getY() + getHeight() - 10);
        gc.rotate(angle);
        gc.drawImage(Basis.ARROW_TEXTURE, -35, -50, this.width, this.height);
        gc.setStroke(Color.GREEN);
        gc.strokeLine(-50, 0, 50, 0);

        gc.restore();
        if (! back) {
            if (++angle >= 90) {
                back = true;
            }
        } else {
            if (--angle <= -90) {
                back = false;
            }
        }
    }

    public void update(){
        move();
    }
}
