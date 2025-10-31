package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;

public class SuperBallPowerUp extends PowerUp {
    private Ball ball;
    private final int oldSpeed;
    private final double oldWidth;
    private final double oldHeight;
    private int oldX;
    private int oldY;

    // remember it will bug if theres another powerUp affect speed
    public SuperBallPowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage);
        type = PowerUpType.SUPER_BALL;
        ball = stage.getBalls().get(0);
        oldSpeed = (int) ball.getSpeed();
        oldWidth = ball.getWidth();
        oldHeight = ball.getHeight();
    }

    public void applyEffect() {
        ball.setSpeed(100);
        ball.setWidth(oldWidth * 1.5);
        ball.setHeight(oldHeight * 1.5);
        // Adjust position so ball stays visually centered
        ball.setX(ball.getX() - (int) (oldWidth / 2));
        ball.setY(ball.getY() - (int) (oldHeight / 2));
    }

    @Override
    public void removeEffect() {
        Ball ball = stage.getBalls().get(0);
        ball.setSpeed(oldSpeed);
        ball.setWidth(oldWidth);
        ball.setHeight(oldHeight);
        // Restore old position
        ball.setX(ball.getX() + (int) (oldWidth / 2));
        ball.setY(ball.getY() + (int) (oldHeight / 2));
        alive = false;
    }
}
