package uet.project.arkanoid;

import javafx.scene.image.Image;

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
    public static final Image BRICK_TEXTURE = null;
    public static final Image BALL_TEXTURE;

    static {
        PADDLE_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/Objects/Paddle_1.png")).toExternalForm());
        BALL_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/Objects/Ball_3.png")).toExternalForm());
    }


}
