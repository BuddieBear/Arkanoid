package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.utils.Basis;

public class SuperBallPowerUp extends PowerUp {
    private Ball ball;
    private final int oldSpeed;
    private final double oldRadius;

    // remember it will bug if theres another powerUp affect speed
    public SuperBallPowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage, PowerUpType.SUPER_BALL);
        ball = stage.getBalls().get(0);
        oldSpeed = (int) ball.getSpeed();
        oldRadius = ball.getRadius();
    }

    public void applyEffect() {
        if (ball.isMainBall()) {
            ball.setSpeed(20);
            ball.setRadius(oldRadius * 2);
            ball.updateVelocity();
        }
    }

    @Override
    public void removeEffect() {
        if (ball.isMainBall()) {
            ball.restoreDefaultStats(); // << use the new safe restore
            alive = false;
        }
    }
}
