package uet.project.arkanoid.utils;

import javafx.scene.image.Image;
import uet.project.arkanoid.game.GameSetup;

import java.util.Objects;

public class Basis {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    //stage
    public static final int STAGE_WIDTH = 1030;
    public static final int STAGE_HEIGHT = 680;
    public static final int STAGE_X = 32;
    public static final int STAGE_Y = 32;

    public static final String STAGE_1 = "src/main/resources/Stages/Stage_1.tmx";
    public static final String STAGE_2 = "src/main/resources/Stages/Stage_2.tmx";
    public static final String STAGE_3 = "src/main/resources/Stages/Stage_3.tmx";


    // Fixed width, height
    public static final int ARROW_WIDTH = 45;
    public static final int ARROW_HEIGHT = 90;
    public static final int BALL_DIAMETER = 40;
    public static final int OBJECTIVE_BOARD_WIDTH = 180;
    public static final int OBJECTIVE_BOARD_HEIGHT = 360;

    // Fixed location
    public static final int OBJECTIVE_BOARD_X = 1080;
    public static final int OBJECTIVE_BOARD_Y = 230;

    // Base stats
    public static final int BALL_SPEED = 5;
    public static final int PADDLE_SPEED = 8;

    // Menu
    public static final int PLAY_X = SCREEN_WIDTH / 2 - 100 - 35;
    public static final int PLAY_Y = SCREEN_HEIGHT - 120;
    public static final int PLAY_W = 200;
    public static final int PLAY_H = 60;

    public static final int INSTRUCTION_X = SCREEN_WIDTH / 2 - 100 - 35;
    public static final int INSTRUCTION_Y = PLAY_Y - 120;
    public static final int INSTRUCTION_W = 200;
    public static final int INSTRUCTION_H = 60;

    public static final int SETTING_X = SCREEN_WIDTH / 2 - 100 - 35;
    public static final int SETTING_Y = INSTRUCTION_Y - 120;
    public static final int SETTING_W = 200;
    public static final int SETTING_H = 60;

    public static final int BACK_X = 5;
    public static final int BACK_Y = 5;
    public static final int BACK_W = 200;
    public static final int BACK_H = 60;

    public static final int LEVEL_IMAGE_W = 340;
    public static final int LEVEL_IMAGE_H = 400;
    public static final int LEVEL_IMAGE_Y = 250;

    public static final int LEVEL_1_IMAGE_X = (Basis.SCREEN_WIDTH - 3 * LEVEL_IMAGE_W) / 4;
    public static final int LEVEL_2_IMAGE_X = 2 * LEVEL_1_IMAGE_X + LEVEL_IMAGE_W;
    public static final int LEVEL_3_IMAGE_X = 3 * LEVEL_1_IMAGE_X + 2 * LEVEL_IMAGE_W;

    public static final int TEXT_LEVEL_Y = 140;
    public static final int TEXT_LEVEL_W = 200;
    public static final int TEXT_LEVEL_H = 60;

    public static final int TEXT_LEVEL_1_X = LEVEL_1_IMAGE_X + 70;
    public static final int TEXT_LEVEL_2_X = LEVEL_2_IMAGE_X + 70;
    public static final int TEXT_LEVEL_3_X = LEVEL_3_IMAGE_X + 70;

    //Pause Menu
    public static final int PAUSE_MENU_WIDTH = 400;
    public static final int PAUSE_MENU_HEIGHT = 500;
    public static final int PAUSE_MENU_X = (SCREEN_WIDTH - PAUSE_MENU_WIDTH) / 2;
    public static final int PAUSE_MENU_Y = (SCREEN_HEIGHT - PAUSE_MENU_HEIGHT) / 2;

    public static final int PAUSE_BTN_WIDTH = 350;
    public static final int PAUSE_BTN_HEIGHT = 60;
    public static final int PAUSE_BTN_X_OFFSET = (PAUSE_MENU_WIDTH - PAUSE_BTN_WIDTH) / 2;

    public static final int CONTINUE_BTN_X = PAUSE_MENU_X + PAUSE_BTN_X_OFFSET;
    public static final int CONTINUE_BTN_Y = PAUSE_MENU_Y + 150;

