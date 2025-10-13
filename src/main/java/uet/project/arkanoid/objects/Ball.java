package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.Basis;
import uet.project.arkanoid.GameManager;

import java.util.List;

public class Ball extends MovableObject {
    private int speed = 5;
    private int angle = 0;

    private boolean hasLaunch = false;
    private boolean back = false;

    Paddle paddleMain = GameManager.getPaddle();

    public Ball(int x, int y, int width, int height, int speed, int dx, int dy) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
    }

    public boolean getLaunchState() {
        return hasLaunch;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean bounceOff(GameObject other) {
        int top_axis = getY();
        int bot_axis = getY() + getHeight();
        int left_axis = getX();
        int right_axis = getX() + getWidth();

        int other_top = other.getY();
        int other_bottom = other.getY() + other.getHeight();
        int other_left = other.getX();
        int other_right = other.getX() + other.getWidth();

        // Calculate overlaps on each side
        int overlapLeft = right_axis - other_left;
        int overlapRight = other_right - left_axis;
        int overlapTop = bot_axis - other_top;
        int overlapBottom = other_bottom - bot_axis;

        setX(getX() - getDx());
        setY(getY() - getDy());

        int minimalOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

        if (minimalOverlap == overlapLeft || minimalOverlap == overlapRight) {
            setDx(-getDx());
        }
        else if (minimalOverlap == overlapTop || minimalOverlap == overlapBottom) {
            setDy(-getDy());
        }

        //TODO: For paddle, changes the angle of the ball depending on the position it landed on the paddle.
        return true;
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
        System.out.println("Angle of the ball: " + this.angle);
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
            System.out.println("This.angle: " + this.angle);
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
        System.out.println("BALL: " + getDx() + " " + getDy());
    }
}
