package uet.project.arkanoid.utils;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;

import uet.project.arkanoid.game.*;
import uet.project.arkanoid.objects.Chest;
import uet.project.arkanoid.objects.brickVariants.IndestructibleBrick;
import uet.project.arkanoid.objects.brickVariants.NormalBrick;

import uet.project.arkanoid.base.Rectangle;
import uet.project.arkanoid.objects.Ball;
import uet.project.arkanoid.objects.Paddle;
import uet.project.arkanoid.objects.Brick;
import uet.project.arkanoid.objects.Brick.BrickType;
import uet.project.arkanoid.game.Level;
import uet.project.arkanoid.game.Saves;
import uet.project.arkanoid.game.GameSetup;


import java.io.*;
import java.util.List;

public class MapLoader {
    public static void loadBricksFromTiled(GameSetup stage, String filePath) {
        try {
            // Read the TMX file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));
            doc.getDocumentElement().normalize();

            // Find all object groups
            NodeList objectGroups = doc.getElementsByTagName("objectgroup");

            for (int i = 0; i < objectGroups.getLength(); i++) {
                Element group = (Element) objectGroups.item(i);

                // Only process the Bricks layer
                if (!"Bricks".equals(group.getAttribute("name"))) {
                    continue;
                }

                NodeList objects = group.getElementsByTagName("object");

                for (int j = 0; j < objects.getLength(); j++) {
                    Element obj = (Element) objects.item(j);

                    int gid = Integer.parseInt(obj.getAttribute("gid"));
                    int x = (int) Double.parseDouble(obj.getAttribute("x"));
                    int y = (int) Double.parseDouble(obj.getAttribute("y"));
                    int width = (int) Double.parseDouble(obj.getAttribute("width"));
                    int height = (int) Double.parseDouble(obj.getAttribute("height"));
                    y = y - height;

                    switch (gid) {
                        case 1 -> stage.getBricks().add(new NormalBrick(x, y, width, height, 0, 1,stage));
                        case 3 -> stage.getBricks().add(new NormalBrick(x, y, width, height, 0, 2, stage));
                        case 4 -> stage.getBricks().add(new NormalBrick(x, y, width, height, 0, 3, stage));
                        case 5 -> stage.getBricks().add(new IndestructibleBrick(x, y, width, height, 0, stage));
                        default -> System.out.println("Unknown gid: " + gid);
                    }
                }
            }

            System.out.println("✅ Loaded bricks from " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getFileName(Saves slot) {
        switch (slot) {
            case SLOT_1:
                return "Saves/SLOT_1.txt";
            case SLOT_2:
                return "Saves/SLOT_2.txt";
            case SLOT_3:
                return "Saves/SLOT_3.txt";
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
            for (Ball ball : stage.getBalls()) { writer.println("Ball," + ball.getCenterX() + ","
                    + ball.getCenterY() + "," + ball.getRadius() + "," + ball.getSpeed() + ","
                    + ball.getDx() + "," + ball.getDy() + "," + ball.getLaunchState() + ","
                    + ball.isInvincible() + "," + ball.isMainBall());
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
        stage.clearLevel();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Paddle mainPaddle = null;
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
                        double paddleX = Double.parseDouble(array[1]);
                        double paddleY = Double.parseDouble(array[2]);
                        double paddleWidth = Double.parseDouble(array[3]);
                        double paddleHeight = Double.parseDouble(array[4]);
                        double paddleSpeed = Double.parseDouble(array[5]);

                        // FIX: Create the paddle if the list is empty, then set properties
                        if (stage.getPaddles().isEmpty()) {
                            // Assume a default speed and size for creation (required by constructor)
                            mainPaddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight, paddleSpeed);
                            stage.getPaddles().add(mainPaddle);
                        } else {
                            mainPaddle = stage.getPaddle(); // Will now safely get the newly created paddle
                        }

                        mainPaddle.setX((int) paddleX);
                        mainPaddle.setY((int) paddleY);
                        mainPaddle.setWidth(paddleWidth);
                        mainPaddle.setHeight(paddleHeight);
                        mainPaddle.setSpeed(paddleSpeed);

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
                        boolean mainBall = Boolean.parseBoolean(array[9]);

                        Ball newBall = new Ball(centerX, centerY, radius, speed, stage);

                        if (!mainBall) {
                            newBall.setBallImage(Basis.MULTI_BALL_TEXTURE);
                        }

                        newBall.setDx(dx); newBall.setDy(dy);
                        newBall.setHasLaunch(hasLaunch);
                        newBall.setInvincible(invincible);
                        newBall.setMainBall(mainBall);

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
                    case "Chest": // ADDED: Load Chests
                        double chestX = Double.parseDouble(array[1]);
                        double chestY = Double.parseDouble(array[2]);
                        double chestWidth = Double.parseDouble(array[3]);
                        double chestHeight = Double.parseDouble(array[4]);
                        boolean chestOpened = Boolean.parseBoolean(array[5]);

                        Chest chest = new Chest(chestX, chestY, chestWidth, chestHeight, stage);
                        chest.setOpened(chestOpened);

                        stage.getChests().add(chest);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Không tìm thấy file: " + fileName);
        } catch (IOException e) {
            System.err.println("Không thể đọc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Lỗi định dạng số trong file save: " + e.getMessage());
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
