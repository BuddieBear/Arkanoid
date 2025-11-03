package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.game.GameSetup;

/**
 * Represents a brick in the Arkanoid game.
 * Bricks can be destroyed by ball collisions and have different types and hit points.
 *
 * <p>This class handles brick rendering, hit point management, and destruction logic.
 */
public class Brick extends GameObject {

  private int hitPoints;
  private int maxHp;

  private BrickType type;
  protected Image brickImage;
  private GameSetup stage;

  private final double originalWidth;
  private final double originalHeight;

  private Rectangle hitBox;

  /**
   * Enum representing different types of bricks.
   */
  public enum BrickType {
    INDESTRUCTIBLE,
    NORMAL
  }

  /**
   * Constructs a new Brick with specified parameters.
   *
   * @param centerX the x-coordinate of the brick's center
   * @param centerY the y-coordinate of the brick's center
   * @param width the width of the brick
   * @param height the height of the brick
   * @param rotation the rotation angle of the brick in radians
   * @param hitPoints the initial hit points of the brick
   * @param type the type of brick (INDESTRUCTIBLE or NORMAL)
   * @param stage the game stage this brick belongs to
   */
  public Brick(double centerX, double centerY, double width, double height, double rotation,
      int hitPoints, BrickType type, GameSetup stage) {
    super(centerX, centerY, width, height);
    this.maxHp = hitPoints;
    this.hitPoints = hitPoints;
    this.type = type;
    this.stage = stage;
    this.originalWidth = width;
    this.originalHeight = height;
    this.hitBox = new Rectangle(centerX, centerY, width, height, rotation);
  }

  /**
   * Renders the brick on the graphics context with proper rotation.
   *
   * @param gc the graphics context to render on
   */
  public void render(GraphicsContext gc) {
    Point center = hitBox.getCenter();
    double rotationDegrees = Math.toDegrees(hitBox.getRotation());
    double w = hitBox.getSize().getX();
    double h = hitBox.getSize().getY();

    gc.save();
    gc.translate(center.getX(), center.getY());
    gc.rotate(rotationDegrees);

    gc.drawImage(brickImage, -w / 2.0, -h / 2.0, w, h);

    gc.restore();
  }

  /**
   * Updates the brick's state.
   *
   * <p>This method is empty as bricks don't require per-frame updates.
   *
   * @param DeltaTime the time elapsed since the last update
   */
  @Override
  public void update(double DeltaTime) {
    return;
  }

  /**
   * Reduces the brick's hit points by one when hit.
   * Only affects NORMAL type bricks.
   */
  public void takeHit() {
    hitPoints--;
  }

  /**
   * Checks if the brick is destroyed.
   *
   * @return true if hit points are 0 or less, false otherwise
   */
  public boolean isDestroy() {
    return hitPoints <= 0;
  }

  /**
   * Gets the type of the brick.
   *
   * @return the brick type
   */
  public BrickType getType() {
    return type;
  }

  /**
   * Sets the type of the brick.
   *
   * @param type the new brick type
   */
  public void setType(BrickType type) {
    this.type = type;
  }

  /**
   * Gets the maximum hit points of the brick.
   *
   * @return the maximum hit points
   */
  public int getMaxHp() {
    return maxHp;
  }

  /**
   * Gets the current hit points of the brick.
   *
   * @return the current hit points
   */
  public int getHitPoints() {
    return hitPoints;
  }

  /**
   * Sets the hit points of the brick.
   * Only affects NORMAL type bricks.
   *
   * @param hitPoints the new hit points value
   */
  public void setHitPoints(int hitPoints) {
    if (type == BrickType.NORMAL) {
      this.hitPoints = hitPoints;
    }
  }

  /**
   * Gets the hitbox of the brick.
   *
   * @return the hitbox shape
   */
  public Shape getHitbox() {
    return this.hitBox;
  }

  /**
   * Gets the original width of the brick.
   *
   * @return the original width
   */
  public double getOriginalWidth() {
    return originalWidth;
  }

  /**
   * Gets the original height of the brick.
   *
   * @return the original height
   */
  public double getOriginalHeight() {
    return originalHeight;
  }
}