import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String[] words = {"java", "hangman", "skillmea", "academy", "computer"};
        final Random random = new Random();
        final String wordToGuess = selectRandomWord(random, words);

        String hiddenWord = generateHiddenWord(wordToGuess);

        final int MAX_INCORRECT_GUESSES = 6;
        int incorrectGuessesCounter = 0;

        final Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Hangman");
        System.out.println("Guess the word: " + hiddenWord);


        while (incorrectGuessesCounter < MAX_INCORRECT_GUESSES && hiddenWord.contains("_")) {
            System.out.println("Enter a letter: ");
            final char guess = scanLetter(scanner);


            if (hiddenWord.contains(String.valueOf(guess))){
                System.out.println("This letter is already revealed");
            } else if (wordToGuess.contains(String.valueOf(guess))){
                hiddenWord = revealLetters(wordToGuess, hiddenWord, guess);
                System.out.println("Correct guess! Updated word: " + hiddenWord);
            } else {
                incorrectGuessesCounter++;
                System.out.println("Incorrect guess, you have (" + (MAX_INCORRECT_GUESSES - incorrectGuessesCounter) + ") guesses left");
            }
        }

        if (!hiddenWord.contains("_")) {

        }


    }

    public static String revealLetters(String word, String hiddenWord, char letter) {
        final char[] hiddenWordChars = hiddenWord.toCharArray();

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter){
                hiddenWordChars[i] = letter;
                System.out.println(String.valueOf(hiddenWordChars));
            }
        }
        return String.valueOf(hiddenWordChars);
    }

    public static char scanLetter(Scanner scanner) {
        while (true) {
            try {
                final String line = scanner.nextLine();

                if (line.length() != 1) {
                    throw new Exception("Line length is not 1. Please enter a single letter");
                }

                if (!Character.isLetter(line.charAt(0))) {
                    throw new Exception("Character is not a letter. Please enter a single letter");
                }

                return line.charAt(0);
            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
    }

    public static String selectRandomWord(Random random, String[] words) {
        return words[random.nextInt(words.length)];
    }

    public static String generateHiddenWord(String word) {
        return "_".repeat(word.length());
    }
}