    public static final int MENU_BTN_X = PAUSE_MENU_X + PAUSE_BTN_X_OFFSET;
    public static final int MENU_BTN_Y = PAUSE_MENU_Y + 225;

    public static final int OPTIONS_BTN_X = PAUSE_MENU_X + PAUSE_BTN_X_OFFSET;
    public static final int OPTIONS_BTN_Y = PAUSE_MENU_Y + 300;

    public static final int SAVEGAME_BTN_X = PAUSE_MENU_X + PAUSE_BTN_X_OFFSET;
    public static final int SAVEGAME_BTN_Y = PAUSE_MENU_Y + 375;

    // Load Game
    public static final int LOAD_GAME_BTN_X = PLAY_X;
    public static final int LOAD_GAME_BTN_Y = PLAY_Y - PLAY_H - 15;
    public static final int LOAD_GAME_BTN_W = PLAY_W;
    public static final int LOAD_GAME_BTN_H = PLAY_H;

    public static final int LEVEL_BTN_WIDTH = 350;
    public static final int LEVEL_BTN_HEIGHT = 60;
    public static final int LEVEL_BTN_X_CENTER = (SCREEN_WIDTH - LEVEL_BTN_WIDTH) / 2;

    public static final int LEVEL_1_BTN_X = LEVEL_BTN_X_CENTER;
    public static final int LEVEL_1_BTN_Y = 250;

    public static final int LEVEL_2_BTN_X = LEVEL_BTN_X_CENTER;
    public static final int LEVEL_2_BTN_Y = 330;

    public static final int LEVEL_3_BTN_X = LEVEL_BTN_X_CENTER;
    public static final int LEVEL_3_BTN_Y = 410;

    // Textures
    public static final Image PADDLE_TEXTURE;
    public static final Image BALL_TEXTURE;
    public static final Image MULTI_BALL_TEXTURE;
    public static final Image ARROW_TEXTURE;
    public static final Image POWERUP_TEXTURE;
    public static final Image BRICK_NORMAL_TEXTURE_1;
    public static final Image[] BRICK_NORMAL_TEXTURE_2 = new Image[2];
    public static final Image[] BRICK_NORMAL_TEXTURE_3 = new Image[3];
    public static final Image BRICK_INDESTRUCTIBLE_TEXTURE;
    public static final Image GAME_BACKGROUND;
    public static final Image OBJECTIVE_BOARD_TEXTURE;
    public static final Image MENU;
    public static final Image PLAY_BUTTON;
    public static final Image OPTION_BUTTON;
    public static final Image SETTING_BUTTON;
    public static final Image LEVEL_1;
    public static final Image LEVEL_2;
    public static final Image LEVEL_3;
    public static final Image TEXT_LEVEL_1;
    public static final Image TEXT_LEVEL_2;
    public static final Image TEXT_LEVEL_3;
    public static final Image BACK_BUTTON;
    public static final Image PAUSE_CONTINUE_BUTTON;
    public static final Image PAUSE_MENU_BUTTON;
    public static final Image PAUSE_OPTIONS_BUTTON;
    public static final Image PAUSE_SAVEGAME_BUTTON;
    public static final Image PAUSE_MENU_PANEL;
    public static final Image LOAD_GAME_BUTTON;
    public static final Image LEVEL_1_BUTTON;
    public static final Image LEVEL_2_BUTTON;
    public static final Image LEVEL_3_BUTTON;
    public static final Image GAME_OVER_MENU_BUTTON;
    public static final Image GAME_OVER_EXIT_BUTTON;
    public static final Image GAME_OVER_REPLAY_BUTTON;
    public static final Image GAME_WIN_TEXT;
    public static final Image GAME_LOSE_TEXT;
    public static final Image GAME_WIN_TROPHY;
    public static final Image GAME_LOSE_HEART;

