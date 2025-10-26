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
        wallBounceSound = load("Assets/wall_bounce.wav");
        lossHpSound = load("Assets/loss_hp.wav");
        collisionPaddleSound = load("Assets/paddle_sound.wav");
        collisionBrickSound = load("Assets/brick_sound.wav");
        gameOverSound = load("Assets/game_over.wav");
        powerUpSound = load("Assets/power_up.wav");
    }

    private static AudioClip load(String filename) {
        URL resource = AudioSet.class.getResource("/" + filename);
        if (resource == null) {
            System.err.println("audio file is missing: " + filename);
            return null;
        }
        return new AudioClip(resource.toExternalForm());
    }
}