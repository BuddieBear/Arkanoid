package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;

import java.util.List;

public class Ammo extends MovableObject {
    private double speed = 100;
    private boolean isDestroy = false;
    private Rectangle hitBox;

    @Override
    public Shape getHitbox() {
        return this.hitBox;
    }

    public Ammo(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.hitBox = new Rectangle(x + width / 2.0, y + height / 2.0, width, height, 0);

    }

    public void Collision(List<? extends GameObject> others) {
        for (GameObject obj : others) {
            if (this.hitBox.intersect(obj.getHitbox())) {
                if (obj instanceof Brick) {
                    ((Brick) obj).takeHit();
                    AudioSet.collisionBrickSound.play();
                    isDestroy = true;
                    break;

                }
            }
        }
    }

    public void update(double deltaTime) {
        move(deltaTime);
        if (getY() < 0) {
            isDestroy = true;
        }
        updateHitbox();
    }

    @Override
    public void move(double deltaTime) {
        setY(getY() - speed * deltaTime);
    }

    public void updateHitbox() {
        hitBox.setCenter(this.getX() + this.getWidth() / 2.0, this.getY() + this.getHeight() / 2);
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(Basis.AMMO_TEXTURE, getX(), getY(), width, height);
    }

    public boolean getIsDestroy() {
        return isDestroy;
    }

    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
    }

}
