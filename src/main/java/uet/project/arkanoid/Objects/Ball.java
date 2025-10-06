package uet.project.arkanoid.Objects;

public class Ball extends MovableObject {
    private int speed = 0;
    private int directionX = 0;
    private int directionY = 0;


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public Ball(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
    }

    public boolean bounceOff(GameObject other) {
        return false;
    }

    public boolean checkCollision(GameObject other) {
        return false;
    }

    public void move(){

    }

    public void render(){

    }

    public void update(){

    }
}
