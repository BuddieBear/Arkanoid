package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.utils.Basis;

public class Paddle extends MovableObject {
    private int speed;
    private PowerUp currentPowerUp = null;


    public Paddle(int x, int y, double width, double height, int speed) {
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
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(Basis.PADDLE_TEXTURE, getX(), getY(), this.width, this.height);
    }

    public void update() {
        // Move the paddle based on its dx
        move();

        // Prevent paddle from leaving stage boundaries
        // 33 is the padding of the paddle.
        if (getX() <= Basis.STAGE_TEST_X) {
            setX(Basis.STAGE_TEST_X);
        } else if (getX() + this.width >= Basis.STAGE_TEST_X + Basis.STAGE_TEST_WIDTH) {
            setX(Basis.STAGE_TEST_X + Basis.STAGE_TEST_WIDTH - (int)this.width);
        }
    }
}
