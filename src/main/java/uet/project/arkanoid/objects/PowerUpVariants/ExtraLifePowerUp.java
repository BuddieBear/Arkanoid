package uet.project.arkanoid.objects.PowerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.PowerUp;

public class ExtraLifePowerUp extends PowerUp {

    public ExtraLifePowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage);
        this.type = PowerUpType.EXTRA_LIFE;
    }

    @Override
    public void applyEffect() {
        stage.setLives(stage.getLives() + 1);
    }

    @Override
    public void removeEffect() {
    }
}