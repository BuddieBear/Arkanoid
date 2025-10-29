package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;

public class InvincibleBallPowerUp extends PowerUp {
    public InvincibleBallPowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage);
        type = PowerUpType.INVINCIBLE_BALL;
    }

    public void applyEffect() {
           stage.getBalls().get(0).setInvincible(true);
    }

    @Override
    public void removeEffect() {
        stage.getBalls().get(0).setInvincible(false);
    }
}
