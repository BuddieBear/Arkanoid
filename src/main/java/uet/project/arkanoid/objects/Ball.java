package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uet.project.arkanoid.objects.FloatingText;
import uet.project.arkanoid.base.Circle;
import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.base.Vector2D;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.utils.HelperFunction;

import javax.naming.ldap.LdapName;
import java.util.List;

public class Ball extends MovableObject {
    private double radius;
    private double centerX;
    private double centerY;

    private double speed;

    private double angle;

    // NEW: A separate variable just for the visual arrow
    private double visualAngle = 0;

    private boolean markedForRemoval = false;
    private boolean mainBall = true;
    private boolean invincible = false;

    private boolean hasLaunch = false;
    private boolean back = false;

    private final Paddle paddleMain;
    private final GameSetup stage;
    private Image ballImage = Basis.BALL_TEXTURE;

    private Circle hitbox;



    public Ball(double centerX, double centerY, double radius, double speed, GameSetup stage) {
        super((centerX - radius), (centerY - radius), radius * 2, radius * 2);
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.speed = speed;
        this.stage = stage;
        this.paddleMain = stage.getPaddles().get(0);
        this.hitbox = new Circle(new Point(centerX, centerY), radius);
        this.angle = 270; // Default movement angle (up)
        this.visualAngle = 0; // Default visual angle (straight)
    }

    public void updateVelocity() {
        double rad = Math.toRadians(angle);
        setDx(speed * Math.cos(rad));
        setDy(speed * Math.sin(rad));
    }


    public void move() {
        if (hasLaunch) {
            String check = checkBorderCollision();
            if (check.equals("Right") || check.equals("Left")) {
                AudioSet.wallBounceSound.stop();
                AudioSet.wallBounceSound.play();
                // Reflect horizontally
                this.angle = 180 - this.angle;
                updateVelocity();
            } else if (check.equals("Up")) { // CHANGED: Don't bounce on "Paddle" string
                AudioSet.wallBounceSound.stop();
                AudioSet.wallBounceSound.play();
                // Reflect vertically
                this.angle = 360 - this.angle;
                updateVelocity();
            }
        }

        this.Collision(stage.getBricks());
        this.Collision(stage.getPaddles());

        setCenter(centerX + getDx(), centerY + getDy());
    }

    public boolean Collision(List<? extends GameObject> others) {
        boolean hit = false;
        for (GameObject obj : others) {
            if (obj.getHitbox() == null) continue; // Safety check

            if (this.hitbox.intersect(obj.getHitbox())) {
                if (obj instanceof Brick) {
                    if (!invincible) {
                        AudioSet.collisionBrickSound.play();
                        ((Brick) obj).takeHit();
                        bounceOff(obj);
                    } else {
                        ((Brick) obj).setHitPoints(0);
                    }

                } else if (obj instanceof Paddle) {
                    bounceOff(obj);
                    AudioSet.collisionPaddleSound.play();
                }
                hit = true;
            }
        }
        return hit;
    }

    public void bounceOff(GameObject other) {
        if (other instanceof Paddle) {
            handlePaddleCollision((Paddle) other);
            return;
        }

        if (other instanceof Brick) {
            Rectangle rect = (Rectangle) other.getHitbox();
            Circle circle = this.hitbox;

            // 1 to 5: same as Circle vs Rectangle
            // 1. Transform Circle Center to Rect's Local Space
            Point rectCenter = rect.getCenter();
            Vector2D rectSize = rect.getSize();

            double rectRotation = rect.getRotation();

            double dx_c = circle.getCenter().getX() - rectCenter.getX();
            double dy_c = circle.getCenter().getY() - rectCenter.getY();

            double cos_local = Math.cos(-rectRotation);
            double sin_local = Math.sin(-rectRotation);

            double localCircleX = dx_c * cos_local - dy_c * sin_local;
            double localCircleY = dx_c * sin_local + dy_c * cos_local;

            double closestX = HelperFunction.clamp(localCircleX, -rectSize.getX() / 2.0, rectSize.getX() / 2.0);
            double closestY = HelperFunction.clamp(localCircleY, - rectSize.getY() / 2.0, rectSize.getY() / 2.0);

            // 3. Get Collision Normal (Local Space)
            double localNormalX = localCircleX - closestX;
            double localNormalY = localCircleY - closestY;

            // 4. Rotate Normal back to World Space
            double cos_pos = Math.cos(rectRotation);
            double sin_pos = Math.sin(rectRotation);
            double worldNormalX = localNormalX * cos_pos - localNormalY * sin_pos;
            double worldNormalY = localNormalX * sin_pos + localNormalY * cos_pos;

            // 5. Reflect Velocity Vector
            Vector2D normal = new Vector2D(worldNormalX, worldNormalY).normalize();
            Vector2D v_in = new Vector2D(getDx(), getDy());
            double dotProduct = v_in.dot(normal);
            Vector2D v_out = v_in.subtract(normal.multiply(2 * dotProduct));


            this.angle = Math.toDegrees(Math.atan2(v_out.getY(), v_out.getX()));
            updateVelocity();

            // 6. FIX FOR STICKY COLLISIONS
            double distDx = localCircleX - closestX;
            double distDy = localCircleY - closestY;
            double distance = Math.sqrt((distDx * distDx) + (distDy * distDy));
            double penetration = radius - distance;

            if (penetration > 0) {
                // Push the ball out along the collision normal
                double pushX = normal.getX() * (penetration + 0.1);
                double pushY = normal.getY() * (penetration + 0.1);
                setCenter(getCenterX() + pushX, getCenterY() + pushY);
            }
        }
    }

