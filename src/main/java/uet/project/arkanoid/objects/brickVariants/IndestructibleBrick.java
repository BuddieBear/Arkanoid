package uet.project.arkanoid.objects.brickVariants;

import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.Brick;


public class IndestructibleBrick extends Brick {

    public IndestructibleBrick(int x, int y, double width, double height, GameSetup stage) {
        super(x, y, width, height, 10, BrickType.INDESTRUCTIBLE, stage);
        this.brickImage = Basis.BRICK_INDESTRUCTIBLE_TEXTURE;
    }

    @Override
    public void takeHit() {
    }
}
