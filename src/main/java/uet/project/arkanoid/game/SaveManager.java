package uet.project.arkanoid.game;

import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.Brick.BrickType;
import uet.project.arkanoid.objects.brickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.brickVariants.NormalBrick;
import uet.project.arkanoid.game.Level;

import java.io.*;
import java.util.List;

public class SaveManager {

    private static String getFileName(Saves slot) {
        switch (slot) {
            case SLOT_1:
                return "SLOT_1.txt";
            case SLOT_2:
                return "SLOT_2.txt";
            case SLOT_3:
                return "SLOT_3.txt";
            default:
                throw new IllegalArgumentException("Unknown slot: " + slot);
        }
    }

    public static boolean isSaveFile(Saves slot) {
        String fileName = getFileName(slot);
        File saveFile = new File(fileName);
        return saveFile.exists() && saveFile.length() > 0;
    }

    public static void saveGame(Saves slot, GameSetup stage) {
        String fileName = getFileName(slot);
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("GameState," + stage.getScore() + "," + stage.getLives());
            Paddle paddle = stage.getPaddle();
            writer.println("Paddle," + paddle.getX() + ","
                    + paddle.getY() + "," + paddle.getWidth() + ","
                    + paddle.getHeight() + "," + paddle.getSpeed());
            for (Ball ball : stage.getBalls()) {
                writer.println("Ball," + ball.getCenterX() + ","
                        + ball.getCenterY() + "," + ball.getRadius() + ","
                        + ball.getSpeed() + ","+ ball.getDx() + ","
                        + ball.getDy() + "," + ball.getLaunchState() + ","
                        + ball.isInvincible());
            }
            for (Brick brick : stage.getBricks()) {
                if (brick.isDestroy() == false) {
                    writer.println("Brick," + brick.getX() + ","
                            + brick.getY() + "," + brick.getWidth() + ","
                            + brick.getHeight() + "," + ((Rectangle) brick.getHitbox()).getRotation() + ","
                            + brick.getHitPoints() + "," + brick.getType().name() + ","
                            + brick.getMaxHp());
                }
            }
        } catch (IOException e) {
            System.err.println("Không thể lưu game: " + e.getMessage());
        }
    }

    public static void loadGame(Saves slot, GameSetup stage) {
        String fileName = getFileName(slot);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            stage.getBalls().clear();
            stage.getBricks().clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] array = line.split(",");
                if (array.length == 0) {
                    continue;
                }
                String type = array[0];
                switch (type) {
                    case "GameState":
                        stage.setScore(Integer.parseInt(array[1]));
                        stage.setLives(Integer.parseInt(array[2]));
                        break;
                    case "Paddle":
                        Paddle paddle = stage.getPaddle();
                        paddle.setX((int) Double.parseDouble(array[1]));
                        paddle.setY((int) Double.parseDouble(array[2]));
                        paddle.setWidth(Double.parseDouble(array[3]));
                        paddle.setHeight(Double.parseDouble(array[4]));
                        paddle.setSpeed(Double.parseDouble(array[5]));
                        break;
                    case "Ball":
                        double centerX = Double.parseDouble(array[1]);
                        double centerY = Double.parseDouble(array[2]);
                        double radius = Double.parseDouble(array[3]);
                        double speed = Double.parseDouble(array[4]);
                        double dx = Double.parseDouble(array[5]);
                        double dy = Double.parseDouble(array[6]);
                        boolean hasLaunch = Boolean.parseBoolean(array[7]);
                        boolean invincible = Boolean.parseBoolean(array[8]);
                        Ball newBall = new Ball(centerX, centerY, radius, speed, stage);
                        newBall.setDx(dx);
                        newBall.setDy(dy);
                        newBall.setHasLaunch(hasLaunch);
                        newBall.setInvincible(invincible);
                        stage.getBalls().add(newBall);
                        break;
                    case "Brick":
                        int brickX = (int) Double.parseDouble(array[1]);
                        int brickY = (int) Double.parseDouble(array[2]);
                        double brickWidth = Double.parseDouble(array[3]);
                        double brickHeight = Double.parseDouble(array[4]);
                        double rotation = Double.parseDouble(array[5]);
                        int hitPoints = Integer.parseInt(array[6]);
                        BrickType brickType = BrickType.valueOf(array[7]);
                        int maxHp = Integer.parseInt(array[8]);
                        Brick newBrick;
                        if (brickType == BrickType.NORMAL) {
                            newBrick = new NormalBrick(brickX, brickY, brickWidth, brickHeight, rotation, maxHp, stage);
                        } else {
                            newBrick = new IndestructibleBrick(brickX, brickY, brickWidth, brickHeight, rotation, stage);
                        }
                        newBrick.setHitPoints(hitPoints);
                        stage.getBricks().add(newBrick);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Không tìm thấy file: " + fileName);
        } catch (IOException e) {
            System.err.println("Không thể đọc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Lỗi định dạng số trong file save: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("File bị lỗi" + e.getMessage());
        }
    }

    public static Saves levelSave(Level level) {
        switch (level) {
            case STAGE_1:
                return Saves.SLOT_1;
            case STAGE_2:
                return Saves.SLOT_2;
            case STAGE_3:
                return Saves.SLOT_3;
            default:
                throw new IllegalArgumentException("Màn chơi không xác định");
        }
    }
}