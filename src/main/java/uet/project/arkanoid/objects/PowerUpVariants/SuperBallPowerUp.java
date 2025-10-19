package uet.project.arkanoid.objects.PowerUpVariants;

import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.BrickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.PowerUp;

public class SuperBallPowerUp extends PowerUp {
    Ball ball = Basis.stage.getBalls().get(0);
    private int oldSpeed = ball.getSpeed();
    private double oldWidth = ball.getWidth();
    private double oldHeight = ball.getHeight();
    private int oldX;
    private int oldY;

    // remember it will bug if theres another powerUp affect speed
    public SuperBallPowerUp(GameObject object, double width, double height) {
        super(object, width, height);
        type = PowerUpType.SUPER_BALL;
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
        super.removeEffect();
        Ball ball = Basis.stage.getBalls().get(0);
        ball.setSpeed(oldSpeed);
        ball.setWidth(oldWidth);
        ball.setHeight(oldHeight);
        // Restore old position
        ball.setX(ball.getX() + (int) (oldWidth / 2));
        ball.setY(ball.getY() + (int) (oldHeight / 2));
    }
}
