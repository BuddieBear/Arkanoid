package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.project.arkanoid.GameManager;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;

import java.util.List;

public class Ball extends MovableObject {
    private double radius;
    private double centerX;
    private double centerY;

    private double speed;
    private double angle; // in degrees

    private boolean invincible = false;
    private boolean hasLaunch = false;
    private boolean back = false;

    private final Paddle paddleMain;
    private final GameSetup stage;
    private Image ballImage = Basis.BALL_TEXTURE;

    // ===== Constructor =====
    public Ball(double centerX, double centerY, double radius, double speed, GameSetup stage) {
        super((int)(centerX - radius), (int)(centerY - radius), radius * 2, radius * 2);
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.speed = speed;
        this.stage = stage;
        this.paddleMain = stage.getPaddles().get(0);
    }

    // ===== Basic Getters / Setters =====
    public boolean getLaunchState() {
        return hasLaunch;
    }

    public void setHasLaunch(boolean hasLaunch) {
        this.hasLaunch = hasLaunch;
    }

    public double getRadius() {
        return radius;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenter(double x, double y) {
        this.centerX = x;
        this.centerY = y;
        super.setX((int)(x - radius));
        super.setY((int)(y - radius));
    }

    public void setBallImage(Image img) {
        this.ballImage = img;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // ===== Velocity Update =====
    public void updateVelocity() {
        double rad = Math.toRadians(angle);
        setDx((int)(speed * Math.cos(rad)));
        setDy((int)(speed * Math.sin(rad)));
    }

    // ===== Movement =====
    public void move() {
        if (hasLaunch) {
            String check = GameManager.getResultCollection();
            if (check.equals("Right") || check.equals("Left")) {
                AudioSet.wallBounceSound.stop();
                AudioSet.wallBounceSound.play();
                setDx(-getDx());
            } else if (check.equals("Up") || check.equals("Paddle")) {
                AudioSet.wallBounceSound.stop();
                AudioSet.wallBounceSound.play();
                setDy(-getDy());
            }
        }

        centerX += getDx();
        centerY += getDy();

        super.setX((int)(centerX - radius));
        super.setY((int)(centerY - radius));
    }

    // ===== Collision Handling =====
    public void Collision(List<? extends GameObject> others) {
        for (GameObject obj : others) {
            if (circleIntersectsRect(centerX, centerY, radius, obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight())) {
                if (obj instanceof Brick) {

                    if (!invincible) {
                        ((Brick) obj).takeHit();
                    } else {
                        ((Brick) obj).setHitPoints(0);
                    }
                    AudioSet.collisionBrickSound.play();

                    if (!invincible) {
                        bounceOff(obj);
                    }
                } else if (obj instanceof Paddle) {
                    bounceOff(obj);
                    AudioSet.collisionPaddleSound.play();
                }
            }
        }
    }

    // ===== Bounce Reflection =====
    public void bounceOff(GameObject other) {
        if (other instanceof Paddle) {
            handlePaddleCollision((Paddle) other);
            return;
        }

        double nearestX = clamp(centerX, other.getX(), other.getX() + other.getWidth());
        double nearestY = clamp(centerY, other.getY(), other.getY() + other.getHeight());

        double dx = centerX - nearestX;
        double dy = centerY - nearestY;

        // Bounce depending on which axis is stronger
        if (Math.abs(dx) > Math.abs(dy)) {
            setDx(-getDx());
        } else {
            setDy(-getDy());
        }
    }

    private void handlePaddleCollision(Paddle paddle) {
        double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;

        double hitPos = (centerX - paddleCenter) / (paddle.getWidth() / 2.0);

        if (hitPos < -1) {
            hitPos = -1;
        } else if (hitPos > 1) {
            hitPos = 1;
        }

        double maxBounce = 60.0;

        double newAngle = 270.0 + hitPos * maxBounce;

        if (newAngle < 195.0) {
            newAngle = 195.0;
        } else if (newAngle > 345.0) {
            newAngle = 345.0;
        }

        this.angle = newAngle;
        updateVelocity();
    }

    // ===== Launch =====
    public void prepareLaunch() {
        setCenter(paddleMain.getX() + paddleMain.getWidth() / 2.0, paddleMain.getY() - radius - 10);

        if (!back) {
            if (++angle >= 75) back = true;
        } else {
            if (--angle <= -75) back = false;
        }
        System.out.println("Arrow angle: " + angle);
    }

    public void launch() {
        if (hasLaunch) {
            return;
        }

        angle = 90 - angle;
        double rad = Math.toRadians(angle);
        setDx((int)(speed * Math.cos(rad)));
        setDy((int)(-speed * Math.sin(rad)));
        hasLaunch = true;
    }

    // ===== Death Check =====
    public void ifDead() {
        if (centerY > Basis.STAGE_Y + Basis.STAGE_HEIGHT && this == stage.getBalls().get(0)) {
            hasLaunch = false;
            setDx(0);
            setDy(0);
            angle = 0;
            AudioSet.lossHpSound.play();
            stage.setLives(stage.getLives() - 1);
        }
    }

    // ===== Rendering =====
    public void render(GraphicsContext gc) {
        if (!hasLaunch) {
            gc.save();
            gc.translate(centerX, centerY);
            gc.rotate(angle);
            gc.drawImage(
                    Basis.ARROW_TEXTURE,
                    -Basis.ARROW_WIDTH / 2.0,
                    -radius - Basis.ARROW_HEIGHT,
                    Basis.ARROW_WIDTH,
                    Basis.ARROW_HEIGHT
            );
            gc.restore();
        }
        gc.drawImage(ballImage, centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    public void update() {
        if (!hasLaunch) {
            prepareLaunch();
        } else {
            Collision(stage.getBricks());
            Collision(stage.getPaddles());
            move();
            ifDead();
        }
    }

    // ===== Collision Helper Functions =====
    private static boolean circleIntersectsRect(double cx, double cy, double r,
                                                double rx, double ry, double rw, double rh) {
        double closestX = clamp(cx, rx, rx + rw);
        double closestY = clamp(cy, ry, ry + rh);
        double dx = cx - closestX;
        double dy = cy - closestY;
        return dx * dx + dy * dy <= r * r;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public String checkBorderCollision() {
        // Right wall
        if (centerX + radius >= Basis.STAGE_X + Basis.STAGE_WIDTH) {
            return "Right";
        }
        // Left wall
        else if (centerX - radius <= Basis.STAGE_X) {
            return "Left";
        }
        // Top wall
        else if (centerY - radius <= Basis.STAGE_Y) {
            return "Up";
        }
        // Bottom (death zone)
        else if (centerY - radius > Basis.STAGE_Y + Basis.STAGE_HEIGHT) {
            return "Down";
        }
        return "";
    }
}