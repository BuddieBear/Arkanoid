package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.Basis;
import uet.project.arkanoid.GameManager;

public class Ball extends MovableObject {
    private int speed = 0;
    private int directionX = 0;
    private int directionY = 0;
    private boolean back = false;

    public Ball(int x, int y, int width, int height, int speed, int dx, int dy) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public boolean bounceOff(GameObject other) {
        return false;
    }

    public boolean checkCollision(GameObject other) {
        return false;
    }

    public void move() {

        Paddle paddleMain = GameManager.getPaddle();
        if (paddleMain.getX() + 33 != Basis.STAGE_TEST_X 
        && paddleMain.getX() + paddleMain.getWidth() != Basis.STAGE_TEST_X + Basis.STAGE_TEST_WIDTH + 33) {   // 33 is the padding of the paddle.
            setX(paddleMain.getDx() + getX());
        }
        if (! back) {
            setX(getX() + getDx());
        } else {
            setX(getX() - getDx());
        }

    }

    public void render(GraphicsContext gc) {
        gc.drawImage(Basis.BALL_TEXTURE, getX(), getY(), this.width, this.height);
    }

    public void update() {
        move();

        if (this.getX() >= GameManager.getPaddle().getX() + GameManager.getPaddle().getWidth() - this.getWidth() - 33) {  // 33 is the padding of the paddle.
            back = true;
        } else if (this.getX() <= GameManager.getPaddle().getX() + 33){
            back = false;
        }
    }

}
