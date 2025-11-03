package uet.project.arkanoid;

import com.almasb.fxgl.audio.Audio;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import uet.project.arkanoid.game.*;
import uet.project.arkanoid.objects.*;
import uet.project.arkanoid.objects.paddleMovement.AIMovement;
import uet.project.arkanoid.objects.paddleMovement.MovementStrategy;
import uet.project.arkanoid.objects.paddleMovement.PlayerMovement;
import uet.project.arkanoid.ui.MenuView;
import uet.project.arkanoid.ui.Setting;
import uet.project.arkanoid.ui.PausedMenuView;
import uet.project.arkanoid.ui.LoadScreenView;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.game.GameView;
import uet.project.arkanoid.game.Level;
import uet.project.arkanoid.ui.*;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Ammo;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.utils.MapLoader;

import java.util.HashSet;
import java.util.Set;

public class GameManager extends Application {

    // Places to render objects
    private final Group root = new Group();
    private final Canvas canvas = new Canvas(Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    AnimationTimer gameLoop;

    // HandleInput
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    private boolean autoMovePaddle = false;
    private boolean BossActive = false;
    private int currentBrickFalls = 0;
    private long lastBrickSpawnTime;
    private final MovementStrategy playerStrategy = new PlayerMovement();
    private final MovementStrategy aiStrategy = new AIMovement();

    // Game and Stage setup
    public GameState currentState = GameState.MENU;
    public GameState previousState = GameState.MENU;
    public Level currentLevel = Level.STAGE_1;

    GameSetup stage;

    // Renderer for each GameState
    GameView renderGame;
    MenuView renderMenu;
    Setting renderSetting;
    Instruction renderInstruction;
    Help renderHelp;
    Power_Up_View renderPower_Up_View;
    BrickView renderBrickInstruction;
    LevelPlay renderLevel;
    PausedMenuView renderPausedMenu;
    LoadScreenView renderLoadScreen;
    ChestMenu renderChestMenu;

    public static void main(String[] args) {
        Application.launch(GameManager.class);
    }

    /**
     * Initializes and starts the main game window.
     * <p>
     * This method is automatically called when the JavaFX application launches. It sets up the
     * {@link Scene} and {@link Canvas} for drawing, registers input handlers, initializes game
     * state and renderers, and starts the {@link AnimationTimer} game loop.
     * </p>
     *
     * @param primaryStage the primary window (stage) for the application
     */
    @Override
    public void start(Stage primaryStage) {
        // Create a scene
        Scene scene = new Scene(root, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

        // Set up the main window
        primaryStage.setTitle("Arkanoid");
        primaryStage.setScene(scene);
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newWidth = newVal.doubleValue();
            double newHeight = newWidth * 9.0 / 16.0;  // Keep 16:9
            primaryStage.setHeight(newHeight);
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newHeight = newVal.doubleValue();
            double newWidth = newHeight * 16.0 / 9.0;  // Keep 16:9
            primaryStage.setWidth(newWidth);
        });

        // Set up canvas for drawing
        root.getChildren().add(canvas);
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());

        // Set up stage
        stage = new GameSetup(currentLevel, currentState);

        // Set up input
        handleInput(scene);

        // Set up renderer for Game
        renderGame = new GameView(stage);
        renderMenu = new MenuView();
        renderSetting = new Setting();
        renderInstruction = new Instruction();
        renderHelp = new Help();
        renderPower_Up_View = new Power_Up_View();
        renderBrickInstruction = new BrickView();
        renderLevel = new LevelPlay();
        renderPausedMenu = new PausedMenuView();
        renderLoadScreen = new LoadScreenView();
        renderChestMenu = new ChestMenu();

