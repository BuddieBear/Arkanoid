import java.util.Scanner;

public class Hangman {
    static char[] playerGuess;
    static String answer;

    public Hangman(String ans) {
        answer = ans;
        playerGuess = new char[ans.length()];
        for (int i = 0; i < ans.length(); i++) {
            playerGuess[i] = '_';
        }
    }

    public static void playHangman(Scanner scan) { //Lives: 6 ->TODO
        boolean win = false;
        int lives = 6;

        while (true) {
            if (answer.equals(new String(playerGuess))) {
                win = true;
                break;
            }

            if (lives == 0) {
                break;
            }
            //Draw -> TODO
            //drawHangman();

            // Show repeated letter  -> TODO

            System.out.println("Current word: " + new String(playerGuess));

            // Get new input
            System.out.print("Guess the next letter: ");
            char guess = scan.next().charAt(0);

            // Check Repeated letter  -> TODO
            //repeated();

            //Check for correct answer
            if (checkGuess(guess)) {
                System.out.println("Correct letter!");
            } else {
                lives--;
                System.out.println("Wrong letter!");
            }
            System.out.println();
        }

        if (win) {
            System.out.println("You Win!");
        }
    }


    //Check and place the letter into the answer
    public static boolean checkGuess(char letter) {
        int len = answer.length();
        boolean correct = false;
        for (int i = 0; i < len; i++) {
            if (letter == answer.charAt(i)) {
                playerGuess[i] = letter;
                correct = true;
            }
        }
        return correct;
    }

    //Draw Hangman
    public static void drawHangman() { // placeholder thay doi tuy theo y muon

    }

    //Repeated Letter
    public static boolean repeated() { // placeholder thay doi tuy theo y muon
        return true;
    }

}
