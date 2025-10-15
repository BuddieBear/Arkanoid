package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.Basis;
import uet.project.arkanoid.GameManager;
import uet.project.arkanoid.GameSetup;

import java.util.List;

public class Ball extends MovableObject {
    private int speed = 5;
    private int angle = 0;

    private boolean hasLaunch = false;
    private boolean back = false;

    private final Paddle paddleMain;

    public Ball(int x, int y, int width, int height, int speed, int dx, int dy, Paddle paddleMain) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
        this.paddleMain = paddleMain;
    }

    public boolean getLaunchState() {
        return hasLaunch;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void bounceOff(GameObject other) {
        int ballLeft = getX();
        int ballRight = getX() + getWidth();
        int ballTop = getY();
        int ballBottom = getY() + getHeight();

        int otherLeft = other.getX();
        int otherRight = other.getX() + other.getWidth();
        int otherTop = other.getY();
        int otherBottom = other.getY() + other.getHeight();

        // Calculate overlaps
        int overlapLeft = ballRight - otherLeft;
        int overlapRight = otherRight - ballLeft;
        int overlapTop = ballBottom - otherTop;
        int overlapBottom = otherBottom - ballTop;

        // Find smallest positive overlap
        int minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

        // Now separate the ball and reflect its velocity
        if (minOverlap == overlapLeft) {          // Hit from left
            setX(getX() - overlapLeft);
            setDx(-Math.abs(getDx()));
        } else if (minOverlap == overlapRight) {  // Hit from right
            setX(getX() + overlapRight);
            setDx(Math.abs(getDx()));
        } else if (minOverlap == overlapTop) {    // Hit from top
            setY(getY() - overlapTop);
            setDy(-Math.abs(getDy()));
        } else {                                  // Hit from bottom
            setY(getY() + overlapBottom);
            setDy(Math.abs(getDy()));
        }
        //TODO: Paddle Ball Angle changes
        return;
    }

    public boolean checkCollision(GameObject other) {
        return this.getX() < other.getX() + other.getWidth()
                && this.getX() + this.getWidth() > other.getX()
                && this.getY() < other.getY() + other.getHeight()
                && this.getY() + this.getHeight() > other.getY();
    }

    public void Collision(List<? extends GameObject> others) {
        for (GameObject Obj : others) {
            if (this.checkCollision(Obj)) {
                if (Obj instanceof Brick && !((Brick) Obj).getProtection()) { // Check for Bricks
                    ((Brick) Obj).takeHit();
                    ((Brick) Obj).setProtection(true);
                    bounceOff(Obj);
                }
                else if (Obj instanceof Paddle) {
                    bounceOff(Obj);
                }
            }
        }
    }

    // Set the ball on the paddle waiting to be launched
    public void prepareLaunch() {
        setX(paddleMain.getX() + paddleMain.getWidth() / 2 -25);
        setY(paddleMain.getY() - this.height);
        if (! back) {
            if (++angle >= 75) {
                back = true;
            }
        } else {
            if (--angle <= -75) {
                back = false;
            }
        }
    }
    // Set speed when launch
    public void launch () {
        if (hasLaunch) {
            return;
        }
        angle = 90 - angle;
        double angleRadian = Math.toRadians(angle);
        setDx((int)(this.speed * Math.cos(angleRadian)));
        setDy((int)(-this.speed * Math.sin(angleRadian)));
        hasLaunch = true;
    }

    public void isDead() {
        if (this.getY() > Basis.STAGE_TEST_Y + Basis.STAGE_TEST_HEIGHT) {
            hasLaunch = false;
            setDx(0);
            setDy(0);
        }
    }

    public void move() {
        if (hasLaunch)
        {
            String check = GameManager.getResultCollecsion();
            if (check.equals("Right") || check.equals("Left")) {
                setDx(-getDx());
            } else if (check.equals("Up") || check.equals("Paddle")) {
                setDy(-getDy());
            }
        }
        setX(getX() + getDx());
        setY(getY() + getDy());
    }

    public void render(GraphicsContext gc) {
        // Draw Arrows if not launched
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
            prepareLaunch();  // Spawn until launched
        } else {
            move();  // After launched
            isDead();
        }
    }
}
