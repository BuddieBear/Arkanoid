package uet.project.arkanoid;

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

    // --- Strategy Instances (NEW) ---
    private final MovementStrategy playerStrategy = new PlayerMovement();
    private final MovementStrategy aiStrategy = new AIMovement();

    // Game and Stage setup
    public GameState currentState = GameState.MENU;
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
     * This method is automatically called when the JavaFX application launches.
     * It sets up the {@link Scene} and {@link Canvas} for drawing,
     * and runs the {@link AnimationTimer} game loop.
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
            }
            for (Ball ball : stage.getBalls()) {
                ball.Collision(stage.getBricks());
                ball.Collision(stage.getPaddles());
                ball.update(deltaTime);
            }
            stage.addPowerUp(stage.getBricks());

            for (PowerUp powerUp : stage.getPowerUps()) {
                powerUp.update(stage.getFloatingBricks(), deltaTime);
            }

            for (FloatingText floatingText : stage.getFloatingBricks()) {
                floatingText.update(deltaTime);
            }

            for (Ammo ammo : stage.getAmmos()) {
                ammo.Collision(stage.getBricks());
                ammo.update(deltaTime);
            }

            for (Brick brick : stage.getBricks()) {
                brick.update(deltaTime);
                if (brick.isDestroy()) {
                    stage.addScore(brick.getMaxHp()*10);
                }
            }

            for (Chest chest : stage.getChests()) {
                if (chest.collision(stage.getBalls())) {
                    renderChestMenu.openChestMenu(stage);
                }
                chest.update(deltaTime);
            }

            stage.getBalls().removeIf(Ball::isMarkedForRemoval);
            stage.getPowerUps().removeIf(PowerUp::isDead);
            stage.getBricks().removeIf(Brick::isDestroy);
            stage.getAmmos().removeIf(Ammo::getIsDestroy);
            stage.getChests().removeIf(Chest::hasOpened);
            stage.getFloatingBricks().removeIf(FloatingText::isExpired);

            currentState = stage.getCurrentState();
        }
    }

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
        // --- Keyboard input tracking ---
        scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        // --- Mouse input ---
        canvas.setOnMouseClicked(event -> {
            double scaleX = canvas.getWidth() / Basis.SCREEN_WIDTH;
            double scaleY = canvas.getHeight() / Basis.SCREEN_HEIGHT;
            double mouseX = event.getX() / scaleX;
            double mouseY = event.getY() / scaleY;
            System.out.println(mouseX + " " + mouseY);

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
                } else if (level == 3){
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
            } else if (currentState == GameState.PLAYING) {
                // Add this block here
                if(stage.getScore() >= 20) {
                    stage.getAmmos().add(new Ammo(stage.getPaddles().get(0).getX()
                            + stage.getPaddles().get(0).getWidth() /2 - 10,
                            stage.getPaddles().get(0).getY(),
                            30, 30));
                    stage.setScore(stage.getScore() - 20);
                }
            } else if (currentState == GameState.SETTING) {
                if (Setting.back(mouseX, mouseY)) {
                    currentState = GameState.MENU;
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
        });
    }

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
            // Set AI strategy if it's not already set
            if (paddle.getMovementStrategy() != aiStrategy) {
                paddle.setMovementStrategy(aiStrategy);
            }
            // (We assume the aiStrategy's update method is called elsewhere)

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
            ball.setCenter(Basis.SCREEN_WIDTH + Basis.BALL_DIAMETER/2 + 1, Basis.SCREEN_HEIGHT + Basis.BALL_DIAMETER/2 + 1 );
        }

        if (pressedKeys.contains(KeyCode.SPACE)) {
            ball.launch();
        }
    }
}

