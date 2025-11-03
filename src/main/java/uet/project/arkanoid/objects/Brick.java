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
/*
package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.base.Vector2D;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.Basis;

public class Brick extends MovableObject {
    private int hitPoints;
    private int maxHp;

    private BrickType type;
    protected Image brickImage;
    private GameSetup stage;
    private Rectangle hitBox;

    // Các thuộc tính cho di chuyển khi có boss
    private boolean isMoving = false;
    private double moveDelay;
    private long startTime;
    private Point startPosition;
    private boolean movementActivated = false;

    public enum BrickType {
        INDESTRUCTIBLE,
        NORMAL
    }

    @Override
    public Shape getHitbox() {
        return this.hitBox;
    }

    public Brick(int x, int y, double width, double height, double rotation, int hitPoints, BrickType type, GameSetup stage) {
        super((int)x, (int)y, width, height);
        this.maxHp = hitPoints;
        this.hitPoints = hitPoints;
        this.type = type;
        this.stage = stage;
        this.hitBox = new Rectangle(x + width / 2.0, y + height / 2.0, width, height, rotation);

        // Khởi tạo vị trí bắt đầu
        this.startPosition = new Point(x + width / 2.0, y + height / 2.0);
        this.startTime = System.currentTimeMillis();
        this.moveDelay = 10; // Delay ngắn 0.05 giây
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

    public void setHitPoints(int hitPoints) {
        if(type == BrickType.NORMAL) {
            this.hitPoints = hitPoints;
        }
    }

    public void takeHit() {
        hitPoints--;
    }

    public boolean isDestroy() {
        return hitPoints <= 0;
    }

    /**
     * Bắt đầu di chuyển khi có boss spawn
     */
//public void startBossMovement(double targetX, double targetY) {
//  if (movementActivated) return;
//
//  this.movementActivated = true;
//
//  // Tính toán hướng di chuyển đến target (paddle)
//  double dx = targetX - startPosition.getX();
//  double dy = targetY - startPosition.getY();
//  double distance = Math.sqrt(dx * dx + dy * dy);
//
//  if (distance > 0) {
//    setDirection(new Vector2D(dx / distance, dy / distance));
//    setSpeed(10.0); // Tốc độ nhanh
//  }
//
//  // Reset thời gian bắt đầu
//  this.startTime = System.currentTimeMillis();
//}
//
//public void stopMoving() {
//  this.isMoving = false;
//  this.movementActivated = false; // Reset để có thể kích hoạt lại// QUAN TRỌNG: Đánh dấu đã về vị trí cũ
//  setDx(0);
//  setDy(0);
//}
//
//public boolean isMoving() {
//  return isMoving;
//}
//
//public boolean isMovementActivated() {
//  return movementActivated;
//}
//
///**
// * Thiết lập tốc độ di chuyển
// */
//public void setSpeed(double speed) {
//  Vector2D currentDir = getDirection();
//  if (currentDir.getLength() > 0) {
//    Vector2D normalized = new Vector2D(
//        currentDir.getX() / currentDir.getLength(),
//        currentDir.getY() / currentDir.getLength()
//    );
//    setDx(normalized.getX() * speed);
//    setDy(normalized.getY() * speed);
//  }
//}
//
//@Override
//public void move() {
//  if (!isMoving || isDestroy()) {
//    return;
//  }
//
//  // Lưu vị trí cũ
//  Point oldPosition = this.hitBox.getCenter();
//
//  // Tính vị trí mới
//  double newX = oldPosition.getX() + getDx();
//  double newY = oldPosition.getY() + getDy();
//
//  // Kiểm tra nếu vượt quá cận dưới
//  if (newY > Basis.SCREEN_HEIGHT) {
//    // VỀ VỊ TRÍ CŨ NGAY LẬP TỨC
//    this.hitBox.setCenter(startPosition);
//    setX(startPosition.getX() - getWidth() / 2.0);
//    setY(startPosition.getY() - getHeight() / 2.0);
//
//    // DỪNG DI CHUYỂN VÀ MẤT DÂY
//    stopMoving();
//    return;
//  }
//
//  // Cập nhật hitbox
//  this.hitBox.setCenter(new Point(newX, newY));
//
//  // Cập nhật vị trí GameObject
//  setX(newX - getWidth() / 2.0);
//  setY(newY - getHeight() / 2.0);
//}
//
//@Override
//public void update() {
//  if (this.isDestroy()) {
//    stage.addScore(maxHp * 10);
//  }
//
//  // Nếu đã được kích hoạt nhưng chưa bắt đầu di chuyển, kiểm tra delay
//  if (movementActivated && !isMoving && !isDestroy()) {
//    this.isMoving = true;
//  }
//
//  if (isMoving) {
//    move();
//  }
//}
//
//@Override
//public void render(GraphicsContext gc) {
//  Point center = hitBox.getCenter();
//  double rotationDegrees = Math.toDegrees(hitBox.getRotation());
//  double w = hitBox.getSize().getX();
//  double h = hitBox.getSize().getY();
//
//  gc.save();
//  gc.translate(center.getX(), center.getY());
//  gc.rotate(rotationDegrees);
//
//
//  // Draw the brick image
//  if (brickImage != null) {
//    gc.drawImage(brickImage, -w / 2.0, -h / 2.0, w, h);
//  }
//
//  gc.restore();
//}
//
//public Point getStartPosition() {
//  return startPosition;
//}
//
//}

