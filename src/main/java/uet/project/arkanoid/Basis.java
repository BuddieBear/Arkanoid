package uet.project.arkanoid;

import javafx.scene.image.Image;
import uet.project.arkanoid.objects.Brick;

import java.util.Objects;

public class Basis {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    //Testing stage
    public static final int STAGE_TEST_WIDTH = 640;
    public static final int STAGE_TEST_HEIGHT = 720;
    public static final int STAGE_TEST_X = 320;
    public static final int STAGE_TEST_Y = 0;

    //Textures
    public static final Image PADDLE_TEXTURE;
    public static final Image BALL_TEXTURE;
    public static final Image BRICK_NORMAL_TEXTURE_1;
    public static final Image BRICK_NORMAL_TEXTURE_2;
    public static final Image BRICK_NORMAL_TEXTURE_3;
    public static final Image BRICK_INDESTRUCTIBLE_TEXTURE;

    static {
        PADDLE_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Paddle_1.png")).toExternalForm());
        BALL_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Ball_1.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_1 = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_1.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_2 = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_2.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_3 = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_3.png")).toExternalForm());
        BRICK_INDESTRUCTIBLE_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_Indestructible.png")).toExternalForm());
    }
}
