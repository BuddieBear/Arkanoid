package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.BrickVariants.IndestructibleBrick;
import uet.project.arkanoid.utils.Basis;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;


public class Brick extends GameObject {
    private int hitPoints;
    private int maxHp;
    private BrickType type;
    protected Image brickImage;
    private GameSetup stage;
    private final double originalWidth;
    private final double originalHeight;

    public enum BrickType {
        INDESTRUCTIBLE,
        NORMAL
    }

    public double getOriginalWidth() {
        return originalWidth;
    }

    public double getOriginalHeight() {
        return originalHeight;
    }

    public Brick(int x, int y, double width, double height, int hitPoints, BrickType type, GameSetup stage) {
        super(x, y, width, height);
        this.maxHp = hitPoints;
        this.hitPoints = hitPoints;
        this.type = type;
        this.stage = stage;
        this.originalWidth = width;
        this.originalHeight = height;
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

    public void render(GraphicsContext gc) {
        gc.drawImage(brickImage, getX(), getY(), this.width, this.height);
    }

    @Override
    public void update() {
        if (this.isDestroy()) {
            stage.addScore(maxHp * 10);
        }
    }
}

