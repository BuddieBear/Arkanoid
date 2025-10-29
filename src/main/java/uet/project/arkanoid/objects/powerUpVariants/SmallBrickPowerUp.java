package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.brickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;

public class SmallBrickPowerUp extends PowerUp {

    public SmallBrickPowerUp(GameObject object,  double width, double height, GameSetup stage) {
        super(object, width, height, stage);
        type = PowerUpType.SMALL_BRICK;
    }

    public void applyEffect() {
        for (Brick brick : stage.getBricks()) {
            if (!(brick instanceof IndestructibleBrick)) {
                brick.setWidth((int)(brick.getOriginalWidth() / 1.2));
                brick.setHeight((int)(brick.getOriginalHeight() / 1.2));
            }
        }
    }

    public void removeEffect() {
        for (Brick brick : stage.getBricks()) {
            if (!(brick instanceof IndestructibleBrick)) {
                brick.setWidth(brick.getOriginalWidth());
                brick.setHeight(brick.getOriginalHeight());
            }
        }
    }
}
