package uet.project.arkanoid.game;

import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.BrickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.BrickVariants.NormalBrick;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.PowerUp;

import java.util.ArrayList;
import java.util.List;

public class GameSetup {
    protected List<Brick> bricks;
    protected List<Ball> balls;
    protected List<Paddle> paddles;
    protected List<PowerUp> powerUps;

    // Constructor initializes all lists and adds test objects
    public GameSetup(GameState currentState) {
        // Initialize the lists
        bricks = new ArrayList<>();
        balls = new ArrayList<>();
        paddles = new ArrayList<>();
        powerUps = new ArrayList<>();

        // TEST setup
        if (currentState == GameState.GAME_TEST) {
            paddles.add(new Paddle(Basis.STAGE_TEST_X - 33, 720 - 70, 210, 56, 6, 0, 0));  // 33 is padding
            Paddle paddleMain = paddles.get(0);

            balls.add(new Ball(
                    paddleMain.getX() + paddleMain.getWidth() / 2 - 25,
                    paddleMain.getY() - 20,
                    35, 35, 5, 0, 0,
                    paddleMain
            ));

            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 50, Basis.STAGE_TEST_Y + 75, 100, 75, 1));
            bricks.add(new IndestructibleBrick(Basis.STAGE_TEST_X + 150, Basis.STAGE_TEST_Y + 150, 100, 75));
            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 250, Basis.STAGE_TEST_Y + 225, 100, 75, 2));
            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 350, Basis.STAGE_TEST_Y + 300, 100, 75, 3));
        }
    }

    // Getter methods to access lists
    public List<Brick> getBricks() {
        return bricks;
    }
    public List<Ball> getBalls() {
        return balls;
    }
    public List<Paddle> getPaddles() {
        return paddles;
    }
    public List<PowerUp> getPowerUps() {
        return powerUps;
    }
}