package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.objects.brickVariants.IndestructibleBrick;
import uet.project.arkanoid.utils.AudioSet;

public class InvincibleBallPowerUp extends PowerUp {
    private static final long EFFECT_DURATION = 5000; // 5 seconds

    public InvincibleBallPowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage, PowerUpType.INVINCIBLE_BALL);
    }

    @Override
    public void applyEffect() {
        Ball ball = stage.getBalls().get(0);
        ball.setInvincible(true);
    }

    @Override
    public void removeEffect() {
        Ball ball = stage.getBalls().get(0);
        ball.setInvincible(false);
        alive = false;
    }

    @Override
    public void update() {
        if (!this.isCatchedPowerUp()) {
            // Falling animation
            setY(getY() + 10);
            this.getHitbox().setCenter(new Point(getX() + this.width / 2.0, getY() + this.height / 2.0));

            // Check if caught by paddle
            if (this.getHitbox().intersect(stage.getPaddles().get(0).getHitbox())) {
                this.setCatchedPowerUp(true);
                applyEffect();
                AudioSet.powerUpSound.play();
                startTime = System.currentTimeMillis();
            }

        } else {
            // Already active → check duration
            if (startTime > 0) {
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed >= EFFECT_DURATION) {
                    Ball ball = stage.getBalls().get(0);

                    // Check if ball is still intersecting an unbreakable brick
                    boolean touchingUnbreakable = stage.getBricks().stream()
                            .filter(b -> b instanceof IndestructibleBrick)
                            .anyMatch(b -> b.getHitbox().intersect(ball.getHitbox()));

                    // Only remove if NOT touching unbreakable
                    if (!touchingUnbreakable) {
                        removeEffect();
                    }
                }
            }
        }
    }
}