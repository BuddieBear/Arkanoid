package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.Basis;
import uet.project.arkanoid.GameManager;

public class Ball extends MovableObject {
    private int speed = 0;
    private int angle = 0;

    private boolean hasLaunch = false;
    private boolean back = false;

    Paddle paddleMain = GameManager.getPaddle();

    public Ball(int x, int y, int width, int height, int speed, int dx, int dy) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean bounceOff(GameObject other) {
        return false;
    }

    public boolean checkCollision(GameObject other) {
        return false;
    }

    // Make the ball slide on the paddle.
    public void spawn() {
        setX(paddleMain.getX() + paddleMain.getWidth() / 2 - 25);
        if (! back) {
            if (++angle >= 75) {
                back = true;
            }
        } else {
            if (--angle <= -75) {
                back = false;
            }
        }
        System.out.println("Angle in the ball: " + this.angle);
    }
    // Set speed when launch
    public void launch () {
        angle = 90 - angle;
        double angleRadian = Math.toRadians(angle);
        setDx((int)(this.speed * Math.cos(angleRadian)));
        setDy((int)(this.speed * Math.sin(angleRadian)));
        hasLaunch = true;
    }

    public void move() {
        if (hasLaunch)
        {
            System.out.println("This.angle: " + this.angle);
            String check = GameManager.getResultCollecsion();
            if (check.equals("Right") || check.equals("Left")) {
                setDx(-getDx());
            } else if (check.equals("Up") || check.equals("Paddle")) {
                setDy(-getDy());
            }
        }
        setX(getX() + getDx());
        setY(getY() - getDy());
    }

    public void render(GraphicsContext gc) {
        if (!hasLaunch) {
            gc.save();
            // Move origin to ball center
            gc.translate(getX() + width / 2.0, getY() + height / 2.0);
            // Rotate the arrow
            gc.rotate(angle);
            // Draw the arrow above the ball, centered horizontally
            gc.drawImage(Basis.ARROW_TEXTURE, -Basis.ARROW_WIDTH / 2.0, -this.height / 2.0 - Basis.ARROW_HEIGHT, Basis.ARROW_WIDTH, Basis.ARROW_HEIGHT);
            gc.restore();
        }

        // Always draw the ball itself
        gc.drawImage(Basis.BALL_TEXTURE, getX(), getY(), width, height);
    }
    public void update() {
        if (!hasLaunch) {
            spawn();  // If "W" hasn’t been pressed yet, the ball moves along the paddle.
        } else {
            move();  // If "W" has been pressed, the ball starts bouncing.
        }
    }
}
