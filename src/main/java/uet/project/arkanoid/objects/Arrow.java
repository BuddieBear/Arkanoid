package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.Basis;

public class Arrow extends MovableObject{
    private int speed = 0;

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
        
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(Basis.ARROW_TEXTURE, getX(), getY(), this.width, this.height);
    }

    public void update(){

    }
}
