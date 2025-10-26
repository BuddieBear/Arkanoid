package uet.project.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.game.GameView;
import uet.project.arkanoid.game.Level;
import uet.project.arkanoid.ui.MenuView;
import uet.project.arkanoid.ui.Setting;
import uet.project.arkanoid.ui.Option;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.utils.AudioSet;
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

    private static String resultCollection = "";  // TODO: Rework this into ball

    public static String getResultCollection() {
        return resultCollection;
    }

    // Game and Stage setup
    public GameState currentState = GameState.MENU;
    public Level currentLevel = Level.STAGE_TEST;

    GameSetup stage;

    // Renderer for each GameState
    GameView renderGame;
    MenuView renderMenu;
    Setting renderSetting;
    Option renderOption;


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
        stage = new GameSetup(currentLevel); // TODO: Only update when currentState is playing

        // Set up renderers
        renderGame = new GameView(stage); // TODO: similar to stage
        renderMenu = new MenuView();
        renderSetting = new Setting();
        renderOption = new Option();

        // Game loop
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long time) { //TODO: Set up delta time
                processInput();
                resultCollection = checkCollisions();
                update();
                render();
            }
        };

        render();
        gameLoop.start(); //Run Game Loop
        primaryStage.show();
    }

    public void update() {
        if (currentState == GameState.PLAYING) {
            if (stage.gameWin() || stage.gameLose()) {
                gameLoop.stop();
                return;
            }
            // TODO: Runs update() on every GameObject.
            for (Paddle paddle : stage.getPaddles()) {
                paddle.update();
            }
            for (Ball ball : stage.getBalls()) {
                ball.update();
                ball.Collision(stage.getBricks());
                ball.Collision(stage.getPaddles());
            }
            stage.addPowerUp(stage.getBricks());

            for (PowerUp powerUp : stage.getPowerUps()) {
                powerUp.update();
            }

            for (Brick brick : stage.getBricks()) {
                brick.update();
                if (brick.isDestroy()) {
                    stage.addScore(brick.getMaxHp()*10);
                }
            }
            stage.getBricks().removeIf(Brick::isDestroy);
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
        }

        gc.restore();
    }

    private void handleInput(Scene scene) {
        // --- Keyboard input tracking ---
        scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        // --- Mouse input ---
        canvas.setOnMouseClicked(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            System.out.println(mouseX + " " + mouseY);
            if (currentState == GameState.MENU) {
                if (mouseX >= Basis.PLAY_X && mouseX <= Basis.PLAY_X + Basis.PLAY_W
                 && mouseY >= Basis.PLAY_Y && mouseY <= Basis.PLAY_Y + Basis.PLAY_H) {
                    currentState = GameState.PLAYING;
                    render();
                } else if (mouseX >= Basis.SETTING_X && mouseX <= Basis.SETTING_X + Basis.SETTING_W
                        && mouseY >= Basis.SETTING_Y && mouseY <= Basis.SETTING_Y + Basis.SETTING_H) {
                    currentState = GameState.SETTING;
                    render();
                } else if (mouseX >= Basis.OPTION_X && mouseX <= Basis.OPTION_X+ Basis.OPTION_W
                        && mouseY >= Basis.OPTION_Y && mouseY <= Basis.OPTION_Y + Basis.OPTION_H) {
                    currentState = GameState.OPTION;
                    render();
                } 
            }
        });
    }

    private void processInput() {
        Paddle paddle = stage.getPaddles().get(0);
        Ball ball = stage.getBalls().get(0);

        boolean left = pressedKeys.contains(KeyCode.A);
        boolean right = pressedKeys.contains(KeyCode.D);

        if (left && !right) {
            paddle.moveLeft();
        } else if (right && !left) {
            paddle.moveRight();
        } else {
            paddle.setDx(0); // stop smoothly
        }

        if (pressedKeys.contains(KeyCode.SPACE)) {
            ball.launch();
        }
    }

    private String checkCollisions() { //TODO: Rework this to call checkCollision of different objects (if exists)
        Ball ballMain = stage.getBalls().get(0);
        Paddle paddleMain = stage.getPaddles().get(0);
        int testX = (int) (ballMain.getX() + ballMain.getWidth() / 2);
        int testY = (int) (ballMain.getY() + ballMain.getHeight() - paddleMain.getY());

        if (ballMain.getX() + ballMain.getWidth() >= Basis.STAGE_TEST_X + Basis.STAGE_TEST_WIDTH) {
            return "Right";
        } else if (ballMain.getX() <= Basis.STAGE_TEST_X) {
            return "Left";
        } else if (ballMain.getY() <= Basis.STAGE_TEST_Y){
            return "Up";
        }

        return "";
    }

    public GameSetup getStage() {
        return stage;
    }
}
