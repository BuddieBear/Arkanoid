package uet.project.arkanoid.objects.PowerUpVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.objects.BrickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.GameObject;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.PowerUp;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.Ball;

public class MultiBallPowerUp extends PowerUp {
    Ball ball = Basis.stage.getBalls().get(0);
    GameSetup stage = Basis.stage;
    Ball b1;
    Ball b2;

    public MultiBallPowerUp(GameObject object, double width, double height) {
        super(object, width, height);
        type = PowerUpType.MULTI_BALL;
    }

    public void applyEffect() {
        int speed = ball.getSpeed();
        // tạo 2 balls mới chia làm 2 hướng lệch 45 độ với ball gốc
        double currentAngle = Math.toDegrees(Math.atan2(-ball.getDy(), ball.getDx()));
        double rad1 = Math.toRadians(currentAngle + 45);
        double rad2 = Math.toRadians(currentAngle - 45);

        b1 = new Ball(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), speed, stage);
        b1.setDx((int)(speed * Math.cos(rad1)));
        b1.setDy((int)(-speed * Math.sin(rad1)));
        b1.setHasLaunch(true);
        b1.setBallImage(Basis.MULTI_BALL_TEXTURE);
        stage.getBalls().add(b1);

        b2 = new Ball(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), speed, stage);
        b2.setDx((int)(speed * Math.cos(rad2)));
        b2.setDy((int)(-speed * Math.sin(rad2)));
        b2.setHasLaunch(true);
        b2.setBallImage(Basis.MULTI_BALL_TEXTURE);
        stage.getBalls().add(b2);
    }

    @Override
    public void removeEffect() {
        super.removeEffect();
        Basis.stage.getBalls().remove(b1);
        Basis.stage.getBalls().remove(b2);
    }
}
