package uet.project.arkanoid.objects;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.base.Point;
import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.base.Shape;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.Basis;

public class Paddle extends MovableObject {
    private double speed;
    private PowerUp currentPowerUp = null;
    private double paddleExpansion = 50;
    private double paddleShrink = 50;

    private Rectangle hitbox;

    public Paddle(int x, int y, double width, double height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
        // Initialize hitbox, assuming 0 rotation for paddle
        this.hitbox = new Rectangle(x + width / 2.0, y + height / 2.0, width, height, 0);
    }

    @Override
    public Shape getHitbox() {
        return this.hitbox;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
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

    public void render(GraphicsContext gc) {
        gc.drawImage(Basis.PADDLE_TEXTURE, getX(), getY(), this.width, this.height);
    }

    public void update() {
        move();
        if (getX() <= Basis.STAGE_X) {
            setX(Basis.STAGE_X);
        } else if (getX() + this.width >= Basis.STAGE_X + Basis.STAGE_WIDTH) {
            setX(Basis.STAGE_X + Basis.STAGE_WIDTH - (int)this.width);
        }

        this.hitbox.setCenter(new Point(getX() + this.width / 2.0, getY() + this.height / 2.0));
    }

    public void extendPaddle() {
        if (getX() < Basis.STAGE_X + paddleExpansion) {

        } else if (getX() > Basis.OBJECTIVE_BOARD_X - paddleExpansion) {
            setX(getX() - 2 * paddleExpansion);
        } else {
            setX(getX() - paddleExpansion);
        }
        setWidth(getWidth() + 2 * paddleExpansion);
    }

    public void shrinkPaddle() {
        setX(getX() + paddleShrink);
        setWidth(getWidth() - 2 * paddleShrink);
    }

    public void restorePaddle() {
        setWidth(210);
    }

    public void autoMovePaddle(GameSetup stage) {
        Ball ball = null;
        if (stage.getBalls().size() != 0) {
            ball = stage.getBalls().get(0);
        }
        int center = ball.getX() + (int)ball.getWidth() / 2;
        int condition1 = Basis.STAGE_X + ((int)getWidth()) / 2;
        int condition2 = Basis.STAGE_WIDTH - Basis.STAGE_X - ((int)getWidth()) / 2;
        if (center < condition1) {
            setX(Basis.STAGE_X);
        } else if (center > condition2) {
            setX(Basis.STAGE_WIDTH - Basis.STAGE_X - (int)getWidth() / 2);
        } else {
            setX(center - (int)getWidth() / 2);
        }
    }
}
