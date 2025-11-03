package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.PowerUp;

public class ExtendPaddle extends PowerUp {

  private Paddle paddleMain = stage.getPaddle();

  public ExtendPaddle(GameObject object, double width, double height, GameSetup stage) {
    super(object, width, height, stage, PowerUpType.SHRINK_PADDLE);
  }

  public void applyEffect() {
    paddleMain.extendPaddle();
  }

  public void removeEffect() {
    paddleMain.restorePaddle();
    alive = false;
  }
}
