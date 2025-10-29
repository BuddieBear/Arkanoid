package uet.project.arkanoid.objects.powerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.objects.Ball;

public class MultiBallPowerUp extends PowerUp {
    private Ball ball;
    private Ball b1;
    private Ball b2;

    public MultiBallPowerUp(GameObject object, double width, double height, GameSetup stage) {
        super(object, width, height, stage);
        type = PowerUpType.MULTI_BALL;
        ball = stage.getBalls().get(0);
    }

    public void applyEffect() {
        int speed = (int) ball.getSpeed();
        // tạo 2 balls mới chia làm 2 hướng lệch 45 độ với ball gốc
        double currentAngle = Math.toDegrees(Math.atan2(-ball.getDy(), ball.getDx()));
        double rad1 = Math.toRadians(currentAngle + 45);
        double rad2 = Math.toRadians(currentAngle - 45);

        b1 = new Ball(ball.getCenterX(), ball.getCenterY(), ball.getRadius(), ball.getSpeed(), stage);
        b1.setDx((int)(speed * Math.cos(rad1)));
        b1.setDy((int)(-speed * Math.sin(rad1)));
        b1.setHasLaunch(true);
        b1.setBallImage(Basis.MULTI_BALL_TEXTURE);
        stage.getBalls().add(b1);

        b2 = new Ball(ball.getCenterX(), ball.getCenterY(), ball.getRadius(), ball.getSpeed(), stage);
        b2.setDx((int)(speed * Math.cos(rad2)));
        b2.setDy((int)(-speed * Math.sin(rad2)));
        b2.setHasLaunch(true);
        b2.setBallImage(Basis.MULTI_BALL_TEXTURE);
        stage.getBalls().add(b2);

        b1.setX((int) Math.max(Basis.STAGE_X, Math.min(b1.getX(), Basis.STAGE_X + Basis.STAGE_WIDTH - b1.getWidth())));
        b1.setY((int) Math.max(Basis.STAGE_Y, Math.min(b1.getY(), Basis.STAGE_Y + Basis.STAGE_HEIGHT - b1.getHeight())));

        b2.setX((int) Math.max(Basis.STAGE_X, Math.min(b2.getX(), Basis.STAGE_X + Basis.STAGE_WIDTH - b2.getWidth())));
        b2.setY((int) Math.max(Basis.STAGE_Y, Math.min(b2.getY(), Basis.STAGE_Y + Basis.STAGE_HEIGHT - b2.getHeight())));

    }

    @Override
    public void removeEffect() {
        stage.getBalls().remove(b1);
        stage.getBalls().remove(b2);
    }
}
