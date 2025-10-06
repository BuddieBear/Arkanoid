package uet.project.arkanoid;

public class Paddle extends MovableObject {
    private int speed = 0;
    private PowerUp currentPowerUp = null;

    public Paddle(int x,int y,int width, int height) {
        super(x, y, width, height);
    }

    //TODO: Setter
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

    }

    public void moveRight() {

    }

    public void move() {

    }

    public void render() {

    }

    public void update() {

    }
}
