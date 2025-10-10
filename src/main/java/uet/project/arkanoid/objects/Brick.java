package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class Brick extends GameObject {
    private int hitPoints;
    private int type;
    private Image BRICK_TEXTURE = null;

    public Brick(int x, int y, int width, int height, int hitPoints, int type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int takeHit() {
            hitPoints--;
        return hitPoints;
    }

    public boolean isDestroy() {
        return hitPoints <= 0;
    }

    public void render(GraphicsContext gc) {
        String brickImage = "/Objects/Brick_"
                + String.valueOf(type) + "_"
                + String.valueOf(hitPoints)
                + ".png";

        BRICK_TEXTURE = new Image(
                Objects.requireNonNull(Brick.class.getResource(brickImage)).toExternalForm()
        );

        gc.drawImage(BRICK_TEXTURE, getX(), getY(), this.width, this.height);
    }

    public void update() {
    /*
    if(collisionWithBalls) takeHit(); cant cuz ball haven't move lol

        // to be honest this is singular update, i cant point to the index of it
        which mean i can't check isDestroy on here so i add on GameManger
        pretty sure its the same if you have 2 balls and deleting
    */
    }
}
