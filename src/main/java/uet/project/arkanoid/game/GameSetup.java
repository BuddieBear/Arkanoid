package uet.project.arkanoid.game;

import uet.project.arkanoid.objects.*;
import uet.project.arkanoid.objects.DeBuffVariants.HarderBrickPowerDown;
import uet.project.arkanoid.objects.PowerUpVariants.*;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.BrickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.BrickVariants.NormalBrick;
import uet.project.arkanoid.utils.FileManager;

import java.util.ArrayList;
import java.util.List;

public class GameSetup {
    // Objects in stage
    protected List<Brick> bricks;
    protected List<Ball> balls;
    protected List<Paddle> paddles;
    protected List<PowerUp> powerUps;

    // Objectives
    int lives;
    int score;

    // Constructor initializes all lists and adds test objects
    public GameSetup(GameState.Stage currentStage) {
        // Initialize the lists
        bricks = new ArrayList<>();
        balls = new ArrayList<>();
        paddles = new ArrayList<>();
        powerUps = new ArrayList<>();

        // TEST setup
        if (currentStage == GameState.Stage.STAGE_TEST) {
            lives = 3;
            paddles.add(new Paddle(Basis.STAGE_TEST_X - 33, 720 - 70, 210, 56, Basis.PADDLE_SPEED));  // 33 is padding
            Paddle paddleMain = paddles.get(0);
            balls.add(new Ball(
                    paddleMain.getX() + (int)paddleMain.getWidth() / 2 - 25,
                    paddleMain.getY() - 20, Basis.BALL_DIAMETER, Basis.BALL_DIAMETER,
                    Basis.BALL_SPEED, this
            ));

            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 50, Basis.STAGE_TEST_Y + 75, 100, 75, 1));
            bricks.add(new IndestructibleBrick(Basis.STAGE_TEST_X + 150, Basis.STAGE_TEST_Y + 150, 100, 75));
            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 250, Basis.STAGE_TEST_Y + 225, 100, 75, 2));
            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 350, Basis.STAGE_TEST_Y + 300, 100, 75, 3));

            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 450, Basis.STAGE_TEST_Y + 300, 100, 75, 1));
            bricks.add(new IndestructibleBrick(Basis.STAGE_TEST_X + 550, Basis.STAGE_TEST_Y + 225, 100, 75));
            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 650, Basis.STAGE_TEST_Y + 150, 100, 75, 2));
            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 750, Basis.STAGE_TEST_Y + 75, 100, 75, 3));

            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 250, Basis.STAGE_TEST_Y + 30, 100, 75, 1));
            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 350, Basis.STAGE_TEST_Y + 30, 100, 75, 2));
            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 450, Basis.STAGE_TEST_Y + 30, 100, 75, 2));
            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 550, Basis.STAGE_TEST_Y + 30, 100, 75, 3));
            bricks.add(new NormalBrick(Basis.STAGE_TEST_X + 650, Basis.STAGE_TEST_Y + 30, 100, 75, 1));

        }
    }

    public void addPowerUp(List<? extends Brick> bricks1) {
        for (Brick brick : bricks1) {
            if (brick.isDestroy()) {
                int choice = (int)(Math.random() * 6); // 0 → 5
                switch (choice) {
                    case 0:
                        powerUps.add(new DamageBrickPowerUp(brick, 30, 30));
                        break;
                    case 1:
                        powerUps.add(new InvincibleBallPowerUp(brick, 30, 30));
                        break;
                    case 2:
                        powerUps.add(new MultiBallPowerUp(brick, 30, 30));
                        break;
                    case 3:
                        powerUps.add(new SmallBrickPowerUp(brick, 30, 30));
                        break;
                    case 4:
                        powerUps.add(new SuperBallPowerUp(brick, 30, 30));
                        break;
                    case 5:
                        powerUps.add(new HarderBrickPowerDown(brick, 30, 30));
                        break;
                }
            }
        }
    }


    public boolean gameLose() {
        if (lives > 0) {
            return false;
        }
        System.out.println("YOU LOST!");
        FileManager.saveScore(this.score);
        return true;
    }
    public boolean gameWin() {
        for (Brick brick: this.bricks) {
            if (brick instanceof NormalBrick) {
                return false;
            }
        }
        FileManager.saveScore(this.score);
        System.out.println("YOU WON!");
        return true;
    }


    // Getter - Setter methods
    public List<Brick> getBricks() {
        return bricks;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public List<Paddle> getPaddles() {
        return paddles;
    }

    public Paddle getPaddle() {
        return paddles.get(0);
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int extra) {
        this.score = this.score + extra;
    }
}
