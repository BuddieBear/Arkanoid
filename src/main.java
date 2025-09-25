import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        String answer = "whatever";
        Scanner scan = new Scanner(System.in);
        Hangman game = new Hangman(answer);
        Hangman.playHangman(scan);
        scan.close();
    }
}
