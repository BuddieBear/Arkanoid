package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.utils.Basis;

import java.util.ArrayList;
import java.util.List;

public class MultiBallPowerUp extends PowerUp {
    private final List<Ball> spawnedBalls = new ArrayList<>();

    public MultiBallPowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage, PowerUpType.MULTI_BALL);
    }

    public void applyEffect() {
        Ball mainBall = stage.getBalls().get(0);
        double baseSpeed = mainBall.getSpeed();
        double currentAngle = Math.atan2(mainBall.getDy(), mainBall.getDx());

        // ±45° directions
        double rad1 = currentAngle + Math.toRadians(45);
        double rad2 = currentAngle - Math.toRadians(45);

        // Create first clone
        Ball b1 = new Ball(mainBall.getCenterX(), mainBall.getCenterY(),
                mainBall.getRadius(), baseSpeed, stage);
        b1.setDx(baseSpeed * Math.cos(rad1));
        b1.setDy(baseSpeed * Math.sin(rad1));
        b1.setHasLaunch(true);
        b1.setBallImage(Basis.MULTI_BALL_TEXTURE);
        b1.setMainBall(false);

        // Create second clone
        Ball b2 = new Ball(mainBall.getCenterX(), mainBall.getCenterY(),
                mainBall.getRadius(), baseSpeed, stage);
        b2.setDx(baseSpeed * Math.cos(rad2));
        b2.setDy(baseSpeed * Math.sin(rad2));
        b2.setHasLaunch(true);
        b2.setBallImage(Basis.MULTI_BALL_TEXTURE);
        b2.setMainBall(false);

        spawnedBalls.add(b1);
        spawnedBalls.add(b2);
        stage.getBalls().addAll(spawnedBalls);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        if (isCatchedPowerUp()) {
            // Remove effect early if all spawned balls are gone
            boolean allGone = spawnedBalls.stream().allMatch(Ball::isMarkedForRemoval);
            if (allGone || isDead()) {
                removeEffect();
            }
        }
    }

    public void removeEffect() {
        stage.getBalls().removeAll(spawnedBalls);
        spawnedBalls.clear();
        alive = false;
    }
}