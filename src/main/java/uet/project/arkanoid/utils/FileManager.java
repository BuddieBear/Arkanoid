package uet.project.arkanoid.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileManager {
    private static final String FOLDER_PATH = "HighScore";
    private static final String SCORE_FILE = FOLDER_PATH + "/score_level_1.txt";

    // Ensure the folder and file exist before any operation
    private static void ensureFileExists() throws IOException {
        Path folderPath = Paths.get(FOLDER_PATH);
        Path filePath = Paths.get(SCORE_FILE);

        // Create folder if missing
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        // Create file if missing
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    public static void saveScore(int score) {
        try {
            ensureFileExists();
            try (FileWriter writer = new FileWriter(SCORE_FILE, true)) {
                writer.write(score + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error saving score: " + e.getMessage());
        }
    }

    public static List<String> loadScores() {
        try {
            ensureFileExists();
            return Files.readAllLines(Paths.get(SCORE_FILE));
        } catch (IOException e) {
            return List.of("No scores yet!");
        }
    }
}