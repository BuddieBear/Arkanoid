package uet.project.arkanoid.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.game.GameState;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;

public class Setting implements View {

    private static final double SLIDER_X = Basis.SCREEN_WIDTH / 2 - 200;
    private static final double SLIDER_WIDTH = 400;
    private static final double SLIDER_HEIGHT = 20;

    private boolean draggingMusic = false;
    private boolean draggingSound = false;

    // 🔘 Toggle for background music
    private boolean musicEnabled = true;

    @Override
    public void onDraw(GraphicsContext gc) {
        gc.setFill(Color.DARKSLATEBLUE);
        gc.fillRect(0, 0, Basis.SCREEN_WIDTH, Basis.SCREEN_HEIGHT);

        gc.drawImage(Basis.BACK_BUTTON, Basis.BACK_X, Basis.BACK_Y, Basis.BACK_W, Basis.BACK_H);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 48));
        gc.fillText("Settings", Basis.SCREEN_WIDTH / 2 - 120, 100);

        gc.setFont(new Font("Arial", 28));

        double musicY = 250;
        double soundY = 400;

        // 🎵 Music toggle
        gc.setFill(musicEnabled ? Color.LIGHTGREEN : Color.RED);
        gc.fillRoundRect(SLIDER_X + SLIDER_WIDTH + 60, musicY - 10, 100, 40, 10, 10);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.fillText(musicEnabled ? "ON" : "OFF", SLIDER_X + SLIDER_WIDTH + 90, musicY + 15);

        // Music volume slider
        gc.setFont(new Font("Arial", 28));
        gc.setFill(Color.WHITE);
        gc.fillText("Music Volume", SLIDER_X - 220, musicY + 10);
        drawSlider(gc, SLIDER_X, musicY, AudioSet.getMusicVolume());

        // Sound effects slider
        gc.fillText("Sound Volume", SLIDER_X - 220, soundY + 10);
        drawSlider(gc, SLIDER_X, soundY, AudioSet.getSoundVolume());
    }

    private void drawSlider(GraphicsContext gc, double x, double y, double value) {
        gc.setFill(Color.GRAY);
        gc.fillRoundRect(x, y, SLIDER_WIDTH, SLIDER_HEIGHT, 10, 10);

        gc.setFill(Color.LIGHTGREEN);
        gc.fillRoundRect(x, y, SLIDER_WIDTH * value, SLIDER_HEIGHT, 10, 10);

        gc.setFill(Color.WHITE);
        gc.fillOval(x + SLIDER_WIDTH * value - 10, y - 5, 30, 30);
    }

    @Override
    public GameState handleClick(double mouseX, double mouseY, GameSetup stage) {
        // Back button
        if (back(mouseX, mouseY)) {
            return GameState.MENU;
        }

        double musicY = 250;
        double soundY = 400;

        // Toggle background music if clicked on the ON/OFF box
        double toggleX = SLIDER_X + SLIDER_WIDTH + 60;
        double toggleY = musicY - 10;
        if (mouseX >= toggleX && mouseX <= toggleX + 100 &&
                mouseY >= toggleY && mouseY <= toggleY + 40) {
            toggleMusic();
        }

        // Sliders
        if (isOnSlider(mouseX, mouseY, SLIDER_X, musicY)) {
            updateMusicVolume(mouseX);
            draggingMusic = true;
        } else if (isOnSlider(mouseX, mouseY, SLIDER_X, soundY)) {
            updateSoundVolume(mouseX);
            draggingSound = true;
        }

        return GameState.SETTING;
    }

    private void toggleMusic() {
        musicEnabled = !musicEnabled;
        if (musicEnabled) {
            AudioSet.playBackgroundMusic();
        } else {
            AudioSet.pauseBackgroundMusic();
        }
    }

    private boolean isOnSlider(double mouseX, double mouseY, double x, double y) {
        return mouseX >= x && mouseX <= x + SLIDER_WIDTH
                && mouseY >= y - 10 && mouseY <= y + SLIDER_HEIGHT + 10;
    }

    private void updateMusicVolume(double mouseX) {
        double volume = (mouseX - SLIDER_X) / SLIDER_WIDTH;
        volume = Math.max(0, Math.min(1, volume));
        AudioSet.setMusicVolume(volume);
    }

    private void updateSoundVolume(double mouseX) {
        double volume = (mouseX - SLIDER_X) / SLIDER_WIDTH;
        volume = Math.max(0, Math.min(1, volume));
        AudioSet.setSoundVolume(volume);
    }

    public static boolean back(double mouseX, double mouseY) {
        return mouseX >= Basis.BACK_X && mouseX <= Basis.BACK_X + Basis.BACK_W
                && mouseY >= Basis.BACK_Y && mouseY <= Basis.BACK_Y + Basis.BACK_H;
    }

    public void handleMouseDrag(double mouseX, double mouseY) {
        if (draggingMusic) {
            System.out.println("Dragging MUSIC at " + AudioSet.getMusicVolume());
            updateMusicVolume(mouseX);
        }
        if (draggingSound) {
            System.out.println("Dragging SOUND at x=" + mouseX);
            updateSoundVolume(mouseX);
        }
    }

    public void handleMouseRelease() {
        draggingMusic = false;
        draggingSound = false;
    }
}
