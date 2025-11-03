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
import uet.project.arkanoid.objects.brickVariants.IndestructibleBrick;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.utils.HelperFunction;

import javax.naming.ldap.LdapName;
import java.util.List;

/**
 * Represents a ball in the Arkanoid game. The ball can move, collide with objects, and be rendered
 * on screen.
 *
 * <p>This class handles ball physics, collision detection, and visual representation.
 */
public class Ball extends MovableObject {

  private double radius;
  private double centerX;
  private double centerY;

  private double speed;

  private double angle;
  private final double defaultRadius;
  private final double defaultSpeed;
  // For Rendering only
  private double visualAngle = 0;
  private double rotationAngle = 0;

  private boolean markedForRemoval = false;
  private boolean mainBall = true;
  private boolean invincible = false;

  private boolean hasLaunch = false;
  private boolean back = false;

  private final Paddle paddleMain;
  private final GameSetup stage;
  private Image ballImage = Basis.BALL_TEXTURE;

  private Circle hitbox;

  /**
   * Constructs a new Ball with specified parameters.
   *
   * @param centerX the initial x-coordinate of the ball's center
   * @param centerY the initial y-coordinate of the ball's center
   * @param radius  the radius of the ball
   * @param speed   the movement speed of the ball
   * @param stage   the game stage this ball belongs to
   */
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
    this.defaultRadius = radius;
    this.defaultSpeed = speed;
  }

  /**
   * Updates the ball's velocity based on its current angle and speed.
   */
  public void updateVelocity() {
    double rad = Math.toRadians(angle);
    setDx(speed * Math.cos(rad));
    setDy(speed * Math.sin(rad));
  }

  /**
   * Restores the ball's radius and speed to their default values.
   */
  public void restoreDefaultStats() {
    this.radius = defaultRadius;
    this.speed = defaultSpeed;
    updateVelocity();
  }

  /**
   * Moves the ball based on its velocity and handles border collisions.
   *
   * @param deltaTime the time elapsed since the last update
   */
  public void move(double deltaTime) {
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
    } else {
      updateVelocity();
    }

    this.Collision(stage.getBricks());
    this.Collision(stage.getPaddles());

    setCenter(centerX + getDx() * deltaTime, centerY + getDy() * deltaTime);
  }

  /**
   * Checks and handles collisions with other game objects.
   *
   * @param others the list of game objects to check collisions with
   * @return true if a collision occurred, false otherwise
   */
  public boolean Collision(List<? extends GameObject> others) {
    boolean hit = false;
    for (GameObject obj : others) {
      if (obj.getHitbox() == null) {
        continue; // Safety check
      }

      if (this.hitbox.intersect(obj.getHitbox())) {
        if (obj instanceof Brick) {
          Brick brick = (Brick) obj;

          if (!invincible) {
            brick.takeHit();
            bounceOff(obj);
          } else if (!(brick instanceof IndestructibleBrick)) {
            brick.setHitPoints(0);
          }

          AudioSet.collisionBrickSound.play();
        } else if (obj instanceof Paddle) {
          bounceOff(obj);
          AudioSet.collisionPaddleSound.play();
        }
        hit = true;
      }
    }
    return hit;
  }

  /**
   * Handles bouncing off other game objects.
   *
   * @param other the game object to bounce off from
   */
  public void bounceOff(GameObject other) {
    if (other instanceof Paddle) {
      handlePaddleCollision((Paddle) other);
      return;
    }

    if (other instanceof Brick) {
      Rectangle rect = (Rectangle) other.getHitbox();
      Circle circle = this.hitbox;

      Point rectCenter = rect.getCenter();
      Vector2D rectSize = rect.getSize();

      double rectRotation = rect.getRotation();

      double dx_c = circle.getCenter().getX() - rectCenter.getX();
      double dy_c = circle.getCenter().getY() - rectCenter.getY();

      double cos_local = Math.cos(-rectRotation);
      double sin_local = Math.sin(-rectRotation);

      double localCircleX = dx_c * cos_local - dy_c * sin_local;
      double localCircleY = dx_c * sin_local + dy_c * cos_local;

      double closestX = HelperFunction.clamp(localCircleX, -rectSize.getX() / 2.0,
          rectSize.getX() / 2.0);
      double closestY = HelperFunction.clamp(localCircleY, -rectSize.getY() / 2.0,
          rectSize.getY() / 2.0);

      double localNormalX = localCircleX - closestX;
      double localNormalY = localCircleY - closestY;

      double cos_pos = Math.cos(rectRotation);
      double sin_pos = Math.sin(rectRotation);
      double worldNormalX = localNormalX * cos_pos - localNormalY * sin_pos;
      double worldNormalY = localNormalX * sin_pos + localNormalY * cos_pos;

      Vector2D normal = new Vector2D(worldNormalX, worldNormalY).normalize();
      Vector2D v_in = new Vector2D(getDx(), getDy());
      double dotProduct = v_in.dot(normal);
      Vector2D v_out = v_in.subtract(normal.multiply(2 * dotProduct));

      this.angle = Math.toDegrees(Math.atan2(v_out.getY(), v_out.getX()));
      updateVelocity();

      double distDx = localCircleX - closestX;
      double distDy = localCircleY - closestY;
      double distance = Math.sqrt((distDx * distDx) + (distDy * distDy));
      double penetration = radius - distance;

      if (penetration > 0) {
        double pushX = normal.getX() * (penetration + 0.1);
        double pushY = normal.getY() * (penetration + 0.1);
        setCenter(getCenterX() + pushX, getCenterY() + pushY);
      }
    }
  }

  /**
   * Handles collision with a paddle.
   *
   * @param paddle the paddle to handle collision with
   */
  private void handlePaddleCollision(Paddle paddle) {
    double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;
    double hitPos = (centerX - paddleCenter) / (paddle.getWidth() / 2.0);
    hitPos = HelperFunction.clamp(hitPos, -1, 1); // Clamp hit position

    double maxBounce = 60.0;

    double newAngle = 270.0 + hitPos * maxBounce;

    newAngle = HelperFunction.clamp(newAngle, 195, 345);

    this.angle = newAngle;
    updateVelocity();
  }

  /**
   * Prepares the ball for launch by positioning it above the paddle and animating the arrow.
   */
  public void prepareLaunch() {
    setCenter(paddleMain.getX() + paddleMain.getWidth() / 2.0, paddleMain.getY() - radius - 10);

    if (!back) {
      if (++visualAngle >= 75) {
        back = true;
      }
    } else {
      if (--visualAngle <= -75) {
        back = false;
      }
    }
  }

  /**
   * Launches the ball with the current visual angle.
   */
  public void launch() {
    if (hasLaunch) {
      return;
    }
    this.angle = 270 + this.visualAngle;
    updateVelocity();
    hasLaunch = true;
    System.out.println("Launched: " + this.angle + "/ " + this.getDx() + "/ " + this.getDy());

  }

  /**
   * Checks if the ball is dead (fell below the stage) and handles consequences.
   */
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

  /**
   * Renders the ball and its aiming arrow (if not launched) on the graphics context.
   *
   * @param gc the graphics context to render on
   */
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
      gc.drawImage(ballImage, centerX - radius, centerY - radius, radius * 2, radius * 2);
    } else {
      gc.save();

      // spin around its center
      gc.translate(centerX, centerY);
      gc.rotate(rotationAngle);
      gc.drawImage(ballImage, -radius, -radius, radius * 2, radius * 2);
      gc.restore();
    }
  }

  /**
   * Updates the ball's state.
   *
   * @param deltaTime the time elapsed since the last update
   */
  public void update(double deltaTime) {
    if (!hasLaunch) {
      prepareLaunch();
    } else {
      rotationAngle = (rotationAngle + speed - 200) % 360;
      move(deltaTime);
      ifDead();
    }
  }

  /**
   * Checks for collisions with stage borders.
   *
   * @return a string indicating which border was hit ("Right", "Left", "Up", "Down"), or empty
   * string if no collision
   */
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

  /**
   * Checks if the ball is marked for removal.
   *
   * @return true if marked for removal, false otherwise
   */
  public boolean isMarkedForRemoval() {
    return markedForRemoval;
  }

  /**
   * Sets whether the ball is marked for removal.
   *
   * @param markedForRemoval true to mark for removal, false otherwise
   */
  public void setMarkedForRemoval(boolean markedForRemoval) {
    this.markedForRemoval = markedForRemoval;
  }

  /**
   * Gets the x-coordinate of the ball's center.
   *
   * @return the x-coordinate of the center
   */
  public double getCenterX() {
    return centerX;
  }

  /**
   * Sets the x-coordinate of the ball's center.
   *
   * @param centerX the new x-coordinate of the center
   */
  public void setCenterX(double centerX) {
    this.centerX = centerX;
  }

  /**
   * Gets the y-coordinate of the ball's center.
   *
   * @return the y-coordinate of the center
   */
  public double getCenterY() {
    return centerY;
  }

  /**
   * Sets the y-coordinate of the ball's center.
   *
   * @param centerY the new y-coordinate of the center
   */
  public void setCenterY(double centerY) {
    this.centerY = centerY;
  }

  /**
   * Gets the ball's radius.
   *
   * @return the radius of the ball
   */
  public double getRadius() {
    return radius;
  }

  /**
   * Sets the ball's radius.
   *
   * @param radius the new radius of the ball
   */
  public void setRadius(double radius) {
    this.radius = radius;
  }

  /**
   * Gets the ball's speed.
   *
   * @return the speed of the ball
   */
  public double getSpeed() {
    return speed;
  }

  /**
   * Sets the ball's speed.
   *
   * @param speed the new speed of the ball
   */
  public void setSpeed(double speed) {
    this.speed = speed;
  }

  /**
   * Gets the main paddle reference.
   *
   * @return the main paddle
   */
  public Paddle getPaddleMain() {
    return paddleMain;
  }

  /**
   * Gets the ball's image.
   *
   * @return the ball image
   */
  public Image getBallImage() {
    return ballImage;
  }

  /**
   * Sets the ball's image.
   *
   * @param ballImage the new ball image
   */
  public void setBallImage(Image ballImage) {
    this.ballImage = ballImage;
  }

  /**
   * Checks if the ball is invincible.
   *
   * @return true if invincible, false otherwise
   */
  public boolean isInvincible() {
    return invincible;
  }

  /**
   * Checks if this is the main ball.
   *
   * @return true if this is the main ball, false otherwise
   */
  public boolean isMainBall() {
    return mainBall;
  }

  /**
   * Sets whether this is the main ball.
   *
   * @param mainBall true to set as main ball, false otherwise
   */
  public void setMainBall(boolean mainBall) {
    this.mainBall = mainBall;
  }

  /**
   * Sets whether the ball is invincible.
   *
   * @param invincible true to make invincible, false otherwise
   */
  public void setInvincible(boolean invincible) {
    this.invincible = invincible;
  }

  /**
   * Gets the ball's hitbox.
   *
   * @return the hitbox shape of the ball
   */
  @Override
  public Shape getHitbox() {
    return this.hitbox;
  }

  /**
   * Gets the launch state of the ball.
   *
   * @return true if the ball has been launched, false otherwise
   */
  public boolean getLaunchState() {
    return hasLaunch;
  }

  /**
   * Sets the launch state of the ball.
   *
   * @param hasLaunch true to set as launched, false otherwise
   */
  public void setHasLaunch(boolean hasLaunch) {
    this.hasLaunch = hasLaunch;
  }

  /**
   * Sets the center position of the ball.
   *
   * @param x the new x-coordinate of the center
   * @param y the new y-coordinate of the center
   */
  public void setCenter(double x, double y) {
    this.centerX = x;
    this.centerY = y;
    super.setX((int) (x - radius));
    super.setY((int) (y - radius));
    this.hitbox.setCenter(new Point(x, y));
  }

}