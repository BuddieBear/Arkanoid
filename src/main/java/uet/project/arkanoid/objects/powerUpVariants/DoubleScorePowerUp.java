package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;

public class DoubleScorePowerUp extends PowerUp {

    public DoubleScorePowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage);
        this.type = PowerUpType.DOUBLE_SCORE;
    }

    @Override
    public void applyEffect() {
        stage.addScore(stage.getScore());
    }

    @Override
    public void removeEffect() {
    }
}
