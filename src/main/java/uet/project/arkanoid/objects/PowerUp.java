package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.base.*;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.utils.AudioSet;

import java.util.List;

public abstract class PowerUp extends GameObject {
    protected boolean alive = true;
    protected PowerUpType type;
    private boolean catchedPowerUp = false;
    protected GameSetup stage;

    private Rectangle hitbox;

    // New timer logic
    private long startTime = 0; // The time the effect was applied
    private long effectDurationMillis = 10000; // 3 seconds

    public enum PowerUpType {
        EXPAND_PADDLE,
        SHRINK_PADDLE,
        MULTI_BALL,
        SPEED_UP,
        DAMAGE_BRICK,
        SMALL_BRICK,
        HARDER_BRICK,
        SUPER_BALL,
        INVINCIBLE_BALL,
        SLOW_DOWN,
        EXTRA_LIFE,
        DOUBLE_SCORE,
        RESPAWN_FREE
    }

    public PowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object.getX() + object.getWidth() / 2 - width / 2,
                object.getY(), width, height);
        this.stage = stage;

        // Initialize the hitbox (0 rotation)
        this.hitbox = new Rectangle(
                getX() + width / 2.0,
                getY() + height / 2.0,
                width,
                height,
                0
        );
    }

    public PowerUp(PowerUp other) {
        super(other.getX(), other.getY(), other.width, other.height);
        this.type = other.type;
        this.catchedPowerUp = other.catchedPowerUp;
        this.stage = other.stage; // Share stage reference
        this.startTime = other.startTime;
        this.effectDurationMillis = other.effectDurationMillis;

        // Deep copy the hitbox
        this.hitbox = new Rectangle(other.hitbox);
    }

    @Override
    public Shape getHitbox() {
        return this.hitbox;
    }

    public abstract void applyEffect();

    public abstract void removeEffect();

    public boolean isDead() {
        return !alive|| this.getY() > Basis.STAGE_Y + Basis.STAGE_HEIGHT ||
                (catchedPowerUp && startTime == 0);
    }

    public void update() {
        if (!catchedPowerUp) {
            setY(getY() + 10); // Move down

            this.hitbox.setCenter(new Point(
                    getX() + this.width / 2.0,
                    getY() + this.height / 2.0
            ));


            // Use the new hitbox intersect method
            if (this.hitbox.intersect(stage.getPaddles().get(0).getHitbox())) {
                catchedPowerUp = true;
                applyEffect();
                AudioSet.powerUpSound.play();

                // Check if this is an instant-effect powerup
                if (type == PowerUpType.EXTRA_LIFE || type == PowerUpType.DOUBLE_SCORE
                        || type == PowerUpType.RESPAWN_FREE) {
                    // Apply effect and die immediately
                    startTime = 0; // This will cause isDead() to return true
                } else {
                    // Start the 3-second timer
                    startTime = System.currentTimeMillis();
                }
            }
        } else {

            if (startTime > 0) {
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed >= effectDurationMillis) {
                    startTime = 0; // Signal for removal
                    removeEffect();
                }
            }
        }
    }

    public void render(GraphicsContext gc) {
        // Only render if it hasn't been caught yet
        if (!catchedPowerUp) {
            gc.drawImage(Basis.POWERUP_TEXTURE, getX(), getY(), this.width, this.height);
        }
    }

    public PowerUpType getType() {
        return type;
    }

    public long getDuration() {
        if (startTime == 0) return 0;
        return Math.max(0, effectDurationMillis - (System.currentTimeMillis() - startTime));
    }
}
