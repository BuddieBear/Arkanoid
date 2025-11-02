package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.brickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.objects.Brick;

public class DamageBrickPowerUp extends PowerUp {
    public DamageBrickPowerUp(GameObject object, double width, double height,  GameSetup stage) {
        super(object, width, height, stage, PowerUpType.DAMAGE_BRICK);
    }

    public void applyEffect() {
        for (Brick brick : stage.getBricks()) {
            if ( Math.random() * 2 < 0.5) {
                continue;
            }
            if (!(brick instanceof IndestructibleBrick)) {
                brick.takeHit();
            }
        }
    }

    public void removeEffect() {
        alive = false;
    }
}
