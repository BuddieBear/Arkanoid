package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.game.GameSetup;


public class Brick extends GameObject {
    private int hitPoints;
    private int maxHp;

    private BrickType type;
    protected Image brickImage;
    private GameSetup stage;

    private final double originalWidth;
    private final double originalHeight;

    private Rectangle hitBox;

    public enum BrickType {
        INDESTRUCTIBLE,
        NORMAL
    }



    public Brick(int x, int y, double width, double height, double rotation, int hitPoints, BrickType type, GameSetup stage) {
        super(x, y, width, height);
        this.maxHp = hitPoints;
        this.hitPoints = hitPoints;
        this.type = type;
        this.stage = stage;
        this.originalWidth = width;
        this.originalHeight = height;
        this.hitBox = new Rectangle(x + width / 2.0, y + height / 2.0, width, height, rotation);
    }



    public void render(GraphicsContext gc) {
        Point center = hitBox.getCenter();
        double rotationDegrees = Math.toDegrees(hitBox.getRotation());
        double w = hitBox.getSize().getX();
        double h = hitBox.getSize().getY();

        gc.save(); // Save the current graphics state
        gc.translate(center.getX(), center.getY()); // Move origin to brick's center
        gc.rotate(rotationDegrees); // Rotate the canvas

        // Draw the image centered at the new (0,0) origin
        gc.drawImage(brickImage, -w / 2.0, -h / 2.0, w, h);

        gc.restore(); // Restore the original state
    }

    @Override
    public void update() {
    }


    public void takeHit() {
        hitPoints--;
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

    public void setHitPoints(int hitPoints) {
        if(type == BrickType.NORMAL) {
            this.hitPoints = hitPoints;
        }
    }

    public Shape getHitbox() {
        return this.hitBox;
    }

    public double getOriginalWidth() {
        return originalWidth;
    }

    public double getOriginalHeight() {
        return originalHeight;
    }
}

