package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.Basis;
import uet.project.arkanoid.GameManager;

public class Ball extends MovableObject {
    private int speed = 0;
    private int directionX = 0;
    private int directionY = 0;
    private int angle = 0;
    private boolean first = false;  // Check if it’s the first shot.

    Paddle paddleMain = GameManager.getPaddle();

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

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean bounceOff(GameObject other) {
        return false;
    }

    public boolean checkCollision(GameObject other) {
        return false;
    }

    // Make the ball slide on the paddle.
    public void glide() {
        setX(paddleMain.getX() + paddleMain.getWidth() / 2 - 25);
    }

    public void move() {
        if (first) {
            this.angle = 90 - Arrow.getAngle();
            double angleRadian = Math.toRadians(angle);
            setDx((int)(this.speed * Math.cos(angleRadian)));
            setDy((int)(this.speed * Math.sin(angleRadian)));
            System.out.println("Angle in the ball: " + this.angle);
        } else {
            System.out.println("This.angle: " + this.angle);
            String check = GameManager.getResultCollecsion();
            if (check.equals("Right") || check.equals("Left")) {
                setDx(-getDx());
            } else if (check.equals("Up") || check.equals("Paddle")) {
                setDy(-getDy());
            }
        }
        setX(getX() + getDx());
        setY(getY() - getDy());
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(Basis.BALL_TEXTURE, getX(), getY(), this.width, this.height);
    }

    public void update() {
        boolean gun = GameManager.getGun();
        if (! gun) {
            glide();  // If "W" hasn’t been pressed yet, the ball moves along the paddle.
        } else {
            move();  // If "W" has been pressed, the ball starts bouncing.
        }
    }
}
