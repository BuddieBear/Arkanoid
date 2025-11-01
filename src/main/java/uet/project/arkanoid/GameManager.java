package uet.project.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import uet.project.arkanoid.game.*;
import uet.project.arkanoid.objects.*;
import uet.project.arkanoid.ui.MenuView;
import uet.project.arkanoid.ui.Setting;
import uet.project.arkanoid.ui.PausedMenuView;
import uet.project.arkanoid.ui.LoadScreenView;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.game.GameView;
import uet.project.arkanoid.game.Level;
import uet.project.arkanoid.ui.*;
import uet.project.arkanoid.utils.Basis;

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


    // Game and Stage setup
    public GameState currentState = GameState.MENU;
    public Level currentLevel = Level.STAGE_1;

    GameSetup stage;

    // Renderer for each GameState
    GameView renderGame;
    MenuView renderMenu;
    Setting renderSetting;
    Instruction renderOption;
    LevelPlay renderLevel;
    PausedMenuView renderPausedMenu;
    LoadScreenView renderLoadScreen;

    protected double lastTime = System.nanoTime() / 1_000_000_000.0;

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

        // Set up input
        handleInput(scene);

        // Set up stage
        stage = new GameSetup(currentLevel);

        // Set up renderer for Game
        renderGame = new GameView(stage);
        renderMenu = new MenuView();
        renderSetting = new Setting();
        renderOption = new Instruction();
        renderLevel = new LevelPlay();
        renderPausedMenu = new PausedMenuView();
        renderLoadScreen = new LoadScreenView();

        // Game loop
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long time) { //TODO: Set up delta time
                processInput();
                update();
                render();
            }
        };

        render();
        gameLoop.start(); //Run Game Loop
        primaryStage.show();
    }

    public void update() {
        stage.updateDeltaTime();

        if (currentState == GameState.PLAYING) {
            if (stage.gameWin() || stage.gameLose()) {
                currentState = GameState.GAME_OVER;
                return;
            }

            // TODO: Runs update() on every GameObject.
            for (Paddle paddle : stage.getPaddles()) {
                paddle.update();
            }
            for (Ball ball : stage.getBalls()) {
                ball.Collision(stage.getBricks());
                ball.Collision(stage.getPaddles());
                ball.update();
            }
            stage.addPowerUp(stage.getBricks());

            for (PowerUp powerUp : stage.getPowerUps()) {
                powerUp.update(stage.getFloatingBricks());
            }
            for (FloatingText floatingText : stage.getFloatingBricks()) {
                floatingText.update(stage.getDeltaTime());
            }

            for (Brick brick : stage.getBricks()) {
                brick.update();
                if (brick.isDestroy()) {
                    stage.addScore(brick.getMaxHp()*10);
                }
            }
            stage.getPowerUps().removeIf(PowerUp::isDead);
            stage.getBricks().removeIf(Brick::isDestroy);
            stage.getFloatingBricks().removeIf(FloatingText::isExpired);
        }
    }

    public void stop() {

    }

    public void render() {
        // gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double scaleX = canvas.getWidth() / Basis.SCREEN_WIDTH;
        double scaleY = canvas.getHeight() / Basis.SCREEN_HEIGHT;

        gc.save();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.scale(scaleX, scaleY);

        if (currentState == GameState.MENU) {
            renderMenu.onDraw(gc);
        } else if (currentState == GameState.SETTING){
            renderSetting.onDraw(gc);
        } else if (currentState == GameState.OPTION) {
            renderOption.onDraw(gc);
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
        } else if(currentState == GameState.LOAD_GAME) {
            renderMenu.onDraw(gc);
            renderLoadScreen.onDraw(gc);
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

                stage = new GameSetup(currentLevel);
                renderGame = new GameView(stage);
                currentState = GameState.PLAYING;
            } else if (currentState == GameState.SETTING) {
                if (Setting.back(mouseX, mouseY)) {
                    currentState = GameState.MENU;
                }
            } else if (currentState == GameState.OPTION) {
                if (Instruction.back(mouseX, mouseY)) {
                    currentState = GameState.MENU;
                }
            } else if (currentState == GameState.GAME_OVER) {
                currentState = GameOverView.handleClick(mouseX, mouseY, stage);
            }
        });
    }

    private void processInput() {
        Paddle paddle = stage.getPaddles().get(0);
        Ball ball = stage.getBalls().get(0);

        boolean left = pressedKeys.contains(KeyCode.A);
        boolean right = pressedKeys.contains(KeyCode.D);
        if (pressedKeys.contains(KeyCode.S)) {
            autoMovePaddle = true;
        }

        if (left && !right) {
            paddle.moveLeft();
            autoMovePaddle = false;
        } else if (right && !left) {
            paddle.moveRight();
            autoMovePaddle = false;
        } else {
            paddle.setDx(0); // stop smoothly
        }

        if (autoMovePaddle) {
            stage.getPaddle().autoMovePaddle(stage);
        }

        if (pressedKeys.contains(KeyCode.R)) {
            ball.setCenter(Basis.SCREEN_WIDTH + Basis.BALL_DIAMETER/2 + 1, Basis.SCREEN_HEIGHT + Basis.BALL_DIAMETER/2 + 1 );
        }

        if (pressedKeys.contains(KeyCode.SPACE)) {
            ball.launch();
        }

        if (pressedKeys.contains(KeyCode.ESCAPE)) {
            if (currentState == GameState.PLAYING) {
                currentState = GameState.PAUSED;
                pressedKeys.remove(KeyCode.ESCAPE);
            } else if (currentState == GameState.PAUSED) {
                currentState = GameState.PLAYING;
                pressedKeys.remove(KeyCode.ESCAPE);
            }
        }
    }
}

