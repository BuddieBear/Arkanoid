package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.Basis;

public class Paddle extends MovableObject {
    private int speed;
    private PowerUp currentPowerUp = null;


    public Paddle(int x, int y, int width, int height, int speed, int dx, int dy) {
        super(x, y, width, height, dx, dy);
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
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(Basis.PADDLE_TEXTURE, getX(), getY(), this.width, this.height);
    }

    public void update() {
        // Move the paddle based on its dx
        move();

        // Prevent paddle from leaving stage boundaries
        // 33 is the padding of the paddle.
        if (getX() <= Basis.STAGE_TEST_X - 33) {
            setX(Basis.STAGE_TEST_X - 33);
        } else if (getX() + this.width - 33 >= Basis.STAGE_TEST_X + Basis.STAGE_TEST_WIDTH) {
            setX(Basis.STAGE_TEST_X + Basis.STAGE_TEST_WIDTH - this.width + 33);
        }
    }
}
