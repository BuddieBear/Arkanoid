package uet.project.arkanoid.game;

import javafx.scene.paint.Color;
import uet.project.arkanoid.objects.*;
import uet.project.arkanoid.objects.deBuffVariants.HarderBrickPowerDown;
import uet.project.arkanoid.objects.deBuffVariants.ShrinkPaddle;
import uet.project.arkanoid.objects.powerUpVariants.*;
import uet.project.arkanoid.utils.*;
import uet.project.arkanoid.objects.brickVariants.NormalBrick;

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
	protected List<Boss> bosses = new ArrayList<>();
	protected List<FloatingText> floatingTexts;
	protected Thunder thunder;

	private int lives;
	private int score;

	private int brick_streak = 0;
	private int scorePerHp = 10;
	private final int Base_ScorePerHp = 10;

	private Level currentLevel;
	private GameState currentState;

	private long lastBossSpawnTime;
	private final long BOSS_SPAWN_TIME = 10_000; // 20 seconds
	private long lastBossTime;
	private final long BOSS_SEQUENCE_TIME = 10_000;

	//time
	protected double lastTime;
	protected double deltaTime;
	protected double currentTime;

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
		this.thunder = new Thunder();

		floatingTexts = new ArrayList<>();
		this.currentLevel = currentStage;
		this.currentState = currentState;
		this.lastBossSpawnTime = System.currentTimeMillis();
		this.lastBossTime = 0;

		loadLevel(currentStage);
	}

	public void loadLevel(Level lvl) {
		this.clearLevel();

		//TODO: Switch - Case to create different Stage

		if (lvl == Level.STAGE_1) {
			lives = 10;
			paddles.add(new Paddle(Basis.STAGE_X, Basis.SCREEN_HEIGHT - 40, 150, 20, Basis.PADDLE_SPEED,
					this));  // 33 is padding
			Paddle paddleMain = paddles.get(0);

			balls.add(new Ball(paddleMain.getX() + paddleMain.getWidth() / 2,
					paddleMain.getY() - (double) Basis.BALL_DIAMETER / 2 - 10,
					(double) Basis.BALL_DIAMETER / 2, Basis.BALL_SPEED, this));

			MapLoader.loadBricksFromTiled(this, Basis.STAGE_1);
		} else if (lvl == Level.STAGE_2) {
			lives = 10;
			paddles.add(new Paddle(Basis.STAGE_X, Basis.SCREEN_HEIGHT - 40, 150, 20, Basis.PADDLE_SPEED,
					this));  // 33 is padding
			Paddle paddleMain = paddles.get(0);
			balls.add(new Ball(paddleMain.getX() + paddleMain.getWidth() / 2,
					paddleMain.getY() - (double) Basis.BALL_DIAMETER / 2 - 10,
					(double) Basis.BALL_DIAMETER / 2, Basis.BALL_SPEED, this));

			MapLoader.loadBricksFromTiled(this, Basis.STAGE_2);
		} else if (lvl == Level.STAGE_3) {
			lives = 10;
			paddles.add(new Paddle(Basis.STAGE_X, Basis.SCREEN_HEIGHT - 40, 150, 20, Basis.PADDLE_SPEED,
					this));  // 33 is padding
			Paddle paddleMain = paddles.get(0);

			balls.add(new Ball(paddleMain.getX() + paddleMain.getWidth() / 2,
					paddleMain.getY() - (double) Basis.BALL_DIAMETER / 2 - 10,
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
		bosses.clear();
	}

	public void updateBosses(double deltaTime) {
		long currentTime = System.currentTimeMillis();

		// Spawn boss every 20 seconds
		if (currentTime - lastBossSpawnTime >= BOSS_SPAWN_TIME) {
			spawnBoss();
			lastBossSpawnTime = currentTime;
		}

		for (Boss boss : bosses) {
			boss.update(deltaTime);
		}

		bosses.removeIf(Boss::isDead);
	}

	private void spawnBoss() {
		// Spawn boss at random position
		double x = Basis.STAGE_X + Math.random() * (Basis.STAGE_WIDTH - 80);
		double y = 50 + Math.random() * 200;

		Boss newBoss = new Boss(x, y, 80, 60);
		bosses.add(newBoss);

		floatingTexts.add(new FloatingText("BOSS SPAWNED!",
				Basis.SCREEN_WIDTH / 2 - 100, Basis.SCREEN_HEIGHT / 2, Color.RED));
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

					if (brick_streak >= 1) {
						brick_streak = 0;
						int choice = (int) (Math.random() * 10); // 0 → 10

						PowerUp newPowerUp = switch (choice) {
							case 0 -> new SuperBallPowerUp(brick, 30, 30, this);
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

	public boolean canStartBoss() {
		long currentTime = System.currentTimeMillis();
		return !bosses.isEmpty() &&
				(currentTime - lastBossTime >= BOSS_SEQUENCE_TIME);
	}

	public void setLastBossTime() {
		this.lastBossTime = System.currentTimeMillis();
	}

	public boolean gameLose() {
		if (lives > 0) {
			return false;
		}
		AudioSet.gameOverSound.play();
		FileManager.saveScore(this.score);

		int level = getLevelHighScore();
		if (level > 0) {
			HighScore newHighScore = new HighScore();
			newHighScore.saveNewHighScore(level, this.score);
		}
		return true;
	}

	public boolean gameWin() {
		for (Brick brick : this.bricks) {
			if (brick instanceof NormalBrick) {
				return false;
			}
		}
		FileManager.saveScore(this.score);
		System.out.println("YOU WON!");

		int level = getLevelHighScore();
		if (level > 0) {
			HighScore newHighScore = new HighScore();
			newHighScore.saveNewHighScore(level, this.score);
		}
		return true;
	}

	// Resume Game
	public void resumeGame() {
		this.currentState = GameState.PLAYING;
	}

	public int getLevelHighScore() {
		if (currentLevel == Level.STAGE_1) {
			return 1;
		} else if (currentLevel == Level.STAGE_2) {
			return 2;
		} else if (currentLevel == Level.STAGE_3) {
			return 3;
		}
		return 0;
	}

	// Getter - Setter methods
	public Thunder getThunder() {
		return thunder;
	}

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

	public List<Boss> getBosses() {
		return bosses;
	}

	// XÓA: public List<Trace> getTraces()

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
