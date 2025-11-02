package uet.project.arkanoid.objects.paddleMovement;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.utils.HelperFunction;

public class PlayerMovement implements MovementStrategy {
    public void move(Paddle paddle, GameSetup stage) {
        // Standard movement logic (copied from Paddle's old move() method, plus boundaries)
        double newX = paddle.getX() + paddle.getDx();

        // Clamp paddle movement within the stage boundaries
        newX = HelperFunction.clamp(
                newX, Basis.STAGE_X,
                Basis.STAGE_WIDTH - Basis.STAGE_X - paddle.getWidth()
        );

        paddle.setX(newX);
        paddle.updateHitBox();
    }
}
