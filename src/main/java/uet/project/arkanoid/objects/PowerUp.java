package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.utils.Basis;

import java.util.List;

public class PowerUp extends GameObject {
    private final PowerUpType type;
    private double duration = 10; // so it wont check isDead
    private boolean catchedPowerUp = false;

    public enum PowerUpType {
        EXPAND_PADDLE,
        SHRINK_PADDLE,
        EXTRA_LIFE,
        MULTI_BALL,
        SPEED_UP,
        SLOW_DOWN
    }

    public PowerUp(Brick brick, int width, int height) {
        super(brick.getX() + brick.getWidth() / 2 - width,
                brick.getY(), width, height);
        type = null;
    }

    public void applyEffect(GameObject paddle) {
    }

    public void removeEffect(GameObject paddle) {
        duration = 0;
    }

    public boolean isDead() {
        return this.getY() > Basis.STAGE_TEST_Y + Basis.STAGE_TEST_HEIGHT && duration != 0;
    }

    public void update(Paddle paddle) {
        setY(getY() + 10);
        if (checkCollision(paddle) && !catchedPowerUp) {
            catchedPowerUp = true;
            applyEffect(paddle);
            duration = System.currentTimeMillis() / 1000.0;
        }
        if (!catchedPowerUp && System.currentTimeMillis() - duration >= Basis.POWERUP_DURATION) {
            removeEffect(paddle);
        }
    }

    public void render(GraphicsContext gc) {
        if (!catchedPowerUp) {
            gc.drawImage(Basis.POWERUP_TEXTURE, getX(), getY(), this.width, this.height);
        }
    }

    public void update() {

    }
    public PowerUpType getType() {
        return type;
    }

    public double getDuration() {
        return duration;
    }

}
