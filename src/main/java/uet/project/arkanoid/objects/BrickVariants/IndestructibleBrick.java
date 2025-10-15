package uet.project.arkanoid.objects.BrickVariants;

import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.Brick;


public class IndestructibleBrick extends Brick {

    public IndestructibleBrick(int x, int y, int width, int height) {
        super(x, y, width, height, 10, BrickType.INDESTRUCTIBLE);
        this.brickImage = Basis.BRICK_INDESTRUCTIBLE_TEXTURE;
    }

    @Override
    public void takeHit() {
    }
}
