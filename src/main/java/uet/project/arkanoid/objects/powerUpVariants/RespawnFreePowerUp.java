package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;

public class RespawnFreePowerUp extends PowerUp {
    private int oldLives;
    public RespawnFreePowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage, PowerUpType.RESPAWN_FREE);
        oldLives = stage.getLives();
    }

    public void applyEffect() {
        stage.setLives(2 + oldLives);
    }

    public void update(double deltaTime) {
        super.update(deltaTime);

        if (isCatchedPowerUp()) {
            if (isDead()) {
                removeEffect();
            }
        }
    }

    public void removeEffect() {
        stage.setLives(oldLives);
        alive = false;
    }
}
