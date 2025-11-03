package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.objects.PowerUp;

public class RespawnFreePowerUp extends PowerUp {
    private int oldLives;
    public RespawnFreePowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage, PowerUpType.RESPAWN_FREE);
    }

    public void applyEffect() {
        oldLives = stage.getLives();
        stage.setLives(100000);
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
