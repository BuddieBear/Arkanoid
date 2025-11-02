package uet.project.arkanoid.objects.deBuffVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.brickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.objects.Brick;

public class HarderBrickPowerDown extends PowerUp {

    public HarderBrickPowerDown(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage, PowerUpType.HARDER_BRICK);
    }

    public void applyEffect() {
        for (Brick brick : stage.getBricks()) {
            if (!(brick instanceof IndestructibleBrick)) {
                brick.setHitPoints(brick.getHitPoints() + 1);
            }
        }
    }

    public void removeEffect() {
    }
}
