package uet.project.arkanoid.objects.PowerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.PowerUp;

public class ShrinkPaddle extends PowerUp{
    private Paddle paddleMain = stage.getPaddle();
    public ShrinkPaddle(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage);
        type = PowerUpType.SHRINK_PADDLE;
    }

    @Override
    public void applyEffect() {
        paddleMain.shrinkPaddle();
    }

    @Override
    public void removeEffect() {
        paddleMain.restorePaddle();
    }
}
