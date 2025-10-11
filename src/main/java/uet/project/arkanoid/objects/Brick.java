package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.project.arkanoid.objects.BrickVariants.IndestructibleBrick;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;


public class Brick extends GameObject {
    private int hitPoints;
    private int maxHp;
    private BrickType type;
    protected Image brickImage;

    public enum BrickType {
        INDESTRUCTIBLE,
        NORMAL
    }

    public Brick(int x, int y, int width, int height, int hitPoints, BrickType type) {
        super(x, y, width, height);
        this.maxHp = hitPoints;
        this.hitPoints = hitPoints;
        this.type = type;
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

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int takeHit() {
        if (hitPoints > 0) {
            hitPoints--;
        } else {
            hitPoints = 0;
        }
        return hitPoints;
    }

    public boolean isDestroy() {
        return hitPoints <= 0;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(brickImage, getX(), getY(), this.width, this.height);
    }

    public void update() {
    }
}

