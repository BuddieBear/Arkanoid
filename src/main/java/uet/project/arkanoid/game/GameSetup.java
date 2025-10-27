package uet.project.arkanoid.game;

import uet.project.arkanoid.objects.*;
import uet.project.arkanoid.objects.DeBuffVariants.HarderBrickPowerDown;
import uet.project.arkanoid.objects.PowerUpVariants.*;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.BrickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.BrickVariants.NormalBrick;
import uet.project.arkanoid.utils.FileManager;
import uet.project.arkanoid.utils.MapLoader;

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
    int brick_streak = 0;

    // Constructor initializes all lists and adds test objects
    public GameSetup(Level currentStage) {
        // Initialize the lists
        bricks = new ArrayList<>();
        balls = new ArrayList<>();
        paddles = new ArrayList<>();
        powerUps = new ArrayList<>();
        MapLoader mapLoader = new MapLoader();

        //TODO: Switch - Case to create different Stage
        if (currentStage == Level.STAGE_1) {
            lives = 5;

            paddles.add(new Paddle(Basis.STAGE_X - 33, Basis.SCREEN_HEIGHT - 40, 130, 20, Basis.PADDLE_SPEED));  // 33 is padding
            Paddle paddleMain = paddles.get(0);

            balls.add(new Ball(
                    paddleMain.getX() + (int)paddleMain.getWidth() / 2 - 25,
                    paddleMain.getY() - 20, Basis.BALL_DIAMETER, Basis.BALL_DIAMETER,
                    Basis.BALL_SPEED, this
            ));

            mapLoader.loadBricksFromTiled(this, Basis.STAGE_1);
        }
    }

    public void addPowerUp(List<? extends Brick> bricks1) {
        for (Brick brick : bricks1) {
            if (brick.isDestroy()) {
                brick_streak++;
                System.out.println("Brick Destroyed: " + brick_streak);
                if (brick_streak >= 3) {
                    brick_streak = 0;
                    int choice = (int) (Math.random() * 9); // 0 → 8
                    switch (choice) {
                        case 0:
                            powerUps.add(new DamageBrickPowerUp(brick, 30, 30, this));
                            break;
                        case 1:
                            powerUps.add(new InvincibleBallPowerUp(brick, 30, 30, this));
                            break;
                        case 2:
                            powerUps.add(new MultiBallPowerUp(brick, 30, 30, this));
                            break;
                        case 3:
                            powerUps.add(new SmallBrickPowerUp(brick, 30, 30, this));
                            break;
                        case 4:
                            powerUps.add(new SuperBallPowerUp(brick, 30, 30, this));
                            break;
                        case 5:
                            powerUps.add(new HarderBrickPowerDown(brick, 30, 30, this));
                            break;
                        case 6:
                            powerUps.add(new ExtraLifePowerUp(brick, 30, 30, this));
                            break;
                        case 7:
                            powerUps.add(new DoubleScorePowerUp(brick, 30, 30, this));
                            break;
                        case 8:
                            powerUps.add(new RespawnFreePowerUp(brick, 30, 30, this));
                            break;
                    }
                }
            }
        }
    }


    public boolean gameLose() {
        if (lives > 0) {
            return false;
        }
        AudioSet.gameOverSound.play();
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
