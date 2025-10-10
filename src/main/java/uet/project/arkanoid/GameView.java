package uet.project.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.project.arkanoid.objects.Arrow;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.PowerUp;

import java.util.List;

public class GameView {
    private List<Brick> bricks;
    private List<Ball> balls;
    private List<Paddle> paddles;
    private List<PowerUp> powerUps;
    private Arrow arrow;

    // Reference every object in the game
    public GameView(List<Brick> bricks,
                    List<Ball> balls,
                    List<Paddle> paddles,
                    List<PowerUp> powerUps,
                    Arrow arrow) {
        this.bricks = bricks;
        this.balls = balls;
        this.paddles = paddles;
        this.powerUps = powerUps;
        this.arrow = arrow;
    }

    // Render objects and background in the stages
    public void onDraw(GraphicsContext gc, boolean gun) {
        //Background
        gc.setFill(Color.BLACK);
        gc.fillRect(Basis.STAGE_TEST_X, Basis.STAGE_TEST_Y, Basis.STAGE_TEST_WIDTH, Basis.STAGE_TEST_HEIGHT);

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
        if (gun) {
            return;
        }
        arrow.render(gc);
    }
}
