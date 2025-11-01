package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;

import java.util.List;

public class Ammo extends GameObject {
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

    public boolean getIsDestroy() {
        return isDestroy;
    }

    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
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
    public void update() {
        setY(getY() - 10);

        if (getY() < 0) {
            isDestroy = true;
        }
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(Basis.AMMO_TEXTURE, getX(), getY(), width, height);
    }
}
