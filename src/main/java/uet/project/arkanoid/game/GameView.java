package uet.project.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.ui.gameUI;

import java.util.List;

public class GameView {
    private GameSetup stage;
    // Reference every object in the game
    public GameView(GameSetup stage) {
        this.stage = stage;
    }

    // Load new Stage
    public void updateStage(GameSetup stage) {
        this.stage = stage;
    }

    // Render objects and background in the stages
    public void onDraw(GraphicsContext gc) {
        //Background
        gameUI.render(gc, stage);

        //Run render() on every objects in game.
        for (Brick brick : stage.getBricks()) {
            brick.render(gc);
        }
        for (Ball ball : stage.getBalls()) {
            ball.render(gc);
        }
        for (Paddle paddle : stage.getPaddles()) {
            paddle.render(gc);
        }
        for (PowerUp powerUp : stage.getPowerUps()) {
            powerUp.render(gc);
        }

    }
}
