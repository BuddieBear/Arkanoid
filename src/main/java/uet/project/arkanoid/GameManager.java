package uet.project.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.PowerUp;

import java.util.ArrayList;
import java.util.List;


public class GameManager extends Application {
    // Places to render objects
    private final Group root = new Group();
    private final Canvas canvas = new Canvas(Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private static String resultCollection = "";  // TODO: Rework this into ball

    public static String getResultCollecsion() {
        return resultCollection;
    }

    // Game and Stage setup
    public GameState currentState = GameState.GAME_TEST;
    GameSetup stage;

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
        stage = new GameSetup(currentState);

        // Set up renderers
        renderGame = new GameView(
                stage.getBricks(),
                stage.getBalls(),
                stage.getPaddles(),
                stage.getPowerUps()
        );

        // Game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long time) { //TODO: Set up delta time
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
        if (currentState == GameState.GAME_TEST) {
            // TODO: Runs update() on every GameObject.
            for (Paddle paddle : stage.paddles) {
                paddle.update();
            }
            for (Ball ball : stage.balls) {
                ball.update();
                ball.Collision(stage.bricks);
                ball.Collision(stage.paddles);
            }
            for (Brick brick : stage.bricks) {
                brick.update();
            }
            stage.bricks.removeIf(Brick::isDestroy);
        }
    }

    public void stop() {

    }

    public void render(/*GraphicsContext gc*/) {
        double scaleX = canvas.getWidth() / Basis.SCREEN_WIDTH;
        double scaleY = canvas.getHeight() / Basis.SCREEN_HEIGHT;

        gc.save(); // 🟢 Save current state
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.scale(scaleX, scaleY);

        if (currentState == GameState.GAME_TEST) {
            renderGame.onDraw(gc);
        }
        gc.restore();
    }

    private void handleInput(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A -> stage.paddles.get(0).moveLeft();
                case D -> stage.paddles.get(0).moveRight();
                case SPACE -> stage.balls.get(0).launch();
                }
            }
        );

        scene.setOnMouseClicked(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            System.out.println("Mouse clicked at: (" + mouseX + ", " + mouseY + ")");
        });

        scene.setOnKeyReleased(event -> stage.paddles.get(0).setDx(0));
    }

    private String checkCollisions() { //TODO: Rework this to call checkCollision of different objects (if exists)
        Ball ballMain = stage.balls.get(0);
        Paddle paddleMain = stage.paddles.get(0);
        int testX = ballMain.getX() + ballMain.getWidth() / 2;
        int testY = ballMain.getY() + ballMain.getHeight() - paddleMain.getY();

        if (ballMain.getX() + ballMain.getWidth() >= Basis.STAGE_TEST_X + Basis.STAGE_TEST_WIDTH) {
            return "Right";
        } else if (ballMain.getX() <= Basis.STAGE_TEST_X) {
            return "Left";
        } else if (ballMain.getY() <= Basis.STAGE_TEST_Y){
            return "Up";
        }

        // 33 and 25 are the padding of the paddle.
        return "";
    }

    public GameSetup getStage() {
        return stage;
    }
}
