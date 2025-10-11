package uet.project.arkanoid.objects.BrickVariants;

import uet.project.arkanoid.Basis;
import uet.project.arkanoid.objects.Brick;

public class NormalBrick extends Brick {

    public NormalBrick(int x, int y, int width, int height, int hitPoints) {
        super(x, y, width, height, hitPoints, BrickType.NORMAL);
        if (hitPoints == 1) {
            this.brickImage = Basis.BRICK_NORMAL_TEXTURE_1;
        }
        else if (hitPoints == 2) {
            this.brickImage = Basis.BRICK_NORMAL_TEXTURE_2;
        }
        else {
            this.brickImage = Basis.BRICK_NORMAL_TEXTURE_3;
        }
    }

}
