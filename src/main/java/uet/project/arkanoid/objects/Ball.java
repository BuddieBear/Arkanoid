package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.GameManager;

import java.util.List;

public class Ball extends MovableObject {
    private int speed = 5;
    private int angle = 0;
    private boolean invincible = false;
    private boolean hasLaunch = false;
    private boolean back = false;

    private final Paddle paddleMain;
    private final GameSetup stage;
    private Image ballImage = Basis.BALL_TEXTURE;

    public Ball(int x, int y, double width, double height, int speed, GameSetup stage) {
        super(x, y, width, height);
        this.speed = speed;
        this.stage = stage;
        this.paddleMain = stage.getPaddles().get(0);
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public boolean getLaunchState() {
        return hasLaunch;
    }

    public void setBallImage(Image ballImage) {
        this.ballImage = ballImage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setHasLaunch(boolean hasLaunch) {
        this.hasLaunch = hasLaunch;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void updateVelocity() {
        double angleRad = Math.toRadians(angle);
        setDx((int) (speed * Math.cos(angleRad)));
        setDy((int) (speed * Math.sin(angleRad)));
    }
    public void bounceOff(GameObject other) {
        // Paddle collision: custom bounce
        if (other instanceof Paddle ) {
            handlePaddleCollision((Paddle) other);
            return;
        }

        int ballLeft = getX();
        int ballRight = getX() + (int)getWidth();
        int ballTop = getY();
        int ballBottom = getY() + (int)getHeight();

        int otherLeft = other.getX();
        int otherRight = other.getX() + (int)other.getWidth();
        int otherTop = other.getY();
        int otherBottom = other.getY() + (int)other.getHeight();

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

    private void handlePaddleCollision(Paddle paddle) {
        // Compute centers
        double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;
        double ballCenter = getX() + getWidth() / 2.0;

        // Relative hit position (-1 → left edge, +1 → right edge)
        double hitPos = (ballCenter - paddleCenter) / (paddle.getWidth() / 2.0);
        hitPos = Math.max(-1.0, Math.min(1.0, hitPos));

        // Define max deviation from vertical (e.g. ±60°)
        double maxBounce = 60.0;

        // Base upward = 270°, adjust left/right
        double newAngle = 270.0 + hitPos * maxBounce;

        // Clamp — never let the ball go too horizontal
        if (newAngle > 345.0) newAngle = 345.0; // up-right limit
        if (newAngle < 195.0) newAngle = 195.0; // up-left limit

        this.angle = (int) newAngle;
        updateVelocity();
    }

    public void Collision(List<? extends GameObject> others) {
        for (GameObject Obj : others) {
            if (this.checkCollision(Obj)) {
                if (Obj instanceof Brick && !((Brick) Obj).getProtection()) { // Check for Bricks
                    if(!invincible) {
                        ((Brick) Obj).takeHit();
                    } else {
                        ((Brick) Obj).setHitPoints(0);
                    }
                    AudioSet.collisionBrickSound.play();
                    ((Brick) Obj).setProtection(true);
                    if(!invincible) {
                        bounceOff(Obj);
                    }
                }
                else if (Obj instanceof Paddle) {
                    bounceOff(Obj);
                    AudioSet.collisionPaddleSound.play();
                }
            }
        }
    }

    // Set the ball on the paddle waiting to be launched
    public void prepareLaunch() {
        setX((int) (paddleMain.getX() + paddleMain.getWidth() / 2 -25));
        setY((int) (paddleMain.getY() - this.height));
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

    public void ifDead() {
        if (this.getY() > Basis.STAGE_TEST_Y + Basis.STAGE_TEST_HEIGHT &&
                this == stage.getBalls().get(0)) {
            hasLaunch = false;
            setDx(0);
            setDy(0);
            angle = 0;
            AudioSet.lossHpSound.play();
            stage.setLives(stage.getLives() - 1);
        }
    }

    public void move() {
        if (hasLaunch)
        {
            String check = GameManager.getResultCollection();
            if (check.equals("Right") || check.equals("Left")) {
                long start = System.nanoTime();
                AudioSet.wallBounceSound.stop();
                AudioSet.wallBounceSound.play();
                System.out.println("WALL BOUNCED at " + (System.nanoTime() - start)/1_000_000.0 + " ms");
                setDx(-getDx());
            } else if (check.equals("Up") || check.equals("Paddle")) {
                AudioSet.wallBounceSound.stop();
                System.out.println("WALL BOUNCED !");
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
        gc.drawImage(ballImage, getX(), getY(), width, height);
    }

    public void update() {
        if (!hasLaunch) {
            prepareLaunch();  // Spawn until launched
        } else {
            this.Collision(stage.getBricks());
            this.Collision(stage.getPaddles());
            move();
            ifDead();
        }
    }
}
