package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.objects.deBuffVariants.HarderBrickPowerDown;
import uet.project.arkanoid.objects.powerUpVariants.*;
import uet.project.arkanoid.ui.ChestMenu;
import uet.project.arkanoid.ui.gameUI;
import uet.project.arkanoid.utils.Basis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chest extends GameObject {
    private final GameSetup stage;
    private final Rectangle hitbox;
    private boolean opened = false;

    public Chest(double x, double y, double width, double height, GameSetup stage) {
        super(x, y, width, height);
        this.stage = stage;
        this.hitbox = new Rectangle(x + width / 2, y + height / 2, width, height, 0);
    }

    @Override
    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public void update() {
        return;
    }

    public boolean collision(List<? extends GameObject> others) {
        for (GameObject obj : others) {
            if (obj.getHitbox().intersect(this.hitbox)) {
                openChest();
                return true;
            }
        }
        return false;
    }

    public void openChest() {
        opened = true;
        System.out.println("Chest opened");
        stage.setCurrentState(GameState.CHEST_MENU);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!opened) {
            gc.drawImage(Basis.CHEST_CLOSE, getX(), getY(), getWidth(), getHeight());
        } else {
            gc.drawImage(Basis.CHEST_OPEN, getX(), getY(), getWidth(), getHeight());
        }
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean hasOpened() {
        return opened;
    }
}