    static {
        // Paddle + Ball
        PADDLE_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Paddle_1.png")).toExternalForm());
        BALL_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Ball_1.png")).toExternalForm());
        MULTI_BALL_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Ball_3.png")).toExternalForm());

        ARROW_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Arrow.png")).toExternalForm());

        // Bricks
        BRICK_NORMAL_TEXTURE_1 = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_1.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_2[1] = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_2_2.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_3[2] = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_3_3.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_2[0] = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_2_1.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_3[1] = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_3_2.png")).toExternalForm());
        BRICK_NORMAL_TEXTURE_3[0] = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_3_1.png")).toExternalForm());
        BRICK_INDESTRUCTIBLE_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/Brick_Indestructible.png")).toExternalForm());

        // Power up
        POWERUP_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/objects/powerUpImage.png")).toExternalForm());

        // UI elements
        GAME_BACKGROUND = new Image(Objects.requireNonNull(Basis.class.getResource("/UI_Elements/InGame/Background_Arkanoid.jpg")).toExternalForm());
        OBJECTIVE_BOARD_TEXTURE = new Image(Objects.requireNonNull(Basis.class.getResource("/UI_Elements/InGame/Objective_Board.jpg")).toExternalForm());

        // Menu
        MENU = new Image(Objects.requireNonNull(Basis.class.getResource("/Menu/menu.jpeg")).toExternalForm());
        PLAY_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/Menu/play.png")).toExternalForm());
        OPTION_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/Menu/instruction.png")).toExternalForm());
        SETTING_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/Menu/setting.png")).toExternalForm());
        LEVEL_1 = new Image(Objects.requireNonNull(Basis.class.getResource("/Menu/Level1.png")).toExternalForm());
        LEVEL_2 = new Image(Objects.requireNonNull(Basis.class.getResource("/Menu/Level2.png")).toExternalForm());
        LEVEL_3 = new Image(Objects.requireNonNull(Basis.class.getResource("/Menu/Level3.png")).toExternalForm());
        TEXT_LEVEL_1 = new Image(Objects.requireNonNull(Basis.class.getResource("/Menu/textLevel_1.png")).toExternalForm());
        TEXT_LEVEL_2 = new Image(Objects.requireNonNull(Basis.class.getResource("/Menu/textLevel_2.png")).toExternalForm());
        TEXT_LEVEL_3 = new Image(Objects.requireNonNull(Basis.class.getResource("/Menu/textLevel_3.png")).toExternalForm());
        BACK_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/Menu/back.png")).toExternalForm());
        LOAD_GAME_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/Menu/LoadGame.png")).toExternalForm());

        // PausedMenu
        PAUSE_MENU_PANEL = new Image(Objects.requireNonNull(Basis.class.getResource("/PausedMenu/Panel.png")).toExternalForm());
        PAUSE_CONTINUE_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/PausedMenu/Continue.png")).toExternalForm());
        PAUSE_MENU_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/PausedMenu/Menu.png")).toExternalForm());
        PAUSE_OPTIONS_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/PausedMenu/Options.png")).toExternalForm());
        PAUSE_SAVEGAME_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/PausedMenu/SaveGame.png")).toExternalForm());

        //LoadGame
        LEVEL_1_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/LoadScreen/Level1.png")).toExternalForm());
        LEVEL_2_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/LoadScreen/Level2.png")).toExternalForm());
        LEVEL_3_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/LoadScreen/Level3.png")).toExternalForm());

        // GameOver
        GAME_OVER_MENU_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/UI_Elements/GameOver/Menu.png")).toExternalForm());
        GAME_OVER_EXIT_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/UI_Elements/GameOver/Exit.png")).toExternalForm());
        GAME_OVER_REPLAY_BUTTON = new Image(Objects.requireNonNull(Basis.class.getResource("/UI_Elements/GameOver/Menu.png")).toExternalForm());;
        GAME_WIN_TEXT = new Image(Objects.requireNonNull(Basis.class.getResource("/UI_Elements/GameOver/Won.png")).toExternalForm());;
        GAME_LOSE_TEXT = new Image(Objects.requireNonNull(Basis.class.getResource("/UI_Elements/GameOver/Lost.png")).toExternalForm());;
        GAME_WIN_TROPHY = new Image(Objects.requireNonNull(Basis.class.getResource("/UI_Elements/GameOver/Trophy.png")).toExternalForm());;
        GAME_LOSE_HEART = new Image(Objects.requireNonNull(Basis.class.getResource("/UI_Elements/GameOver/Shattered_heart.png")).toExternalForm());;
    }
}
