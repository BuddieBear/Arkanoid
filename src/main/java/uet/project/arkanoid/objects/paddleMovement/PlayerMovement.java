package uet.project.arkanoid.objects.paddleMovement;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.utils.HelperFunction;

public class PlayerMovement implements MovementStrategy {

  public void move(Paddle paddle, GameSetup stage, double deltaTime) {
    // standard movement logic
    double newX = paddle.getX() + paddle.getDx() * deltaTime;

    newX = HelperFunction.clamp(newX, Basis.STAGE_X,
        Basis.STAGE_WIDTH + Basis.STAGE_X - paddle.getWidth());

    paddle.setX(newX);
    paddle.updateHitBox();
  }
}
