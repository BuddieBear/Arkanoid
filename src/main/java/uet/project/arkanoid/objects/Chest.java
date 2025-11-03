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

/**
 * Represents a chest in the Arkanoid game that can be opened to reveal rewards.
 *
 * <p>Chests can be collided with by balls and when opened, transition the game to a chest menu
 * state.
 */
public class Chest extends GameObject {

  private final GameSetup stage;
  private final Rectangle hitbox;
  private boolean opened = false;

  /**
   * Constructs a new Chest with specified parameters.
   *
   * @param x      the x-coordinate of the chest's top-left corner
   * @param y      the y-coordinate of the chest's top-left corner
   * @param width  the width of the chest
   * @param height the height of the chest
   * @param stage  the game stage this chest belongs to
   */
  public Chest(double x, double y, double width, double height, GameSetup stage) {
    super(x, y, width, height);
    this.stage = stage;
    this.hitbox = new Rectangle(x + width / 2, y + height / 2, width, height, 0);
  }

  /**
   * Gets the hitbox of the chest.
   *
   * @return the rectangular hitbox of the chest
   */
  @Override
  public Rectangle getHitbox() {
    return hitbox;
  }

  /**
   * Updates the chest's state.
   *
   * <p>This method is empty as chests don't require per-frame updates.
   *
   * @param DeltaTime the time elapsed since the last update
   */
  @Override
  public void update(double DeltaTime) {
    return;
  }

  /**
   * Checks for collisions with other game objects and opens the chest if collision occurs.
   *
   * @param others the list of game objects to check collisions with
   * @return true if a collision occurred and chest was opened, false otherwise
   */
  public boolean collision(List<? extends GameObject> others) {
    for (GameObject obj : others) {
      if (obj.getHitbox().intersect(this.hitbox)) {
        openChest();
        return true;
      }
    }
    return false;
  }

  /**
   * Opens the chest and transitions the game to the chest menu state.
   */
  public void openChest() {
    opened = true;
    System.out.println("Chest opened");
    stage.setCurrentState(GameState.CHEST_MENU);
  }

  /**
   * Renders the chest on the graphics context.
   *
   * <p>Displays different images based on whether the chest is open or closed.
   *
   * @param gc the graphics context to render on
   */
  @Override
  public void render(GraphicsContext gc) {
    if (!opened) {
      gc.drawImage(Basis.CHEST_CLOSE, getX(), getY(), getWidth(), getHeight());
    } else {
      gc.drawImage(Basis.CHEST_OPEN, getX(), getY(), getWidth(), getHeight());
    }
  }

  /**
   * Sets the opened state of the chest.
   *
   * @param opened true to mark the chest as opened, false as closed
   */
  public void setOpened(boolean opened) {
    this.opened = opened;
  }

  /**
   * Checks if the chest has been opened.
   *
   * @return true if the chest is opened, false otherwise
   */
  public boolean hasOpened() {
    return opened;
  }
}