package uet.project.arkanoid.objects.PowerUpVariants;

import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.PowerUp;

public class InvincibleBallPowerUp extends PowerUp {
    public InvincibleBallPowerUp(GameObject object, double width, double height) {
        super(object, width, height);
        type = PowerUpType.INVINCIBLE_BALL;
    }

    public void applyEffect() {
           Basis.stage.getBalls().get(0).setInvincible(true);
    }

    @Override
    public void removeEffect() {
        super.removeEffect();
        Basis.stage.getBalls().get(0).setInvincible(false);
    }
}
