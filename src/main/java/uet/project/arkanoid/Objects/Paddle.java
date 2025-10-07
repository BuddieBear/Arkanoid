package uet.project.arkanoid.Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.project.arkanoid.Basis;

import java.util.Objects;

public class Paddle extends MovableObject {
    private int speed;
    private PowerUp currentPowerUp = null;


    public Paddle(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public PowerUp getCurrentPowerUp() {
        return this.currentPowerUp;
    }

    public void setCurrentPowerUp(PowerUp current) {
        this.currentPowerUp = current;
    }

    public void moveLeft() {
        setDx(-speed);
    }

    public void moveRight() {
        setDx(speed);
    }

    @Override
    public void move() {
        setX(getX() + getDx());
        System.out.println("Paddle X = " + getX() + " | DX = " + getDx());
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(Basis.PADDLE_TEXTURE, getX(), getY(), this.width, this.height);
    }

    public void update() {
        // Move the paddle based on its dx
        move();

        // Prevent paddle from leaving stage boundaries
        if (getX() < Basis.STAGE_TEST_X) {
            setX(Basis.STAGE_TEST_X);
        } else if (getX() + this.width > Basis.STAGE_TEST_X + Basis.STAGE_TEST_WIDTH) {
            setX(Basis.STAGE_TEST_X + Basis.STAGE_TEST_WIDTH - this.width);
        }
    }
}
