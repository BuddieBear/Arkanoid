package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.base.*;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.utils.AudioSet;

public abstract class PowerUp extends GameObject {
    protected boolean alive = true;
    protected PowerUpType type;
    private boolean catchedPowerUp = false;
    protected GameSetup stage;

    private Rectangle hitbox;

    // New timer logic
    protected long startTime = 0; // The time the effect was applied
    protected long effectDurationMillis = 3000; // 3 seconds

    public enum PowerUpType {
        EXPAND_PADDLE,
        SHRINK_PADDLE,
        MULTI_BALL,
        DAMAGE_BRICK,
        SUPER_BALL,
        INVINCIBLE_BALL,
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

    public void extendDuration(long extraTime) {
        if (this.startTime > 0) {
            this.effectDurationMillis += extraTime; // extends the expiry by extraTime
        }
    }


    @Override
    public Rectangle getHitbox() {
        return this.hitbox;
    }

    public abstract void applyEffect();

    public abstract void removeEffect();

    public boolean isDead() {
        return !alive|| this.getY() > Basis.STAGE_Y + Basis.STAGE_HEIGHT ||
                (catchedPowerUp && startTime == 0);
    }


    public boolean isCatchedPowerUp() {
        return catchedPowerUp;
    }

    public void setCatchedPowerUp(boolean catchedPowerUp) {
        this.catchedPowerUp = catchedPowerUp;
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

    public void startEffectTimer() {
        if (type == PowerUpType.EXTRA_LIFE || type == PowerUpType.DOUBLE_SCORE
                || type == PowerUpType.RESPAWN_FREE) {
            this.startTime = 0;
        } else {
            this.startTime = System.currentTimeMillis();
        }
    }

    public PowerUpType getType() {
        return type;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public long getEffectDurationMillis() {
        return effectDurationMillis;
    }

    public long getDuration() {
        if (startTime == 0) {
            return 0;
        }
        return Math.max(0, effectDurationMillis - (System.currentTimeMillis() - startTime));
    }
}
