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
}