        AudioSet.playBackgroundMusic();
        // Game loop
        gameLoop = new AnimationTimer() {
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) { //TODO: Set up delta time
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                deltaTime = Math.min(deltaTime, 0.05); // max 50 ms

                if (currentState == GameState.EXIT) {
                    gameLoop.stop();
                    Platform.exit();
                }

                processInput();
                update(deltaTime);
                render();
            }
        };

        render();
        gameLoop.start(); //Run Game Loop
        primaryStage.show();
    }

    /**
     * Update game logic for the PLAYING state.
     * <p>
     * This method runs per frame from the game loop. It:
     * <ul>
     *   <li>Checks win/lose conditions</li>
     *   <li>Updates paddles, balls, power-ups, bosses, ammo, floating texts, chests, and bricks</li>
     *   <li>Handles boss spawn / boss sequences</li>
     *   <li>Removes marked-for-removal objects from collections</li>
     *   <li>Syncs the {@code currentState} with the {@link GameSetup}</li>
     * </ul>
     * </p>
     *
     * @param deltaTime time elapsed since last frame in seconds
     */
    public void update(double deltaTime) {
        if (currentState == GameState.PLAYING) {
            if (stage.gameWin() || stage.gameLose()) {
                currentState = GameState.GAME_OVER;
                return;
            }

            stage.setCurrentState(GameState.PLAYING);
            // TODO: Runs update() on every GameObject.
            for (Paddle paddle : stage.getPaddles()) {
                paddle.update(deltaTime);
                paddle.Collision(stage.getBricks());
            }
            for (Ball ball : stage.getBalls()) {
                if (ball.getLaunchState()) {
                    ball.Collision(stage.getBricks());
                }
                ball.Collision(stage.getPaddles());
                ball.update(deltaTime);
            }
            stage.addPowerUp(stage.getBricks());

            for (PowerUp powerUp : stage.getPowerUps()) {
                powerUp.update(stage.getFloatingBricks(), deltaTime);
            }

            for (Boss boss : stage.getBosses()) {
                boss.update(deltaTime);
            }

            for (FloatingText floatingText : stage.getFloatingBricks()) {
                floatingText.update(deltaTime);
            }

            for (Ammo ammo : stage.getAmmos()) {
                ammo.Collision(stage.getBricks());
                ammo.update(deltaTime);
            }

            stage.updateBosses(deltaTime);

            if (stage.canStartBoss() && !BossActive) {
                startBoss();
                stage.setLastBossTime(); // Reset cooldown
            }

            if (BossActive && currentBrickFalls < stage.getBricks().size()) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastBrickSpawnTime >= 50) { // 50ms giữa mỗi brick
                    spawnNextBrick();
                    lastBrickSpawnTime = currentTime;
                    currentBrickFalls++;
                }
            }

            // Reset boss sequence khi đã spawn hết brick
            if (BossActive && currentBrickFalls >= stage.getBricks().size()) {
                BossActive = false;
            }

            for (Brick brick : stage.getBricks()) {
                brick.update(deltaTime);
                if (brick.isDestroy()) {
                    stage.addScore(brick.getMaxHp() * 10);
                }
            }

            for (Chest chest : stage.getChests()) {
                if (chest.collision(stage.getBalls())) {
                    renderChestMenu.openChestMenu(stage);
                }
                chest.update(deltaTime);
            }

            stage.getThunder().update();

            stage.getBosses().removeIf(Boss::isDead);
            stage.getBalls().removeIf(Ball::isMarkedForRemoval);
            stage.getPowerUps().removeIf(PowerUp::isDead);
            stage.getBricks().removeIf(Brick::isDestroy);
            stage.getAmmos().removeIf(Ammo::getIsDestroy);
            stage.getChests().removeIf(Chest::hasOpened);
            stage.getFloatingBricks().removeIf(FloatingText::isExpired);

            currentState = stage.getCurrentState();
        }
    }

    /**
     * Stop the game loop, halt audio, clear stage objects and canvas.
     * <p>
     * This should be called when the application is closing or the game needs a full cleanup.
     * </p>
     */
    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }

        try {
            uet.project.arkanoid.utils.AudioSet.stopAllSounds();
        } catch (Exception e) {
            System.err.println("Warning: Failed to stop sounds - " + e.getMessage());
        }

        // Clear stage objects for safety
        if (stage != null) {
            stage.clearLevel();
        }
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        System.out.println("Game stopped and cleaned up successfully.");
    }

    /**
     * Start the boss sequence: enable sequence flag and reset spawn Falls/time.
     * <p>
     * This prepares the manager to spawn bricks that will move as part of the boss sequence.
     * </p>
     */
    private void startBoss() {
        BossActive = true;
        currentBrickFalls = 0;
        lastBrickSpawnTime = System.currentTimeMillis();
    }


    /**
     * Spawn the next brick for the boss sequence.
     * <p>
     * This method randomly decides (by probability) whether to start movement on the next brick. If
     * the selected brick is valid and not already moving, it will be instructed to start moving
     * towards the paddle position.
     * </p>
     */
    private void spawnNextBrick() {
        if (Math.random() * 4 < 0.5) { // 50% tỉ lệ
            if (currentBrickFalls >= stage.getBricks().size()) {
                return;
            }

            Brick brick = stage.getBricks().get(currentBrickFalls);
            if (!brick.isDestroy() && !brick.isMovementActivated()) {
                Paddle paddle = stage.getPaddles().get(0);

                brick.startBossMovement(
                    paddle.getX() + paddle.getWidth() / 2,
                    paddle.getY() + paddle.getHeight() / 2,
                    200
                );

                System.out.println("Brick " + currentBrickFalls + " started movement");
            }
        }
    }

    /**
     * Render the current game state.
     * <p>
     * This method handles scaling the canvas to the current window size and delegates drawing to
     * the appropriate UI/renderer for the current {@link GameState}.
     * </p>
     */
    public void render() {
        double scaleX = canvas.getWidth() / Basis.SCREEN_WIDTH;
        double scaleY = canvas.getHeight() / Basis.SCREEN_HEIGHT;

        gc.save();

        if (currentState != GameState.PAUSED
            && currentState != GameState.CHEST_MENU
            && currentState != GameState.GAME_OVER) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }

        gc.scale(scaleX, scaleY);

        if (currentState == GameState.MENU) {
            renderMenu.onDraw(gc);
        } else if (currentState == GameState.SETTING) {
            renderSetting.onDraw(gc);
        } else if (currentState == GameState.INSTRUCTION) {
            renderInstruction.onDraw(gc);
        } else if (currentState == GameState.HELP) {
            renderHelp.onDraw(gc);
        } else if (currentState == GameState.POWER_UP) {
            renderPower_Up_View.onDraw(gc);
            renderPower_Up_View.onDraw(gc, canvas);
        } else if (currentState == GameState.BRICK_VIEW) {
            renderBrickInstruction.onDraw(gc);
            renderBrickInstruction.onDraw(gc, canvas);
        } else if (currentState == GameState.PLAYING) {
            renderGame.onDraw(gc);
        } else if (currentState == GameState.PAUSED) {
            renderGame.onDraw(gc);
            renderPausedMenu.onDraw(gc);
        } else if (currentState == GameState.LEVEL) {
            renderLevel.onDraw(gc);
        } else if (currentState == GameState.GAME_OVER) {
            renderGame.onDraw(gc);
            GameOverView.OnDraw(gc, stage);
        } else if (currentState == GameState.LOAD_GAME) {
            renderMenu.onDraw(gc);
            renderLoadScreen.onDraw(gc);
        } else if (currentState == GameState.CHEST_MENU) {
            renderGame.onDraw(gc);
            renderChestMenu.onDraw(gc);
        }

        gc.restore();
    }

    private void handleInput(Scene scene) {
        //Keyboard input tracking
        scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        //Mouse input
        canvas.setOnMousePressed(event -> {
            double scaleX = canvas.getWidth() / Basis.SCREEN_WIDTH;
            double scaleY = canvas.getHeight() / Basis.SCREEN_HEIGHT;
            double mouseX = event.getX() / scaleX;
            double mouseY = event.getY() / scaleY;
            System.out.println(mouseX + " " + mouseY);

            GameState beforeState = currentState;
            if (currentState == GameState.MENU) {
                currentState = renderMenu.handleClick(mouseX, mouseY, stage);
            } else if (currentState == GameState.PAUSED) {
                currentState = renderPausedMenu.handleClick(mouseX, mouseY, stage);
            } else if (currentState == GameState.LOAD_GAME) {
                currentState = renderLoadScreen.handleClick(mouseX, mouseY, stage);
            } else if (currentState == GameState.LEVEL) {
                int level = LevelPlay.selectLevel(mouseX, mouseY);
                if (level == 1) {
                    currentLevel = Level.STAGE_1;
                } else if (level == 2) {
                    currentLevel = Level.STAGE_2;
                } else if (level == 3) {
                    currentLevel = Level.STAGE_3;
                } else if (level == 4) {
                    currentState = GameState.MENU;
                    return;
                } else {
                    return;
                }

                stage = new GameSetup(currentLevel, currentState);
                renderGame = new GameView(stage);
                currentState = GameState.PLAYING;
                BossActive = false;
                currentBrickFalls = 0;
                lastBrickSpawnTime = 0;
            } else if (currentState == GameState.PLAYING) {
                // add ammo by clicking
                if (stage.getScore() >= 20) {
                    stage.getAmmos().add(new Ammo(stage.getPaddles().get(0).getX()
                        + stage.getPaddles().get(0).getWidth() / 2 - 10,
                        stage.getPaddles().get(0).getY(),
                        30, 30));
                    stage.setScore(stage.getScore() - 20);
                }
            } else if (currentState == GameState.SETTING) {
                if (Setting.back(mouseX, mouseY)) {
                    currentState = previousState;
                } else {
                    currentState = renderSetting.handleClick(mouseX, mouseY, stage);
                }
            } else if (currentState == GameState.INSTRUCTION) {
                currentState = renderInstruction.handleClick(mouseX, mouseY, stage);
            } else if (currentState == GameState.HELP) {
                currentState = renderHelp.handleClick(mouseX, mouseY, stage);
            } else if (currentState == GameState.POWER_UP) {
                currentState = renderPower_Up_View.handleClick(mouseX, mouseY, stage);
            } else if (currentState == GameState.BRICK_VIEW) {
                currentState = renderBrickInstruction.handleClick(mouseX, mouseY, stage);
            } else if (currentState == GameState.GAME_OVER) {
                currentState = GameOverView.handleClick(mouseX, mouseY, stage);
            } else if (currentState == GameState.CHEST_MENU) {
                currentState = renderChestMenu.handleClick(mouseX, mouseY, stage);
            }

            if (beforeState != currentState) {
                previousState = beforeState;
            }
        });

        canvas.setOnMouseDragged(event -> {
            if (currentState == GameState.SETTING) {
                double scaleX = canvas.getWidth() / Basis.SCREEN_WIDTH;
                double scaleY = canvas.getHeight() / Basis.SCREEN_HEIGHT;
                double mouseX = event.getX() / scaleX;
                double mouseY = event.getY() / scaleY;
                renderSetting.handleMouseDrag(mouseX, mouseY);
            }
        });

        // Mouse release (stop dragging)
        canvas.setOnMouseReleased(event -> {
            if (currentState == GameState.SETTING) {
                renderSetting.handleMouseRelease();
            }
        });
    }

    /**
     * Process keyboard and movement input, update paddle/ball controls and toggle states.
     * <p>
     * This reads the pressedKeys set and:
     * <ul>
     *   <li>Toggles auto-move with S</li>
     *   <li>Toggles pause with ESCAPE</li>
     *   <li>Applies player or AI movement strategy to the paddle</li>
     *   <li>Handles reset (R) and launch (SPACE)</li>
     * </ul>
     * </p>
     */
    private void processInput() {
        Paddle paddle = stage.getPaddles().get(0);
        Ball ball = stage.getBalls().get(0);

        if (pressedKeys.contains(KeyCode.S)) {
            autoMovePaddle = !autoMovePaddle;
            pressedKeys.remove(KeyCode.S);
        }

        if (pressedKeys.contains(KeyCode.ESCAPE)) {
            if (currentState == GameState.PLAYING) {
                currentState = GameState.PAUSED;
            } else if (currentState == GameState.PAUSED) {
                currentState = GameState.PLAYING;
            }
            pressedKeys.remove(KeyCode.ESCAPE);
        }

        boolean left = pressedKeys.contains(KeyCode.A);
        boolean right = pressedKeys.contains(KeyCode.D);

        if (left || right) {
            autoMovePaddle = false;
        }

        if (autoMovePaddle) {
            if (paddle.getMovementStrategy() != aiStrategy) {
                paddle.setMovementStrategy(aiStrategy);
            }

        } else {
            // Set Player strategy if it's not already set
            if (paddle.getMovementStrategy() != playerStrategy) {
                paddle.setMovementStrategy(playerStrategy);
            }

            if (left && !right) {
                paddle.moveLeft();
            } else if (right && !left) {
                paddle.moveRight();
            } else {
                paddle.setDx(0);
            }
        }

        if (pressedKeys.contains(KeyCode.R)) {
            ball.setCenter(Basis.SCREEN_WIDTH + Basis.BALL_DIAMETER / 2 + 100,
                Basis.SCREEN_HEIGHT + Basis.BALL_DIAMETER / 2 + 100);
        }

        if (pressedKeys.contains(KeyCode.SPACE)) {
            ball.launch();
        }
    }
}
