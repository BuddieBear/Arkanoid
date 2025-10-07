package uet.project.arkanoid.Objects;

public class Paddle extends MovableObject {
    private int speed = 0;
    private PowerUp currentPowerUp = null;

    public Paddle(int x,int y,int width, int height) {
        super(x, y, width, height);
    }

    public int getSpeed() {
        return this.speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public PowerUp getCurrentPowerUp() {
        return this.currentPowerUp;
    }
    public void setCurrentPowerUp(PowerUp current) {
        this.currentPowerUp = current;
    }

    public void moveLeft() {
        setDx(-speed);
    }

    public void moveRight() {
        setDx(speed);
    }

    @Override
    public void move() {
        setX(getX() + getDx());
    }

    public void render() {

    }

    public void update() {

    }
}
