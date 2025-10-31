package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;

public class RespawnFreePowerUp extends PowerUp {

    public RespawnFreePowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage);
        this.type = PowerUpType.RESPAWN_FREE;
    }

    @Override
    public void applyEffect() {
        if (stage.getLives() <= 0) {
            stage.setLives(1);
        }
    }

    @Override
    public void removeEffect() {
    }
}
