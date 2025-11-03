package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.base.Vector2D;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.Basis;

public class Brick extends MovableObject {

    private int hitPoints;
    private int maxHp;
    protected BrickType type;
    protected Image brickImage;
    private Rectangle hitBox;
    private GameSetup stage;

    private boolean movementActivated = false;
    private double moveSpeed = 0;
    private Point startPos;
    private double originalX, originalY;

    private double glowRadius = 0;
    private boolean glowIncreasing = true;
    double glowSpeed = 30;

    public enum BrickType {
        NORMAL,
        INDESTRUCTIBLE
    }

    public Brick(double centerX, double centerY, double width, double height, double rotation,
        int hitPoints, BrickType type, GameSetup stage) {
        super(centerX, centerY, width, height);
        this.maxHp = hitPoints;
        this.hitPoints = hitPoints;
        this.type = type;
        this.stage = stage;
        this.hitBox = new Rectangle(centerX, centerY, width, height, rotation);
        this.startPos = new Point(centerX, centerY);
        this.originalX = centerX;
        this.originalY = centerY;
    }

    @Override
    public void move(double deltaTime) {
        if (!movementActivated) {
            return;
        }

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
        if (movementActivated) {
            return;
        }

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


    public void update(double deltaTime) {
        if (movementActivated) {// pixels per second
            if (glowIncreasing) {
                glowRadius += glowSpeed * deltaTime;
                if (glowRadius > 40) {
                    glowIncreasing = false;
                }
            } else {
                glowRadius -= glowSpeed * deltaTime;
                if (glowRadius < 10) {
                    glowIncreasing = true;
                }
            }
        }
        if (isDestroy()) {
            return;
        }
        move(deltaTime);
    }

    public void render(GraphicsContext gc) {
        if (movementActivated) {
            Point center = hitBox.getCenter();
            double alpha = 0.2; // transparency
            gc.save();
            gc.setGlobalAlpha(alpha);
            gc.setFill(javafx.scene.paint.Color.RED);
            gc.fillOval(center.getX() - glowRadius, center.getY() - glowRadius,
                glowRadius * 2, glowRadius * 2);
            gc.restore();
        }

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
        if (type == BrickType.NORMAL) {
            this.hitPoints = hitPoints;
        }
    }

    public void takeHit() {
        if (type != BrickType.INDESTRUCTIBLE) {
            hitPoints--;
        }
    }

    public boolean isDestroy() {
        return hitPoints <= 0;
    }

    public BrickType getType() {
        return type;
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

    public double getOriginalX() {
        return originalX;
    }

    public double getOriginalY() {
        return originalY;
    }

    public void resetToOriginalPosition() {
        this.setX(originalX - getWidth() / 2.0);
        this.setY(originalY - getHeight() / 2.0);
        this.movementActivated = false;
        this.moveSpeed = 0;
        setDirection(new Vector2D(0, 0));

        this.hitBox.setCenter(new Point(originalX, originalY));

        System.out.println("RESET BRICK to: " + (originalX - getWidth() / 2.0) + ", " + (originalY
            - getHeight() / 2.0));
    }

    public void setMovementActivated(boolean movementActivated) {
        this.movementActivated = movementActivated;
    }
}