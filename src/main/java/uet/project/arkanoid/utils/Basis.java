package uet.project.arkanoid.utils;

import javafx.scene.image.Image;

import java.util.Objects;

public class Basis {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    //Testing stage
    public static final int STAGE_TEST_WIDTH = 1030;
    public static final int STAGE_TEST_HEIGHT = 680;
    public static final int STAGE_TEST_X = 32;
    public static final int STAGE_TEST_Y = 32;

    // Fixed width, height
    public static final int ARROW_WIDTH = 45;
    public static final int ARROW_HEIGHT = 90;
    public static final int BALL_DIAMETER = 40;

    // Base stats
    public static final int BALL_SPEED = 8;
    public static final int PADDLE_SPEED = 6;

    // Textures
    public static final Image PADDLE_TEXTURE;
    public static final Image BALL_TEXTURE;
    public static final Image ARROW_TEXTURE;
    public static final Image BRICK_NORMAL_TEXTURE_1;
    public static final Image[] BRICK_NORMAL_TEXTURE_2 = new Image[2];
    public static final Image[] BRICK_NORMAL_TEXTURE_3 = new Image[3];
    public static final Image BRICK_INDESTRUCTIBLE_TEXTURE;
    public static final Image GAME_BACKGROUND;

    static {
        // Paddle + Ball
        PADDLE_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Paddle_1.png")).toExternalForm());
        BALL_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Ball_1.png")).toExternalForm());
        ARROW_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Arrow.png")).toExternalForm());

        // Bricks
        BRICK_NORMAL_TEXTURE_1 = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_1.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_2[1] = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_2_2.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_3[2] = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_3_3.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_2[0] = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_2_1.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_3[1] = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_3_2.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_3[0] = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_3_1.png")).toExternalForm());
        BRICK_INDESTRUCTIBLE_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_Indestructible.png")).toExternalForm());

        // UI elements
        GAME_BACKGROUND = new Image(Objects.requireNonNull(Basis.class.getResource("/UI_Elements/InGame/Background_Arkanoid.jpg")).toExternalForm());
    }
}
