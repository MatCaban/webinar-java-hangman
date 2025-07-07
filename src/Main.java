import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
// TODO: add case sensitiveness
// TODO: don't count same incorrect guess more than one
// TODO: draw the actual hangman
// TODO: restart game at the end
// TODO: add colors somehow (very optional - you don't have to do this)
// TODO: add information about how many guesses left (Enter a letter (...))
// TODO: show incorrect guesses (letters which user already tried)

public class Main {
    public static void main(String[] args) {
        final String[] words = {"java", "hangman", "skillmea", "academy", "computer"};
        final Random random = new Random();
        final String wordToGuess = selectRandomWord(random, words);

        String hiddenWord = generateHiddenWord(wordToGuess);

        final int MAX_INCORRECT_GUESSES = 6;
        int incorrectGuessesCounter = 0;
        List<Character> alreadyGuessed = new ArrayList<>();

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
            } else if (alreadyGuessed.contains(guess)) {
                System.out.println("You already try this letter");
            } else {
                alreadyGuessed.add(guess);
                incorrectGuessesCounter++;
                System.out.println("Incorrect guess, you have (" + (MAX_INCORRECT_GUESSES - incorrectGuessesCounter) + ") guesses left");
            }
        }
        if (!hiddenWord.contains("_")) {
            System.out.println("");
            System.out.println("Congratulations, you guessed it: " + wordToGuess);
        } else {
            System.out.println("");
            System.out.println("Sorry, you have run out of guesses. It was " + wordToGuess);
        }



    }

    public static String revealLetters(String word, String hiddenWord, char letter) {
        final char[] hiddenWordChars = hiddenWord.toCharArray();

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter){
                hiddenWordChars[i] = letter;
            }
        }
        return String.valueOf(hiddenWordChars);
    }

    public static char scanLetter(Scanner scanner) {
        while (true) {
            try {
                final String line = scanner.nextLine().toLowerCase();

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