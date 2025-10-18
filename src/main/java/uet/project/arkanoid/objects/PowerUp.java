package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.utils.Basis;

import java.util.List;

public abstract class PowerUp extends GameObject {
    protected PowerUpType type;
    private double duration = 10; // so it wont check isDead
    private boolean catchedPowerUp = false;

    public enum PowerUpType {
        EXPAND_PADDLE,
        SHRINK_PADDLE,
        EXTRA_LIFE,
        MULTI_BALL,
        SPEED_UP,
        DAMAGE_BRICK,
        SMALL_BRICK,
        HARDER_BRICK,
        SLOW_DOWN
    }

    public PowerUp(GameObject object, int width, int height) {
        super(object.getX() + object.getWidth() / 2 - width,
                object.getY(), width, height);
    }

    public abstract void applyEffect(GameObject paddle);

    public void removeEffect(GameObject paddle) {
        duration = 0;
    }

    public boolean isDead() {
        return this.getY() > Basis.STAGE_TEST_Y + Basis.STAGE_TEST_HEIGHT && duration != 0;
    }

    public void update(GameObject object) {
        setY(getY() + 10);
        if (checkCollision(object) && !catchedPowerUp) {
            catchedPowerUp = true;
            applyEffect(object);
            duration = System.currentTimeMillis();
        }
        if (catchedPowerUp && (System.currentTimeMillis() - duration) / 1000.0 >= Basis.POWERUP_DURATION) {
            removeEffect(object);
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