    private void handlePaddleCollision(Paddle paddle) {
        double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;
        double hitPos = (centerX - paddleCenter) / (paddle.getWidth() / 2.0);
        hitPos = HelperFunction.clamp(hitPos, -1, 1); // Clamp hit position

        double maxBounce = 60.0;

        // Use 270 degrees as "up"
        double newAngle = 270.0 + hitPos * maxBounce;

        // Clamp angles to be between 195 (up-left) and 345 (up-right)
        newAngle = HelperFunction.clamp(newAngle, 195, 345);

        // CHANGED: Set the *movement* angle
        this.angle = newAngle;
        updateVelocity();
    }

    public void prepareLaunch() {
        setCenter(paddleMain.getX() + paddleMain.getWidth() / 2.0, paddleMain.getY() - radius - 10);

        // CHANGED: This now updates the visualAngle
        if (!back) {
            if (++visualAngle >= 75) back = true;
        } else {
            if (--visualAngle <= -75) back = false;
        }
    }

    public void launch() {
        if (hasLaunch) {
            return;
        }
        this.angle = 270 + this.visualAngle;
        updateVelocity();
        hasLaunch = true;
        System.out.println("Launched: " + this.angle + "/ " + this.getDx() + "/ " + this.getDy());

    }


    public void ifDead() {
        if (getY() > Basis.STAGE_Y + Basis.STAGE_HEIGHT) {
            if (mainBall) {
                hasLaunch = false;
                setDx(0);
                setDy(0);
                visualAngle = 0;
                angle = 270;
                AudioSet.lossHpSound.play();
                stage.setLives(stage.getLives() - 1);
            } else {
                markedForRemoval = true;
            }
        }
    }


    public void render(GraphicsContext gc) {
        if (!hasLaunch) {
            gc.save();
            gc.translate(centerX, centerY);
            // CHANGED: Rotate by the visualAngle
            gc.rotate(visualAngle);
            gc.drawImage(
                    Basis.ARROW_TEXTURE,
                    -Basis.ARROW_WIDTH / 2.0,
                    -radius - Basis.ARROW_HEIGHT,
                    Basis.ARROW_WIDTH,
                    Basis.ARROW_HEIGHT
            );
            gc.restore();
            gc.drawImage(ballImage, centerX-radius, centerY-radius, radius * 2, radius * 2);
        } else {
            gc.save();
            long now = System.currentTimeMillis();
            double rotation = ((now / 1000.0) * speed * 300) % 360;

            // spin around its center
            gc.translate(centerX, centerY);
            gc.rotate(rotation);
            gc.drawImage(ballImage, -radius, -radius, radius * 2, radius * 2);
            gc.restore();
        }
    }

    public void update() {
        if (!hasLaunch) {
            prepareLaunch();
        } else {
            move();
            ifDead();
        }
    }

    public String checkBorderCollision() {
        // Right wall
        if (centerX + radius >= Basis.STAGE_X + Basis.STAGE_WIDTH) {
            setCenter(Basis.STAGE_X + Basis.STAGE_WIDTH - radius, centerY); // Prevent sticking
            return "Right";
        }
        // Left wall
        else if (centerX - radius <= Basis.STAGE_X) {
            setCenter(Basis.STAGE_X + radius, centerY); // Prevent sticking
            return "Left";
        }
        // Top wall
        else if (centerY - radius <= Basis.STAGE_Y) {
            setCenter(centerX, Basis.STAGE_Y + radius); // Prevent sticking
            return "Up";
        }
        // Bottom (death zone)
        else if (centerY - radius > Basis.STAGE_Y + Basis.STAGE_HEIGHT) {
            return "Down";
        }
        return "";
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

    public void setMarkedForRemoval(boolean markedForRemoval) {
        this.markedForRemoval = markedForRemoval;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Paddle getPaddleMain() {
        return paddleMain;
    }

    public Image getBallImage() {
        return ballImage;
    }

    public void setBallImage(Image ballImage) {
        this.ballImage = ballImage;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public boolean isMainBall() {
        return mainBall;
    }

    public void setMainBall(boolean mainBall) {
        this.mainBall = mainBall;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    @Override
    public Shape getHitbox() {
        return this.hitbox;
    }

    public boolean getLaunchState() {
        return hasLaunch;
    }

    public void setHasLaunch(boolean hasLaunch) {
        this.hasLaunch = hasLaunch;
    }


    public void setCenter(double x, double y) {
        this.centerX = x;
        this.centerY = y;
        super.setX((int)(x - radius));
        super.setY((int)(y - radius));
        this.hitbox.setCenter(new Point(x, y));
    }

}