package uet.project.arkanoid.objects.paddleMovement;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.utils.Basis;

public class AIMovement implements MovementStrategy {
    @Override
    public void move(Paddle paddle, GameSetup stage, double deltaTime) {
        Ball ball = null;
        if (!stage.getBalls().isEmpty()) {
            ball = stage.getBalls().get(0);
        }
        if (ball == null) {
            paddle.setDx(0);
            return;
        }

        double ballCenter = ball.getCenterX();

        // calculate the boundaries for the paddle's center
        double minPaddleCenter = Basis.STAGE_X + paddle.getWidth() / 2;
        double maxPaddleCenter = Basis.STAGE_WIDTH + Basis.STAGE_X - paddle.getWidth() / 2;

        double newPaddleCenter = ballCenter;

        if (newPaddleCenter < minPaddleCenter) {
            newPaddleCenter = minPaddleCenter;
        } else if (newPaddleCenter > maxPaddleCenter) {
            newPaddleCenter = maxPaddleCenter;
        }

        // convert the center back to the top-left X position
        double newX = newPaddleCenter - paddle.getWidth() / 2;

        paddle.setX(newX);
        paddle.updateHitBox();
    }
}

