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

import java.io.FileWriter;
import java.io.IOException;


public class GameManager extends Application {
    // Places to render objects
    private final Group root = new Group();
    private final Canvas canvas = new Canvas(Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private int lives = 3;
    private int score = 0;

    // Object lists
    private static final List<Ball> balls = new ArrayList<>();
    private static final List<Brick> bricks = new ArrayList<>();

    private static final List<Paddle> paddles = new ArrayList<>();
    private static final List<PowerUp> powerUps = new ArrayList<>();

    private static String resultCollection = "";  // The result of the "checkCollision" function will be stored here.

    public static Paddle getPaddle () {     // Used to pass the paddle to other classes.
        return paddles.get(0);
    }

    public static Ball getBall() {
        return balls.get(0);
    }


    public static String getResultCollecsion() {
        return resultCollection;
    }

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
     * </p>
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

        // Set up input
        handleInput(scene);

        // Set up renderers
        renderGame = new GameView(bricks, balls, paddles, powerUps);

        // Set up stage
        GameSetup stage = new GameSetup(bricks, balls, paddles, powerUps, currentState);

        // Game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long time) { //Can set up delta time
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
            for (Paddle paddle : paddles) {
                paddle.update();
            }
            for (Ball ball : balls) {
                ball.update();
            }

            // TODO: Check if ball is dead, reduce lives.
            if (balls.get(0).getY() > Basis.STAGE_TEST_Y + Basis.STAGE_TEST_HEIGHT) {
                lives--;
                if (lives <= 0) {
                    System.out.println("Game over!");
                }
            }

            for (int i = bricks.size() - 1; i >= 0; i--) {
                Brick brick = bricks.get(i);
                if (brick.getHitPoints() <= 0 && brick.getMaxHp() > 0) {
                    score += brick.getMaxHp() * 10;
                    bricks.remove(i);
                }
            }

            checkLevelComplete();
        }
    }

    public void stop() {

    }

    public void render(/*GraphicsContext gc*/) {
        if (currentState == GameState.GAME_TEST) {
            renderGame.onDraw(gc);
        }
    }

    private void handleInput(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A -> paddles.get(0).moveLeft();
                case D -> paddles.get(0).moveRight();
                case SPACE -> balls.get(0).launch();
                }
            }
        );

        scene.setOnKeyReleased(event -> paddles.get(0).setDx(0));
    }

    private String checkCollisions() {
        Ball ballMain = balls.get(0);
        Paddle paddleMain = paddles.get(0);
        int testX = ballMain.getX() + ballMain.getWidth() / 2;
        int testY = ballMain.getY() + ballMain.getHeight() - paddleMain.getY();

        if (ballMain.getX() + ballMain.getWidth() >= Basis.STAGE_TEST_X + Basis.STAGE_TEST_WIDTH) {
            return "Right";
        } else if (ballMain.getX() <= Basis.STAGE_TEST_X) {
            return "Left";
        } else if (ballMain.getY() <= Basis.STAGE_TEST_Y){
            return "Up";
        } else if (testX <= paddleMain.getX() + paddleMain.getWidth() - 33
                && testX >= paddleMain.getX() + 33
                && testY >= 25) {
            return "Paddle";
        }
        // 33 and 25 are the padding of the paddle.
        return "";
    }

    private void saveScoreForGame() {
        try (FileWriter writer = new FileWriter("score_level_test.txt", true)) {
            writer.write(score + "\n");
        } catch (IOException e) {
            System.out.println("Error saving score: " + e.getMessage());
        }
    }

    public void checkLevelComplete() {
        if (currentState == GameState.GAME_TEST) {
            boolean levelComplete = true;
            for (Brick brick : bricks) {
                if (brick.getHitPoints() > 0) {
                    levelComplete = false;
                    break;
                }
            }
            if (levelComplete) {
                saveScoreForGame();
            }
        }
    }
}
