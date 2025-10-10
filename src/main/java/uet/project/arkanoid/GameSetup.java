package uet.project.arkanoid;

import uet.project.arkanoid.objects.Arrow;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.PowerUp;

import java.util.List;

public class GameSetup {
    private List<Brick> bricks;
    private List<Ball> balls;
    private List<Paddle> paddles;
    private List<PowerUp> powerUps;
    private Arrow arrow = null;

    // Reference every object in the game
    public GameSetup(List<Brick> bricks,
                     List<Ball> balls,
                     List<Paddle> paddles,
                     List<PowerUp> powerUps,
                     Arrow arrow,
                     GameState currentState) {
        this.bricks = bricks;
        this.balls = balls;
        this.paddles = paddles;
        this.powerUps = powerUps;
        this.arrow = arrow;

        //Test here
        if (currentState == GameState.GAME_TEST) {
            paddles.add(new Paddle(Basis.STAGE_TEST_X - 33, 720 - 70, 210, 56, 6, 0, 0));  // 33 is the padding of the paddle.
            Paddle paddleMain = paddles.get(0);
            balls.add(new Ball(paddleMain.getX() + paddleMain.getWidth() / 2 - 25, paddleMain.getY() - 20, 50, 40, 5, 0, 0));
            Ball ballMain = balls.get(0);
            arrow.setUpArrow(ballMain.getX() - 10, ballMain.getY() - 50, 70, 60, 0, 0, 0);
        }
    }
}
