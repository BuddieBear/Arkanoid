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
    protected List<Ammo> ammos;
    protected List<Chest> chests;
    protected List<FloatingText> floatingTexts;

    // Objectives
    private int lives;
    private int score;

    private int brick_streak = 0;
    private int scorePerHp = 10;
    private final int Base_ScorePerHp = 10;

    private Level currentLevel;
    private GameState currentState;

    //time
    protected double lastTime;
    protected double deltaTime;
    protected double currentTime;

    // Constructor initializes all lists and adds test objects
    public GameSetup(Level currentStage, GameState currentState) {
        lastTime = System.nanoTime();
        currentTime = lastTime;
        deltaTime = 0;
        // Initialize the lists
        this.bricks = new ArrayList<>();
        this.balls = new ArrayList<>();
        this.paddles = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.chests = new ArrayList<>();
        this.ammos = new ArrayList<>();

        floatingTexts = new ArrayList<>();
        this.currentLevel = currentStage;
        this.currentState = currentState;
        loadLevel(currentStage);
    }

    public void loadLevel(Level lvl) {
        this.clearLevel();

        //TODO: Switch - Case to create different Stage

        if (lvl == Level.STAGE_1) {
            lives = 5;
            chests.add(new Chest(500, 500, 50, 50, this));
            paddles.add(new Paddle(Basis.STAGE_X , Basis.SCREEN_HEIGHT - 40, 130, 20, Basis.PADDLE_SPEED));  // 33 is padding
            Paddle paddleMain = paddles.get(0);

            balls.add(new Ball( paddleMain.getX() + paddleMain.getWidth() / 2,
                    paddleMain.getY() - (double) Basis.BALL_DIAMETER / 2 -  10,
                    (double) Basis.BALL_DIAMETER / 2, Basis.BALL_SPEED, this));

            MapLoader.loadBricksFromTiled(this, Basis.STAGE_1);
        } else if (lvl == Level.STAGE_2) {
            lives = 8;
            chests.add(new Chest(500, 500, 50, 50, this));
            paddles.add(new Paddle(Basis.STAGE_X , Basis.SCREEN_HEIGHT - 40, 130, 20, Basis.PADDLE_SPEED));  // 33 is padding
            Paddle paddleMain = paddles.get(0);

            balls.add(new Ball( paddleMain.getX() + paddleMain.getWidth() / 2,
                    paddleMain.getY() - (double) Basis.BALL_DIAMETER / 2 - 10,
                    (double) Basis.BALL_DIAMETER / 2, Basis.BALL_SPEED, this));


            MapLoader.loadBricksFromTiled(this, Basis.STAGE_2);
        } else if (lvl == Level.STAGE_3) {
            lives = 5;
            chests.add(new Chest(500, 500, 50, 50, this));
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
        ammos.clear();
        chests.clear();
        floatingTexts.clear();
    }

    public void addPowerUp(List<? extends Brick> bricks1) {
        for (Brick brick : bricks1) {
            if (brick.isDestroy()) {
                brick_streak++;

                if (brick_streak >= 3) {
                    // add flying texts
                    floatingTexts.add(new FloatingText("+" + String.valueOf(brick.getMaxHp() * 10),
                            brick.getX() + brick.getWidth(),
                            brick.getY() + brick.getHeight() / 2,
                            Color.GREEN));

                    //System.out.println("Brick Destroyed: " + brick_streak);
                    if (brick_streak >= 1) {
                        brick_streak = 0;
                        int choice = (int) (Math.random() * 11); // 0 → 10

                        PowerUp newPowerUp = switch (choice) {
                            case 0 -> new DamageBrickPowerUp(brick, 30, 30, this);
                            case 1 -> new InvincibleBallPowerUp(brick, 30, 30, this);
                            case 2 -> new MultiBallPowerUp(brick, 30, 30, this);
                            case 3 -> new SuperBallPowerUp(brick, 30, 30, this);
                            case 4 -> new HarderBrickPowerDown(brick, 30, 30, this);
                            case 5 -> new ExtraLifePowerUp(brick, 30, 30, this);
                            case 6 -> new DoubleScorePowerUp(brick, 30, 30, this);
                            case 7 -> new RespawnFreePowerUp(brick, 30, 30, this);
                            case 8 -> new ExtendPaddle(brick, 30, 30, this);
                            case 9 -> new ShrinkPaddle(brick, 30, 30, this);
                            default -> null;
                        };

                        if (newPowerUp != null) {
                            // Check if same type is already active
                            PowerUp existing = powerUps.stream()
                                    .filter(p -> p.getType() == newPowerUp.getType()
                                            && p.isCatchedPowerUp()).findFirst().orElse(null);

                            if (existing != null) {
                                // Extend or refresh duration instead of adding a new one
                                existing.extendDuration(existing.getEffectDurationMillis());
                            } else {
                                powerUps.add(newPowerUp);
                            }
                        }
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

    public void resumeGame() {
        this.currentState = GameState.PLAYING;
    }

    // Getter - Setter methods
    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    public List<Chest> getChests() {
        return chests;
    }

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

    public int getBase_ScorePerHp() {
        return Base_ScorePerHp;
    }

    public int getScorePerHp() {
        return scorePerHp;
    }

    public void setScorePerHp(int scorePerHp) {
        this.scorePerHp = scorePerHp;
    }

    public Paddle getPaddle() {
        return paddles.get(0);
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public List<Ammo> getAmmos() {
        return ammos;
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
