package uet.project.arkanoid.utils;

import java.io.*;

public class HighScore {
    private static final String FILE_PATH = "highscore.txt";

    public int getHighScore(int level) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] array = line.split(",");
                int score = Integer.parseInt(array[1]);
                switch (array[0]) {
                    case "level1":
                        if (level == 1) return score;
                        break;
                    case "level2":
                        if (level == 2) return score;
                        break;
                    case "level3":
                        if (level == 3) return score;
                        break;
                }
            }
        } catch (IOException e) {
                System.err.println("Không thể đọc file: " + e.getMessage());
        }
        return 0;
    }

    public void saveNewHighScore(int level, int newScore) {
        int score1 = getHighScore(1);
        int score2 = getHighScore(2);
        int score3 = getHighScore(3);
        boolean needToSave = false;

        switch (level) {
            case 1:
                if (newScore > score1) {
                    score1 = newScore;
                    needToSave = true;
                }
                break;
            case 2:
                if (newScore > score2) {
                    score2 = newScore;
                    needToSave = true;
                }
                break;
            case 3:
                if (newScore > score3) {
                    score3 = newScore;
                    needToSave = true;
                }
                break;
        }
        if (!needToSave) {
            return;
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.println("level1," + score1);
            writer.println("level2," + score2);
            writer.println("level3," + score3);
        } catch (IOException e) {
            System.err.println("Không thể ghi file highscore: " + e.getMessage());
        }
    }
}