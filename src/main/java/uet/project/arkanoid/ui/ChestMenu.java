package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.objects.deBuffVariants.ShrinkPaddle;
import uet.project.arkanoid.objects.powerUpVariants.*;
import uet.project.arkanoid.utils.Basis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChestMenu implements View {

  private static boolean chestMenuOpen = false;
  private static List<PowerUp.PowerUpType> chestChoices = new ArrayList<>();
  private boolean generatedPowerUps = false;

  public void onDraw(GraphicsContext gc) {
    // Dim background
    gc.setFill(Color.rgb(0, 0, 0, 0.6));
    gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

    gc.setFont(new Font("Comic Sans MS", 36));
    gc.setFill(Color.WHITE);
    gc.fillText("Pick one Power Up!", Basis.SCREEN_WIDTH / 2.0 - 180, 150);

    // Back Button
    gc.drawImage(Basis.BACK_BUTTON, Basis.BACK_X, Basis.BACK_Y, Basis.BACK_W, Basis.BACK_H);

    // Layout 4 boxes
    double startX = 200;
    double y = 300;
    double w = 200;
    double h = 100;
    double gap = 80;

    for (int i = 0; i < chestChoices.size(); i++) {
      double x = startX + i * (w + gap);

      // Draw box
      gc.setFill(Color.DARKGOLDENROD);
      gc.fillRoundRect(x, y, w, h, 20, 20);

      gc.setFill(Color.WHITE);
      gc.setFont(new Font("Comic Sans MS", 20));
      gc.fillText(chestChoices.get(i).name(), x + 30, y + 60);
    }
  }


  public GameState handleClick(double mouseX, double mouseY, GameSetup stage) {

    double startX = 200;
    double y = 300;
    double w = 200;
    double h = 100;
    double gap = 80;
    GameState result = GameState.CHEST_MENU;

    if (mouseX >= Basis.BACK_X && mouseX <= Basis.BACK_X + Basis.BACK_W &&
        mouseY >= Basis.BACK_Y && mouseY <= Basis.BACK_Y + Basis.BACK_H) {
      return GameState.PLAYING;
    }

    for (int i = 0; i < chestChoices.size(); i++) {
      double x = startX + i * (w + gap);

      if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
        PowerUp.PowerUpType chosenType = chestChoices.get(i);

        Brick randomBrick = new Brick(0, 0, 0, 0, 0, 0, Brick.BrickType.INDESTRUCTIBLE, stage);

        // Apply effect directly based on type
        PowerUp newPowerUp = switch (chosenType) {
          case DAMAGE_BRICK -> new DamageBrickPowerUp(randomBrick, 30, 30, stage);
          case INVINCIBLE_BALL -> new InvincibleBallPowerUp(randomBrick, 30, 30, stage);
          case MULTI_BALL -> new MultiBallPowerUp(randomBrick, 30, 30, stage);
          case SUPER_BALL -> new SuperBallPowerUp(randomBrick, 30, 30, stage);
          case EXTRA_LIFE -> new ExtraLifePowerUp(randomBrick, 30, 30, stage);
          case DOUBLE_SCORE -> new DoubleScorePowerUp(randomBrick, 30, 30, stage);
          case RESPAWN_FREE -> new RespawnFreePowerUp(randomBrick, 30, 30, stage);
          case EXPAND_PADDLE -> new ExtendPaddle(randomBrick, 30, 30, stage);
          case SHRINK_PADDLE -> new ShrinkPaddle(randomBrick, 30, 30, stage);
          default -> null;
        };
        if (newPowerUp != null) {
          newPowerUp.setCatchedPowerUp(true);
          newPowerUp.applyEffect();
          newPowerUp.startEffectTimer();
          stage.getPowerUps().add(newPowerUp);
          result = GameState.PLAYING;
        }
        closeChestMenu(stage);
        break;
      }
    }
    return result;
  }

  public void openChestMenu(GameSetup stage) {
    if (generatedPowerUps) {
      return;
    }
    generatedPowerUps = true;
    chestMenuOpen = true;
    stage.setCurrentState(GameState.CHEST_MENU);

    List<PowerUp.PowerUpType> allBuffs = new ArrayList<>(List.of(PowerUp.PowerUpType.values()));
    Collections.shuffle(allBuffs);
    chestChoices = allBuffs.subList(0, 4);
    System.out.println("Chest Menu Open: " + chestChoices.size());
  }

  public void closeChestMenu(GameSetup stage) {
    chestMenuOpen = false;
    chestChoices.clear();
    stage.resumeGame();
    generatedPowerUps = false;
  }

  public static boolean isChestMenuOpen() {
    return chestMenuOpen;
  }
}
