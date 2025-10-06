package uet.project.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
public class GameManager extends Application {


    public static void main(String[] args) {
        Application.launch(GameManager.class);
    }

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Hello, JavaFX!");

        // Create a layout (root node)
        StackPane root = new StackPane(label);

        // Create a scene with width=400, height=300
        Scene scene = new Scene(root, 400, 300);

        // Set up the main window (stage)
        primaryStage.setTitle("Arkanoid");
        primaryStage.setScene(scene);
        primaryStage.show(); // 👈 SHOW the window
    }

    public void update(/*KeyEvent keyEvent*/) {

    }

    public void stop() {

    }

    public void render(/*GraphicsContext gc*/) {

    }

    private void handleInput(/*KeyEvent keyEvent*/) {

    }

    /*private void checkCollisions() {
    }
    */

}
