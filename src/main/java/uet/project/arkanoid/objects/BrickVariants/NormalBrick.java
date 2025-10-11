package uet.project.arkanoid.objects.BrickVariants;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.Basis;
import uet.project.arkanoid.objects.Brick;

public class NormalBrick extends Brick {
    private TextureType type;
    public enum TextureType {
        ONE,
        TWO,
        THREE
    }

    public NormalBrick(int x, int y, int width, int height, int hitPoints) {
        super(x, y, width, height, hitPoints, BrickType.NORMAL);
        if (hitPoints == 1) {
            //brick texture 1 chỉ có 1 ảnh k cần mảng
            this.brickImage = Basis.BRICK_NORMAL_TEXTURE_1;
            this.type = TextureType.ONE;
        }
        else if (hitPoints == 2) {
            this.brickImage = Basis.BRICK_NORMAL_TEXTURE_2[0];
            this.type = TextureType.TWO;
        }
        else {
            this.brickImage = Basis.BRICK_NORMAL_TEXTURE_3[0];
            this.type = TextureType.THREE;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        switch(type){
            // case ONE only have 1 image no need fix
            case TWO:
                this.brickImage = Basis.BRICK_NORMAL_TEXTURE_2[getHitPoints()-1];
                break;
            case THREE:
                this.brickImage = Basis.BRICK_NORMAL_TEXTURE_3[getHitPoints()-1];
                break;
        }
        super.render(gc);
    }

}
