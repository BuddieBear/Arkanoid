package uet.project.arkanoid.utils;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class AudioSet {

	public static final AudioClip wallBounceSound;
	public static final AudioClip lossHpSound;
	public static final AudioClip collisionPaddleSound;
	public static final AudioClip collisionBrickSound;
	public static final AudioClip gameOverSound;
	public static final AudioClip powerUpSound;
	public static final AudioClip thunderSound;
	private static MediaPlayer backgroundMusicPlayer;

	private static double musicVolume = 0.5;
	private static double soundVolume = 0.5;
	public static final AudioClip bossSound;

	static {
		wallBounceSound = load("/Sound/wall_bounce.wav");
		lossHpSound = load("/Sound/loss_hp.wav");
		collisionPaddleSound = load("/Sound/paddle_sound.wav");
		collisionBrickSound = load("/Sound/brick_sound.wav");
		gameOverSound = load("/Sound/game_over.wav");
		powerUpSound = load("/Sound/power_up.wav");
		thunderSound = load("/Sound/thunder.wav");
		loadBackgroundMusic("/Sound/Music/BackgroundMusic.mp3");
		bossSound = load("/Sound/devil.wav");

	}

	private static AudioClip load(String filename) {
		URL resource = AudioSet.class.getResource(filename);
		if (resource == null) {
			System.err.println("audio file is missing: " + filename);
			return null;
		}
		return new AudioClip(resource.toExternalForm());
	}

	private static void loadBackgroundMusic(String filename) {
		try {
			URL resource = AudioSet.class.getResource(filename);
			if (resource == null) {
				System.err.println("background music missing: " + filename);
				return;
			}
			Media media = new Media(resource.toExternalForm());
			backgroundMusicPlayer = new MediaPlayer(media);
			backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loop forever
			backgroundMusicPlayer.setVolume(0.5); // set default volume
		} catch (Exception e) {
			System.err.println("Error loading background music: " + e.getMessage());
		}
	}

	public static void playBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			System.out.println("playing background music");
			backgroundMusicPlayer.play();
		}
	}

	public static void pauseBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.pause();
		}
	}

	public static void stopBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.stop();
		}
	}

	public static void setMusicVolume(double volume) {
		musicVolume = Math.max(0, Math.min(1, volume));
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.setVolume(musicVolume);
		}
		System.out.println("Music volume set to: " + musicVolume);
	}

	public static void stopAllSounds() {
		try {
			if (wallBounceSound != null) {
				wallBounceSound.stop();
			}
			if (lossHpSound != null) {
				lossHpSound.stop();
			}
			if (lossHpSound != null) {
				lossHpSound.stop();
			}
			if (collisionPaddleSound != null) {
				collisionPaddleSound.stop();
			}
			if (collisionBrickSound != null) {
				collisionBrickSound.stop();
			}
			if (gameOverSound != null) {
				gameOverSound.stop();
			}
			if (powerUpSound != null) {
				powerUpSound.stop();
			}
			if (collisionPaddleSound != null) {
				collisionPaddleSound.stop();
			}
			if (collisionBrickSound != null) {
				collisionBrickSound.stop();
			}
			if (gameOverSound != null) {
				gameOverSound.stop();
			}
			if (powerUpSound != null) {
				powerUpSound.stop();
			}
			if (powerUpSound != null) {
				bossSound.stop();
			}
			if (thunderSound != null) {
				thunderSound.stop();
			}
		} catch (Exception e) {
			System.err.println("Error stopping sounds: " + e.getMessage());
		}
	}

	public static void setSoundVolume(double volume) {
		soundVolume = Math.max(0, Math.min(1, volume));
		System.out.println("Sound volume set to: " + soundVolume);
		if (wallBounceSound != null) {
			wallBounceSound.setVolume(soundVolume);
		}
		if (lossHpSound != null) {
			lossHpSound.setVolume(soundVolume);
		}
		if (collisionPaddleSound != null) {
			collisionPaddleSound.setVolume(soundVolume);
		}
		if (collisionBrickSound != null) {
			collisionBrickSound.setVolume(soundVolume);
		}
		if (gameOverSound != null) {
			gameOverSound.setVolume(soundVolume);
		}
		if (powerUpSound != null) {
			powerUpSound.setVolume(soundVolume);
		}
		if (thunderSound != null) {
			thunderSound.setVolume(soundVolume * 3);
		}
	}

	public static double getSoundVolume() {
		return soundVolume;
	}

	public static double getMusicVolume() {
		return musicVolume;
	}
}