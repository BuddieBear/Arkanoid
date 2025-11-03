package uet.project.arkanoid.objects.deBuffVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.PowerUp;

public class ShrinkPaddle extends PowerUp{
    private Paddle paddleMain = stage.getPaddle();
    public ShrinkPaddle(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage, PowerUpType.SHRINK_PADDLE);
    }

    public void applyEffect() {
        paddleMain.shrinkPaddle();
    }

    public void removeEffect() {
        paddleMain.restorePaddle();
        alive = false;
    }
}
