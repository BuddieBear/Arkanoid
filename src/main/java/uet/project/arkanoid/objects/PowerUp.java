package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.utils.Basis;

import java.util.List;

public abstract class PowerUp extends GameObject {
    protected PowerUpType type;
    private long duration = 10; // so it wont check isDead
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
        SUPER_BALL,
        INVINCIBLE_BALL,
        SLOW_DOWN
    }

    public PowerUp(GameObject object, double width, double height) {
        super( (int)(object.getX() + object.getWidth() / 2 - width),
                object.getY(), width, height);
    }

    public abstract void applyEffect();

    public abstract void removeEffect();

    public boolean isDead() {
        return this.getY() > Basis.STAGE_TEST_Y + Basis.STAGE_TEST_HEIGHT || duration == 0;
    }

    public void update() {
        if(!catchedPowerUp) {
            setY(getY() + 10);
        }
        if (checkCollision(Basis.stage.getPaddles().get(0)) && !catchedPowerUp) {
            catchedPowerUp = true;
            applyEffect();
            duration = System.currentTimeMillis();
        }
        if (catchedPowerUp && (System.currentTimeMillis() - duration) >= 3000) {
            duration = 0;
            removeEffect();
        }
    }

    public void render(GraphicsContext gc) {
        if (!catchedPowerUp) {
            gc.drawImage(Basis.POWERUP_TEXTURE, getX(), getY(), this.width, this.height);
        }
    }

    public PowerUpType getType() {
        return type;
    }

    public long getDuration() {
        return duration;
    }

}
