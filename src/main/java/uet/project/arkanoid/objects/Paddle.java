package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.paddleMovement.MovementStrategy;
import uet.project.arkanoid.objects.paddleMovement.PlayerMovement;
import uet.project.arkanoid.utils.Basis;

public class Paddle extends MovableObject {
    private double speed;
    private PowerUp currentPowerUp = null;
    private double paddleExpansion = 50;
    private double paddleShrink = 30;

    private double originalHeight;
    private double originalWidth;

    private Rectangle hitbox;

    private MovementStrategy movementStrategy;

    private GameSetup stage;

    public Paddle(double x, double y, double width, double height, double speed, GameSetup stage) {
        super(x, y, width, height);
        this.speed = speed;
        originalWidth = width;
        originalHeight = height;
        this.stage = stage;
        this.hitbox = new Rectangle(x + width / 2.0, y + height / 2.0, width, height, 0);
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

    public void updateHitBox() {
        // Update the hitbox center and size to match the paddle’s current geometry
        double centerX = getX() + getWidth() / 2.0;
        double centerY = getY() + getHeight() / 2.0;

        this.hitbox.setCenter(new Point(centerX, centerY));
        this.hitbox.setSize(new uet.project.arkanoid.base.Vector2D(getWidth(), getHeight()));
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(Basis.PADDLE_TEXTURE, getX(), getY(), this.width, this.height);
    }

    public void update() {

        this.movementStrategy.move(this, stage);

        if (movementStrategy instanceof PlayerMovement) {
            setDx(0);
        }
    }

    public void extendPaddle() {
        if (getX() < Basis.STAGE_X + paddleExpansion) {

        } else if (getX() > Basis.OBJECTIVE_BOARD_X - paddleExpansion) {
            setX(getX() - 2 * paddleExpansion);
        } else {
            setX(getX() - paddleExpansion);
        }
        setWidth(getWidth() + 2 * paddleExpansion);
        updateHitBox();
    }

    public void shrinkPaddle() {
        setX(getX() + paddleShrink);
        setWidth(getWidth() - 2 * paddleShrink);
        updateHitBox();
    }

    public void restorePaddle() {
        setWidth(originalWidth);
        updateHitBox();
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

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }
}