/*
public void update() {
  if (currentState == GameState.PLAYING) {
    if (stage.gameWin() || stage.gameLose()) {
      currentState = GameState.GAME_OVER;
      return;
    }

    // TODO: Runs update() on every GameObject.
    for (Paddle paddle : stage.getPaddles()) {
      paddle.update();
    }
    for (Ball ball : stage.getBalls()) {
      ball.Collision(stage.getBricks());
      ball.Collision(stage.getPaddles());
      ball.update();
    }
    stage.addPowerUp(stage.getBricks());

    for (PowerUp powerUp : stage.getPowerUps()) {
      powerUp.update();
    }

    if (stage.getScore() >= 20 && !bossSequenceActive) {
      startBossSequence();
    }

    // Spawn từng brick với interval
    if (bossSequenceActive && currentBrickIndex < stage.getBricks().size()) {
      long currentTime = System.currentTimeMillis();
      if (currentTime - lastBrickSpawnTime >= 400) { // 2 GIÂY giữa mỗi brick
        spawnNextBrick();
        lastBrickSpawnTime = currentTime;
        currentBrickIndex++;
      }
    }

    // Reset boss sequence khi đã spawn hết brick
    if (bossSequenceActive && currentBrickIndex >= stage.getBricks().size()) {
      bossSequenceActive = false;
      bossSpawned = true;
    }

    for (Brick brick : stage.getBricks()) {
      brick.update();
      if (brick.isDestroy()) {
        stage.addScore(brick.getMaxHp()*10);
      }
    }
    stage.getPowerUps().removeIf(PowerUp::isDead);
    stage.getBricks().removeIf(Brick::isDestroy);
  }
}
public void stop() {

}

private void startBossSequence() {
  bossSequenceActive = true;
  currentBrickIndex = 0;
  lastBrickSpawnTime = System.currentTimeMillis();
}

private void spawnNextBrick() {
  if (currentBrickIndex >= stage.getBricks().size()) return;

  Brick brick = stage.getBricks().get(currentBrickIndex);
  if (!brick.isDestroy() && !brick.isMovementActivated()) {
    Paddle paddle = stage.getPaddles().get(0);
    brick.startBossMovement(
        paddle.getX() + paddle.getWidth()/2,
        paddle.getY() + paddle.getHeight()/2
    );
    brick.setSpeed(6.0); // TĂNG TỐC ĐỘ LÊN 6.0 (rất nhanh)
  }
}
*/