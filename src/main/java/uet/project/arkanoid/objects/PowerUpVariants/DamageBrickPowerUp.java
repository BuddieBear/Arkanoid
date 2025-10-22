package uet.project.arkanoid.objects.PowerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.BrickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.objects.Brick;

public class DamageBrickPowerUp extends PowerUp {
    public DamageBrickPowerUp(GameObject object, double width, double height,  GameSetup stage) {
        super(object, width, height, stage);
        type = PowerUpType.DAMAGE_BRICK;
    }

    public void applyEffect() {
        for (Brick brick : stage.getBricks()) {
            if (!(brick instanceof IndestructibleBrick)) {
                brick.takeHit();
            }
        }
    }

    public void removeEffect() {
    }
}
