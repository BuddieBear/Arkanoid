package uet.project.arkanoid.objects.brickVariants;

import javafx.scene.canvas.GraphicsContext;
import uet.project.arkanoid.game.GameSetup;
import uet.project.arkanoid.utils.Basis;
import uet.project.arkanoid.objects.Brick;

public class NormalBrick extends Brick {
    private TextureType type;
    public enum TextureType {
        ONE,
        TWO,
        THREE
    }

    public NormalBrick(int x, int y, double width, double height, int hitPoints, GameSetup stage) {
        super(x, y, width, height, hitPoints, BrickType.NORMAL, stage);
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
        if (index < 0) {
            index = 0;
        }
        switch(type){
            case TWO:
                if(index < 2) {
                    this.brickImage = Basis.BRICK_NORMAL_TEXTURE_2[index];
                } else {
                    this.brickImage = Basis.BRICK_NORMAL_TEXTURE_2[1];
                }
                break;
            case THREE:
                if(index < 3) {
                    this.brickImage = Basis.BRICK_NORMAL_TEXTURE_3[index];
                } else {
                    this.brickImage = Basis.BRICK_NORMAL_TEXTURE_3[2];
                }
                break;
        }
        super.render(gc);
    }
}
