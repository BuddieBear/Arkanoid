package uet.project.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.game.GameView;
import uet.project.arkanoid.game.Level;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.utils.Basis;


public class GameManager extends Application {
    // Places to render objects
    private final Group root = new Group();
    private final Canvas canvas = new Canvas(Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    AnimationTimer gameLoop;

    private static String resultCollection = "";  // TODO: Rework this into ball

    public static String getResultCollection() {
        return resultCollection;
    }

    // Game and Stage setup
    public GameState currentState = GameState.PLAYING;
    public Level currentLevel = Level.STAGE_TEST;

    GameSetup stage;

    // Renderer for each GameState
    GameView renderGame;


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

        // Game loop
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long time) { //TODO: Set up delta time

                resultCollection = checkCollisions();
                render();
                update();
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
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double scaleX = canvas.getWidth() / Basis.SCREEN_WIDTH;
        double scaleY = canvas.getHeight() / Basis.SCREEN_HEIGHT;

        gc.save();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.scale(scaleX, scaleY);

        if (currentState == GameState.PLAYING) {
            renderGame.onDraw(gc);
            System.out.println("Score: " + stage.getScore() + "| Lives: " + stage.getLives());
        }

        gc.restore();
    }

    private void handleInput(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A -> stage.getPaddles().get(0).moveLeft();
                case D -> stage.getPaddles().get(0).moveRight();
                case SPACE -> stage.getBalls().get(0).launch();
                }
            }
        );

        canvas.setOnMouseClicked(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            System.out.println("Mouse clicked at: (" + mouseX + ", " + mouseY + ")");
        });

        scene.setOnKeyReleased(event -> stage.getPaddles().get(0).setDx(0));
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

        // 33 and 25 are the padding of the paddle.
        return "";
    }

    public GameSetup getStage() {
        return stage;
    }
}
