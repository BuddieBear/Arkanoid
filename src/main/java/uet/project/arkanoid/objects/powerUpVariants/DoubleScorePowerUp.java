package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;

public class DoubleScorePowerUp extends PowerUp {

    public DoubleScorePowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage, PowerUpType.DOUBLE_SCORE);
    }

    @Override
    public void applyEffect() {
        stage.setScorePerHp(stage.getBase_ScorePerHp()*2);
    }

    @Override
    public void removeEffect() {
        stage.setScorePerHp(stage.getBase_ScorePerHp());
        alive = false;
    }
}
