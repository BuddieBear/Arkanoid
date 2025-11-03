package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.base.Vector2D;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.Basis;

/**
 * Represents a brick that can move during boss sequence.
 */
public class Brick extends MovableObject {

  private int hitPoints;
  private int maxHp;
  private BrickType type;
  private Image brickImage;
  private Rectangle hitBox;
  private GameSetup stage;

  private boolean movementActivated = false;
  private double moveSpeed = 0;
  private Point startPos;

  public enum BrickType {
    NORMAL,
    INDESTRUCTIBLE
  }

  public Brick(double centerX, double centerY, double width, double height,
      int hitPoints, BrickType type, GameSetup stage) {
    super(centerX, centerY, width, height);
    this.maxHp = hitPoints;
    this.hitPoints = hitPoints;
    this.type = type;
    this.stage = stage;
    this.hitBox = new Rectangle(centerX, centerY, width, height, 0);
    this.startPos = new Point(centerX, centerY);
  }

  //==================== Movement Logic ====================//

  @Override
  public void move(double deltaTime) {
    if (!movementActivated) return;

    double dx = getDx() * moveSpeed * deltaTime;
    double dy = getDy() * moveSpeed * deltaTime;

    setX(getX() + dx);
    setY(getY() + dy);
    hitBox.setCenter(new Point(hitBox.getCenter().getX() + dx, hitBox.getCenter().getY() + dy));

    // Reset if falls off screen
    if (getY() > Basis.SCREEN_HEIGHT) {
      resetMovement();
    }
  }

  public void startBossMovement(double targetX, double targetY, double speed) {
    if (movementActivated) return;

    this.movementActivated = true;
    this.moveSpeed = speed;

    double dx = targetX - hitBox.getCenter().getX();
    double dy = targetY - hitBox.getCenter().getY();
    double len = Math.sqrt(dx * dx + dy * dy);

    if (len > 0) {
      setDirection(new Vector2D(dx / len, dy / len));
    }
  }

  public void resetMovement() {
    movementActivated = false;
    setDirection(new Vector2D(0, 0));
    setX(startPos.getX() - getWidth() / 2.0);
    setY(startPos.getY() - getHeight() / 2.0);
    hitBox.setCenter(startPos);
  }

  //==================== Update & Render ====================//

  public void update(double deltaTime) {
    if (isDestroy()) return;
    move(deltaTime);
  }

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

  //==================== Brick Logic ====================//

  public void takeHit() {
    if (type != BrickType.INDESTRUCTIBLE) hitPoints--;
  }

  public boolean isDestroy() {
    return hitPoints <= 0;
  }

  public BrickType getType() {
    return type;
  }

  public void setType(BrickType type) {
    this.type = type;
  }

  public int getMaxHp() {
    return maxHp;
  }

  public int getHitPoints() {
    return hitPoints;
  }

  @Override
  public Shape getHitbox() {
    return hitBox;
  }

  public boolean isMovementActivated() {
    return movementActivated;
  }

  public void setBrickImage(Image image) {
    this.brickImage = image;
  }
}