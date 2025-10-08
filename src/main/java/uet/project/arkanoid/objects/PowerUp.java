package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;

public class PowerUp extends GameObject {
    private final PowerUpType type;
    private final double duration;
    public PowerUp(int x, int y, int width, int height, PowerUpType type, double duration) {
        super(x, y, width, height);
        this.type = type;
        this.duration = duration;
    }

    public void applyEffect(Paddle paddle) {
    }

    public void removeEffect(Paddle paddle) {

    }

    public PowerUpType getType() {
        return type;
    }

    public double getDuration() {
        return duration;
    }

    public void update() {

    }

    public void render(GraphicsContext gc) {

    }

    public enum PowerUpType {
        EXPAND_PADDLE,
        SHRINK_PADDLE,
        EXTRA_LIFE,
        MULTI_BALL,
        SPEED_UP,
        SLOW_DOWN
    }
}
