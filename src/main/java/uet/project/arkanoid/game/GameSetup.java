package uet.project.arkanoid.game;

import javafx.scene.paint.Color;
import uet.project.arkanoid.objects.*;
import uet.project.arkanoid.objects.deBuffVariants.HarderBrickPowerDown;
import uet.project.arkanoid.objects.powerUpVariants.*;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.brickVariants.NormalBrick;
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
    protected List<FloatingText> floatingTexts;

    // Objectives
    int lives;
    int score;
    int brick_streak = 0;

    private Level currentLevel;

    //time
    protected double lastTime;
    protected double deltaTime;
    protected double currentTime;

    // Constructor initializes all lists and adds test objects
    public GameSetup(Level currentStage) {
        lastTime = System.nanoTime();
        currentTime = lastTime;
        deltaTime = 0;
        // Initialize the lists
        bricks = new ArrayList<>();
        balls = new ArrayList<>();
        paddles = new ArrayList<>();
        powerUps = new ArrayList<>();
        floatingTexts = new ArrayList<>();
        this.currentLevel = currentStage;

        loadLevel(currentStage);
    }

    public void loadLevel(Level lvl) {
        this.clearLevel();
        //TODO: Switch - Case to create different Stage
        if (lvl == Level.STAGE_1) {
            lives = 5;

            paddles.add(new Paddle(Basis.STAGE_X , Basis.SCREEN_HEIGHT - 40, 130, 20, Basis.PADDLE_SPEED));  // 33 is padding
            Paddle paddleMain = paddles.get(0);

            balls.add(new Ball( paddleMain.getX() + paddleMain.getWidth() / 2,
                    paddleMain.getY() - (double) Basis.BALL_DIAMETER / 2 -  10,
                    (double) Basis.BALL_DIAMETER / 2, Basis.BALL_SPEED, this));

            MapLoader.loadBricksFromTiled(this, Basis.STAGE_1);
        } else if (lvl == Level.STAGE_2) {
            lives = 8;

            paddles.add(new Paddle(Basis.STAGE_X , Basis.SCREEN_HEIGHT - 40, 130, 20, Basis.PADDLE_SPEED));  // 33 is padding
            Paddle paddleMain = paddles.get(0);

            balls.add(new Ball( paddleMain.getX() + paddleMain.getWidth() / 2,
                    paddleMain.getY() - (double) Basis.BALL_DIAMETER / 2 - 10,
                    (double) Basis.BALL_DIAMETER / 2, Basis.BALL_SPEED, this));


            MapLoader.loadBricksFromTiled(this, Basis.STAGE_2);
        } else if (lvl == Level.STAGE_3) {
            lives = 5;

            paddles.add(new Paddle(Basis.STAGE_X , Basis.SCREEN_HEIGHT - 40, 130, 20, Basis.PADDLE_SPEED));  // 33 is padding
            Paddle paddleMain = paddles.get(0);

            balls.add(new Ball( paddleMain.getX() + paddleMain.getWidth() / 2,
                    paddleMain.getY() - (double) Basis.BALL_DIAMETER / 2 -  10,
                    (double) Basis.BALL_DIAMETER / 2, Basis.BALL_SPEED, this));


            MapLoader.loadBricksFromTiled(this, Basis.STAGE_3);
        }
    }

    public void clearLevel() {
        bricks.clear();
        balls.clear();
        paddles.clear();
        powerUps.clear();
        floatingTexts.clear();
    }
    public void addPowerUp(List<? extends Brick> bricks1) {
        for (Brick brick : bricks1) {
            if (brick.isDestroy()) {
                brick_streak++;
                // add flying texts
                floatingTexts.add(new FloatingText("+" + String.valueOf(brick.getMaxHp() * 10),
                        brick.getX() + brick.getWidth(),
                        brick.getY() + brick.getHeight() / 2,
                        Color.GREEN));

                //System.out.println("Brick Destroyed: " + brick_streak);
                if (brick_streak >= 1) {
                    brick_streak = 0;
                    int choice = (int) (Math.random() * 9);//(int) (Math.random() * 9); // 0 → 8
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
                        case 9:
                            powerUps.add(new ExtendPaddle(brick, 30, 30, this));
                            break;
                        case 10:
                            powerUps.add(new ShrinkPaddle(brick, 30, 30, this));
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

    public void updateDeltaTime() {
        currentTime = System.nanoTime();
        deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;
    }

    // Getter - Setter methods
    public List<Brick> getBricks() {
        return bricks;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public List<FloatingText> getFloatingBricks() {
        return floatingTexts;
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

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
