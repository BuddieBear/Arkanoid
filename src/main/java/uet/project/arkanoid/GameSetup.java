package uet.project.arkanoid;

import uet.project.arkanoid.Objects.Ball;
import uet.project.arkanoid.Objects.Brick;
import uet.project.arkanoid.Objects.Paddle;
import uet.project.arkanoid.Objects.PowerUp;

import java.util.List;

public class GameSetup {
    private List<Brick> bricks;
    private List<Ball> balls;
    private List<Paddle> paddles;
    private List<PowerUp> powerUps;

    // Reference every object in the game
    public GameSetup(List<Brick> bricks,
                     List<Ball> balls,
                     List<Paddle> paddles,
                     List<PowerUp> powerUps,
                     GameState currentState) {
        this.bricks = bricks;
        this.balls = balls;
        this.paddles = paddles;
        this.powerUps = powerUps;

        //Test here
        if (currentState == GameState.GAME_TEST) {
            paddles.add(new Paddle(Basis.STAGE_TEST_X, 720 - 70, 210, 56, 6));
        }

    }

}
