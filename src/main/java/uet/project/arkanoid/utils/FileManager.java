package uet.project.arkanoid.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileManager {
    private static final String SCORE_FILE = "Saves/score_level_test.txt";

    public static void saveScore(int score) {
        try (FileWriter writer = new FileWriter(SCORE_FILE, true)) {
            writer.write(score + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> loadScores() {
        try {
            return Files.readAllLines(Paths.get(SCORE_FILE));
        } catch (IOException e) {
            return List.of("No scores yet!");
        }
    }
}
