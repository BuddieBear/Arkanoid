package uet.project.arkanoid.objects.DeBuffVariants;

import uet.project.arkanoid.objects.BrickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.objects.Brick;

public class HarderBrickPowerDown extends PowerUp {

    public HarderBrickPowerDown(GameObject object, double width, double height) {
        super(object, width, height);
        type = PowerUpType.HARDER_BRICK;
    }

    public void applyEffect() {
        for (Brick brick : Basis.stage.getBricks()) {
            if (!(brick instanceof IndestructibleBrick)) {
                brick.setHitPoints(brick.getHitPoints() + 1);
            }
        }
    }

    public void removeEffect() {
    }
}
