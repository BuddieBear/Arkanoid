        package uet.project.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.ui.gameUI;

import java.util.List;

public class GameView {
    private List<Brick> bricks;
    private List<Ball> balls;
    private List<Paddle> paddles;
    private List<PowerUp> powerUps;

    // Reference every object in the game
    public GameView(List<Brick> bricks,
                    List<Ball> balls,
                    List<Paddle> paddles,
                    List<PowerUp> powerUps) {
        this.bricks = bricks;
        this.balls = balls;
        this.paddles = paddles;
        this.powerUps = powerUps;
    }

    // Render objects and background in the stages
    public void onDraw(GraphicsContext gc) {
        //Background
        gameUI.render(gc);

        //Run render() on every objects in game.
        for (Brick brick : bricks) {
            brick.render(gc);
        }
        for (Ball ball : balls) {
            ball.render(gc);
        }
        for (Paddle paddle : paddles) {
            paddle.render(gc);
        }
        for (PowerUp powerUp : powerUps) {
            powerUp.render(gc);
        }
    }
}
