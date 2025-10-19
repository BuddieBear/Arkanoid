package uet.project.arkanoid.objects.PowerUpVariants;

import uet.project.arkanoid.objects.BrickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.objects.Brick;

public class MultiBallPowerUp extends PowerUp {

    public MultiBallPowerUp(GameObject object, double width, double height) {
        super(object, width, height);
        type = PowerUpType.DAMAGE_BRICK;
    }

    public void applyEffect() {

    }
}
