package uet.project.arkanoid.objects;

import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.base.Vector2D;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.paddleMovement.MovementStrategy;
import uet.project.arkanoid.objects.paddleMovement.PlayerMovement;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;

/**
 * Represents the paddle in the Arkanoid game that the player controls.
 *
 * <p>The paddle can move horizontally, change size through power-ups, and has different movement
 * strategies.
 */
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
    private Thunder thunder;

    private long currentTime;
    private boolean subtracted = false;

    /**
     * Constructs a new Paddle with specified parameters.
     *
     * @param x      the x-coordinate of the paddle's top-left corner
     * @param y      the y-coordinate of the paddle's top-left corner
     * @param width  the initial width of the paddle
     * @param height the initial height of the paddle
     * @param speed  the movement speed of the paddle
     * @param stage  the game stage this paddle belongs to
     */
    public Paddle(double x, double y, double width, double height, double speed, GameSetup stage) {
        super(x, y, width, height);
        this.speed = speed;
        originalWidth = width;
        originalHeight = height;
        this.stage = stage;
        this.hitbox = new Rectangle(x + width / 2.0, y + height / 2.0, width, height, 0);
        this.thunder = stage.getThunder();
        this.currentTime = System.nanoTime();
    }

    /**
     * Sets the paddle's velocity to move left.
     */
    public void moveLeft() {
        setDx(-speed);
    }

    /**
     * Sets the paddle's velocity to move right.
     */
    public void moveRight() {
        setDx(speed);
    }

    /**
     * Moves the paddle based on its current velocity.
     *
     * @param deltaTime the time elapsed since the last update
     */
    @Override
    public void move(double deltaTime) {
        setX(getX() + getDx() * deltaTime);
    }

    /**
     * Updates the paddle's hitbox to match its current position and size.
     */
    public void updateHitBox() {
        // Update the hitbox center and size to match the paddle's current geometry
        double centerX = getX() + getWidth() / 2.0;
        double centerY = getY() + getHeight() / 2.0;

        this.hitbox.setCenter(new Point(centerX, centerY));
        this.hitbox.setSize(new Vector2D(getWidth(), getHeight()));
    }

    /**
     * Renders the paddle on the graphics context.
     *
     * @param gc the graphics context to render on
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(Basis.PADDLE_TEXTURE, getX(), getY(), this.width, this.height);
    }

    /**
     * Updates the paddle's state including movement and hitbox.
     *
     * @param deltaTime the time elapsed since the last update
     */
    public void update(double deltaTime) {

        this.movementStrategy.move(this, stage, deltaTime);

        if (movementStrategy instanceof PlayerMovement) {
            setDx(0);
        }

        if (collisionThunder()) {
            if (!subtracted) {
                stage.setLives((stage.getLives() - 1));
                subtracted = true;
                currentTime = System.nanoTime();
            }
            if ((System.nanoTime() - currentTime) / 1_000_000_000.0 >= 0.5 && subtracted) {
                subtracted = false;
            }
        }
    }

    private boolean collisionThunder() {
        int[] position = thunder.getPosition();
        int n = thunder.getAmount();
        for (int i = 0; i < n; i++) {
            int condition1 = position[i] + thunder.getThunderWidth() / 2;
            int condition2 = position[i] - thunder.getThunderWidth() / 2;
            if (((condition1 >= getX() && position[i] < getX())
                || (condition2 <= getX() + getWidth() && position[i] > getX() + getWidth())
                || (position[i] <= getX() + getWidth() && position[i] >= getX()))
                && thunder.getShowThunder()) {
                return true;
            }
        }
        return false;
    }

    public void Collision(List<? extends GameObject> others) {
        for (GameObject obj : others) {
            if (this.hitbox.intersect(obj.getHitbox())) {
                if (obj instanceof Brick) {
                    Brick brick = (Brick) obj;

                    // CHỈ reset khi brick đang di chuyển
                    if (brick.isMovementActivated()) {
                        stage.setLives(stage.getLives() - 1);
                        brick.resetToOriginalPosition();
                        brick.setMovementActivated(false);
                        AudioSet.collisionBrickSound.play();

                        System.out.println("Paddle hit moving brick - life lost");
                    }
                }
            }
        }
    }

    /**
     * Extends the paddle's width by the expansion amount. Adjusts position to maintain center
     * alignment.
     */
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

    /**
     * Shrinks the paddle's width by the shrink amount. Adjusts position to maintain center
     * alignment.
     */
    public void shrinkPaddle() {
        setX(getX() + paddleShrink);
        setWidth(getWidth() - 2 * paddleShrink);
        updateHitBox();
    }

    /**
     * Restores the paddle to its original width.
     */
    public void restorePaddle() {
        setWidth(originalWidth);
        updateHitBox();
    }

    /**
     * Gets the paddle's hitbox.
     *
     * @return the rectangular hitbox of the paddle
     */
    @Override
    public Shape getHitbox() {
        return this.hitbox;
    }

    /**
     * Gets the paddle's movement speed.
     *
     * @return the current speed of the paddle
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Sets the paddle's movement speed.
     *
     * @param speed the new speed value
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Gets the current active power-up on the paddle.
     *
     * @return the current power-up, or null if none is active
     */
    public PowerUp getCurrentPowerUp() {
        return this.currentPowerUp;
    }

    /**
     * Sets the current active power-up on the paddle.
     *
     * @param current the power-up to set as active
     */
    public void setCurrentPowerUp(PowerUp current) {
        this.currentPowerUp = current;
    }

    /**
     * Gets the current movement strategy of the paddle.
     *
     * @return the movement strategy being used
     */
    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    /**
     * Sets the movement strategy for the paddle.
     *
     * @param strategy the movement strategy to use
     */
    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }
}