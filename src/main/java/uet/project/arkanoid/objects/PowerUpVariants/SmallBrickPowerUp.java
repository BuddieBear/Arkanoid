package uet.project.arkanoid.objects.PowerUpVariants;

import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.BrickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.PowerUp;

public class SmallBrickPowerUp extends PowerUp {

    public SmallBrickPowerUp(GameObject object,  double width, double height) {
        super(object, width, height);
        type = PowerUpType.SMALL_BRICK;
    }

    public void applyEffect() {
        for (Brick brick : Basis.stage.getBricks()) {
            if (!(brick instanceof IndestructibleBrick)) {
                brick.setX((int)(brick.getX() + getWidth() / 4));
                brick.setY((int)(brick.getY() + getHeight() / 4));
                brick.setHeight(brick.getHeight() / 1.2);
                brick.setWidth(brick.getWidth() / 1.2);//  BUG LOL WAIT
            }
        }
    }

    @Override
    public void removeEffect() {
        for (Brick brick : Basis.stage.getBricks()) {
            if (!(brick instanceof IndestructibleBrick)) {
                brick.setX((int)(brick.getX() - getWidth() / 4));
                brick.setY((int)(brick.getY() - getHeight() / 4));
                brick.setHeight(brick.getHeight() * 1.2);
                brick.setWidth(brick.getWidth() * 1.2);
            }
        }
    }
}
