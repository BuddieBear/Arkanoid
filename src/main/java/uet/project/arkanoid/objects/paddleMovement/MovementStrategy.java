package uet.project.arkanoid.objects.paddleMovement;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.Paddle;

public interface MovementStrategy {
    void move(Paddle paddle, GameSetup stage, double deltaTime);
}
