package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.base.*;
import uet.project.arkanoid.utils.Basis;

public class Paddle extends MovableObject {
    private double speed;
    private PowerUp currentPowerUp = null;

    private Rectangle hitbox;

    public Paddle(int x, int y, double width, double height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
        // Initialize hitbox, assuming 0 rotation for paddle
        this.hitbox = new Rectangle(x + width / 2.0, y + height / 2.0, width, height, 0);
    }

    @Override
    public Shape getHitbox() {
        return this.hitbox;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
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

        move();
        if (getX() <= Basis.STAGE_X) {
            setX(Basis.STAGE_X);
        } else if (getX() + this.width >= Basis.STAGE_X + Basis.STAGE_WIDTH) {
            setX(Basis.STAGE_X + Basis.STAGE_WIDTH - (int)this.width);
        }

        this.hitbox.setCenter(new Point(getX() + this.width / 2.0, getY() + this.height / 2.0));
    }
}
