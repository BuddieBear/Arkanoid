package uet.project.arkanoid.objects.PowerUpVariants;

import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.PowerUp;

public class SmallBrickPowerUp extends PowerUp {

    public SmallBrickPowerUp(GameObject object, int width, int height) {
        super(object, width, height);
        type = PowerUpType.SMALL_BRICK;
    }

    public void applyEffect(GameObject paddle) {
    }
}
