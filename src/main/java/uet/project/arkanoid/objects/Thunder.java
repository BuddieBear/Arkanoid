package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.utils.AudioSet;
import uet.project.arkanoid.utils.Basis;

public class Thunder {

	private boolean showThunder = false;
	private boolean showCloud = false;
	private boolean showAimPoint = false;
	private boolean condition = false;
	private long currentTime;
	private int amount = 2;
	private int[] position;

	private double showTime = 0.5;

	private int aimPointWidth = 70;
	private int aimPointHeight = 70;
	private int thunderWidth = 50;
	private int cloudWidth = 300;
	private int cloudHeight = 100;

	public Thunder() {
		position = new int[amount];
		currentTime = System.nanoTime();
	}

	/**
	 * Generate random positions.
	 */
	public void randPosition() {
		for (int i = 0; i < amount; i++) {
            position[i] = Basis.STAGE_X + (int)(Basis.STAGE_WIDTH * Math.random());
		}
	}

	public void render(GraphicsContext gc) {
		if (showAimPoint) {
			for (int i = 0; i < amount; i++) {
				gc.drawImage(Basis.AIM_POINT_IMAGE, position[i] - aimPointWidth / 2,
						Basis.SCREEN_HEIGHT - aimPointHeight, aimPointWidth, aimPointHeight);
			}
		}

		if (showCloud) {
			for (int i = 0; i < amount; i++) {
				gc.drawImage(Basis.CLOUD_IMAGE, position[i] - cloudWidth / 2, -30, cloudWidth,
						cloudHeight);
			}
		}

		if (showThunder) {
			for (int i = 0; i < amount; i++) {
				gc.drawImage(Basis.THUNDER_IMAGE, position[i] - thunderWidth / 2, 30, thunderWidth,
						Basis.SCREEN_HEIGHT);
			}
			if ((System.nanoTime() - currentTime) / 1_000_000_000.0 >= showTime) {
				currentTime = System.nanoTime();
				showThunder = false;
				showCloud = false;
			}
		}
	}

	public void update() {
		long timeNow = System.nanoTime();
		if ((timeNow - currentTime) / 1_000_000_000.0 >= 3.5) {
			showAimPoint = true;
			randPosition();
			currentTime = System.nanoTime();
			condition = true;
		}
		if ((timeNow - currentTime) / 1_000_000_000.0 >= 1 && condition) {
			showCloud = true;
		}
		if ((timeNow - currentTime) / 1_000_000_000.0 >= 1.5 && condition) {
			showThunder = true;
			showAimPoint = false;
			condition = false;
			currentTime = System.nanoTime();
			AudioSet.thunderSound.play();
		}
	}

	public int[] getPosition() {
		return position;
	}

	public int getThunderWidth() {
		return thunderWidth;
	}

	public boolean getShowThunder() {
		return showThunder;
	}

	public int getAmount() {
		return amount;
	}
}
