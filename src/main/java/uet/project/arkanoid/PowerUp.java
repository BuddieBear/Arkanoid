package uet.project.arkanoid;

public class PowerUp {
    public enum PowerUpType {
        EXPAND_PADDLE,
        SHRINK_PADDLE,
        EXTRA_LIFE,
        MULTI_BALL,
        SPEED_UP,
        SLOW_DOWN
    }

    private PowerUpType type;
    private double duration;

    public PowerUp(PowerUpType type, double duration) {
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
}
