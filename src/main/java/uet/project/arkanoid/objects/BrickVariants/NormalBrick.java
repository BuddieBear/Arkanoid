package uet.project.arkanoid.objects.BrickVariants;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.utils.Basis;
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
        int index = getHitPoints()-1;
        switch(type){
            case TWO:
                this.brickImage = Basis.BRICK_NORMAL_TEXTURE_2[index];
                break;
            case THREE:
                if (getHitPoints() < getMaxHp() && index > 1) {
                    index = 1;
                }
                this.brickImage = Basis.BRICK_NORMAL_TEXTURE_3[index];
                break;
        }
        super.render(gc);
    }
}