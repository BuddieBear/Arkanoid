package uet.project.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.project.arkanoid.Objects.Ball;
import uet.project.arkanoid.Objects.Brick;
import uet.project.arkanoid.Objects.Paddle;
import uet.project.arkanoid.Objects.PowerUp;

import java.util.ArrayList;
import java.util.List;


public class GameManager extends Application {
    // Places to render objects
    private final Group root = new Group();
    private final Canvas canvas = new Canvas(Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    // Object lists
    private final List<Ball> balls = new ArrayList<>();
    private final List<Brick> bricks = new ArrayList<>();
    private final List<Paddle> paddles = new ArrayList<>();
    private final List<PowerUp> powerUps = new ArrayList<>();

    // GameState
    public GameState currentState = GameState.GAME_TEST;

    // Renderer for each GameState
    GameView renderGame; // For stages

    public static void main(String[] args) {
        Application.launch(GameManager.class);
    }

    /**
     * Initializes and starts the main game window.
     * <p>
     * This method is automatically called when the JavaFX application launches.
     * It sets up the {@link Scene} and {@link Canvas} for drawing,
     * and runs the {@link AnimationTimer} game loop.
     *</p>
     *
     * @param primaryStage the primary window (stage) for the application
     */
    @Override
    public void start(Stage primaryStage) {
        // Create a scene
        Scene scene = new Scene(root, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

        // Set up the main window (stage)
        primaryStage.setTitle("Arkanoid");
        primaryStage.setScene(scene);

        // Set up canvas for drawing
        root.getChildren().add(canvas);

        // Set up renderers
        renderGame = new GameView(bricks, balls, paddles, powerUps);

        // Game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long time) { //Can set up delta time
                update();
                render();
            }
        };

        render();
        gameLoop.start(); //Run Game Loop
        primaryStage.show();
    }

    public void update() {
        if(currentState == GameState.GAME_TEST) {
            // TODO: Runs update() on every GameObject.
        }
    }

    public void stop() {

    }

    public void render(/*GraphicsContext gc*/) {
        if(currentState == GameState.GAME_TEST) {
            renderGame.onDraw(gc);
        }
    }

    private void handleInput() {

    }

    /*private void checkCollisions() {
    }
    */

}
