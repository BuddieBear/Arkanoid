package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import jdk.jfr.Category;
import uet.project.arkanoid.base.*;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.utils.AudioSet;

import java.util.List;
import java.util.Objects;

public abstract class PowerUp extends GameObject {
    protected boolean alive = true;
    protected PowerUpType type;
    private boolean catchedPowerUp = false;
    protected GameSetup stage;
    private double speed = 75;

    private Rectangle hitbox;

    // New timer logic
    protected long startTime = 0; // The time the effect was applied
    protected long effectDurationMillis = 3000; // 3 seconds

    public enum PowerUpType {
        // Buffs
        EXPAND_PADDLE,
        SHRINK_PADDLE,
        MULTI_BALL,
        DAMAGE_BRICK,
        SUPER_BALL,
        INVINCIBLE_BALL,
        EXTRA_LIFE,
        DOUBLE_SCORE,
        RESPAWN_FREE,

        // Debuffs
        HARDER_BRICK
    }

    public PowerUp(GameObject object, double width, double height, GameSetup stage, PowerUpType type) {
        super(object.getX() + object.getWidth() / 2 - width / 2,
                object.getY(), width, height);
        this.stage = stage;
        this.type = type; // ✅ FIXED — always initialized

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

    public void update(double deltaTime) {

    }
    public void update(List<FloatingText> others, double deltaTime) {
        if (!catchedPowerUp) {
            setY(getY() + speed * deltaTime); // Move down

            this.hitbox.setCenter(new Point(
                    getX() + this.width / 2.0,
                    getY() + this.height / 2.0
            ));


            // Use the new hitbox intersect method
            if (this.hitbox.intersect(stage.getPaddles().get(0).getHitbox())) {
                catchedPowerUp = true;
                applyEffect();
                AudioSet.powerUpSound.play();
                if (type == PowerUpType.HARDER_BRICK) {
                    others.add(new FloatingText(String.valueOf(type),
                            getX(),
                            getY(),
                            Color.RED));
                } else {
                    others.add(new FloatingText(String.valueOf(type),
                            getX(),
                            getY(),
                            Color.GREEN));
                }
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

    }
    public void render(GraphicsContext gc, int index) {
        // Only render if it hasn't been caught yet
        if (!catchedPowerUp) {
            gc.drawImage(Basis.POWERUP_TEXTURE, getX(), getY(), this.width, this.height);
        } else {
            // Render duration bar (reload bar)
            long elapsed = System.currentTimeMillis() - startTime;
            double progress = Math.max(0, 1.0 - (double) elapsed / effectDurationMillis);

            double barY = 390 + 40 * index;

            double iconSize = 40;
            double iconX = 1135 - iconSize + 10; // cách bar 10px
            double iconY = barY - 5;

            if(type == PowerUpType.HARDER_BRICK) {
                gc.setFill(Color.DARKRED);
            } else {
                gc.setFill(Color.LIMEGREEN);
            }
            gc.fillRect(1135, barY, 100 * progress, 30); // 30 is height //1135 is X, barY is Y

            switch (type) {
                case HARDER_BRICK -> gc.drawImage(Basis.SKULL_TEXTURE, iconX, iconY, iconSize, iconSize);
                case EXPAND_PADDLE, SHRINK_PADDLE -> gc.drawImage(Basis.PADDLE_TEXTURE, iconX, iconY, iconSize, iconSize);
                case SUPER_BALL, INVINCIBLE_BALL, MULTI_BALL -> gc.drawImage(Basis.BALL_TEXTURE, iconX, iconY, iconSize, iconSize);
                default -> gc.drawImage(Basis.POWERUP_TEXTURE, iconX, iconY, iconSize, iconSize);
            }
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
