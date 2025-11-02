package uet.project.arkanoid.utils;
import javafx.scene.media.AudioClip;
import java.net.URL;

public class AudioSet {
    public static final AudioClip wallBounceSound;
    public static final AudioClip lossHpSound;
    public static final AudioClip collisionPaddleSound;
    public static final AudioClip collisionBrickSound;
    public static final AudioClip gameOverSound;
    public static final AudioClip powerUpSound;

    static {
        wallBounceSound = load("/Sound/wall_bounce.wav");
        lossHpSound = load("/Sound/loss_hp.wav");
        collisionPaddleSound = load("/Sound/paddle_sound.wav");
        collisionBrickSound = load("/Sound/brick_sound.wav");
        gameOverSound = load("/Sound/game_over.wav");
        powerUpSound = load("/Sound/power_up.wav");
    }

    private static AudioClip load(String filename) {
        URL resource = AudioSet.class.getResource(filename);
        if (resource == null) {
            System.err.println("audio file is missing: " + filename);
            return null;
        }
        return new AudioClip(resource.toExternalForm());
    }

    public static void stopAllSounds() {
        try {
            if (wallBounceSound != null) wallBounceSound.stop();
            if (lossHpSound != null) lossHpSound.stop();
            if (collisionPaddleSound != null) collisionPaddleSound.stop();
            if (collisionBrickSound != null) collisionBrickSound.stop();
            if (gameOverSound != null) gameOverSound.stop();
            if (powerUpSound != null) powerUpSound.stop();
        } catch (Exception e) {
            System.err.println("Error stopping sounds: " + e.getMessage());
        }
    }
}