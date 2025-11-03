package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;

public class Boss extends GameObject {
  private long spawnTime;
  private final long LIFE_TIME = 5_000; // 2 seconds
  private boolean isDead = false;
  private boolean hasRelocated = false;

  public Boss(double x, double y, double width, double height) {
    super(x, y, width, height);
    this.spawnTime = System.currentTimeMillis();
    AudioSet.bossSound.play();
  }

  public void update(double deltaTime) {
    long currentTime = System.currentTimeMillis();
    long elapsed = currentTime - spawnTime;

    // Die after 2 seconds
    if (elapsed >= LIFE_TIME) {
      isDead = true;
    }
  }

  public boolean isDead() {
    return isDead;
  }

  @Override
  public void render(GraphicsContext gc) {
    gc.drawImage(Basis.BOSS_TEXTURE, getX(), getY(), getWidth(), getHeight());
  }

  public Shape getHitbox() {
    return null;
  }
}