
import java.util.*;


public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";


    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int MAX_INCORRECT_GUESSES = 6;
        final String[] words = {"java", "hangman", "skillmea", "academy", "computer"};
        final Random random = new Random();
        int incorrectGuessesCounter = 0;
        String[][] hangman = createHangman();
        List<Character> alreadyGuessedLetters = new ArrayList<>();
        String wordToGuess = selectRandomWord(random, words);
        String hiddenWord = generateHiddenWord(wordToGuess);

        System.out.println("Welcome to Hangman");
        System.out.println("Guess the word: " + hiddenWord);


        while (true) {

            if (incorrectGuessesCounter == MAX_INCORRECT_GUESSES || !hiddenWord.contains("_")) {
                System.out.println("");

                if (!hiddenWord.contains("_")) {
                    System.out.println("Congratulations, you guessed it: " + ANSI_GREEN + wordToGuess + ANSI_RESET);
                } else {
                    System.out.println("Sorry, you have run out of guesses. It was " + ANSI_RED + wordToGuess + ANSI_RESET);
                }


                if (!doUserWantToContinue(scanner)) {
                    break;
                } else {
                    System.out.println("=".repeat(30));

                    incorrectGuessesCounter = 0;
                    wordToGuess = selectRandomWord(random, words);
                    hiddenWord = generateHiddenWord(wordToGuess);
                    alreadyGuessedLetters.clear();
                    hangman = createHangman();
                    System.out.println("Welcome to new game");
                    System.out.println("Guess the word: " + hiddenWord);
                }
            }


            System.out.print("Enter a letter: ");
            final char guess = scanLetter(scanner);


            if (hiddenWord.contains(String.valueOf(guess))) {
                System.out.println(ANSI_YELLOW + "This letter is already revealed" + ANSI_RESET);
            } else if (wordToGuess.contains(String.valueOf(guess))) {
                hiddenWord = revealLetters(wordToGuess, hiddenWord, guess);
                System.out.println(ANSI_GREEN + "Correct guess!" + ANSI_RESET + " Updated word: " + hiddenWord);
            } else if (alreadyGuessedLetters.contains(guess)) {
                System.out.println(ANSI_YELLOW + "You already try this letters" + ANSI_RESET);
                for (char c : alreadyGuessedLetters) {
                    System.out.print(ANSI_BLUE + c + ANSI_RESET + " ");
                }
                System.out.println("");

            } else {
                alreadyGuessedLetters.add(guess);
                incorrectGuessesCounter++;
                System.out.println("Incorrect guess, you have (" + ANSI_RED + (MAX_INCORRECT_GUESSES - incorrectGuessesCounter) + ANSI_RESET + ") guesses left");
                System.out.println("=".repeat(30));
                updateHangman(hangman, incorrectGuessesCounter);
                printHangman(hangman, incorrectGuessesCounter);
                System.out.println("=".repeat(30));
            }
        }
    }

    public static String[][] createHangman() {
        String[][] hangman = new String[5][5];
        for (String[] row : hangman) {
            Arrays.fill(row, " ");
        }

        for (int i = 1; i < 4; i++) {
            hangman[0][i] = "_";
        }

        for (int i = 1; i < hangman.length; i++) {
            hangman[i][1] = "|";
        }

        for (int i = 0; i < hangman.length; i++) {
            if (i == 1) {
                continue;
            }
            hangman[4][i] = "_";
        }


        return hangman;


    }

    public static void printHangman(String[][] hangman, int incorrectGuesses) {
        for (String[] row : hangman) {
            for (String col : row) {
                System.out.print(
                        incorrectGuesses < 6 ? col : ANSI_RED + col + ANSI_RESET
                );
            }
            System.out.println("");
        }
    }

    public static void updateHangman(String[][] hangman, int incorrectGuesses) {
        switch (incorrectGuesses) {
            case 1 -> hangman[1][3] = "O";
            case 2 -> hangman[2][3] = "|";
            case 3 -> hangman[2][2] = "\\";
            case 4 -> hangman[2][4] = "/";
            case 5 -> hangman[3][2] = "/";
            case 6 -> hangman[3][4] = "\\";

        }
    }

    public static boolean doUserWantToContinue(Scanner scanner) {
        System.out.println("Do you want to play next game? y/n");
        char answer = scanAnswer(scanner);
        if (answer == 'n') {
            System.out.println("Good bye");
            return false;
        }
        return true;
    }

    public static char scanAnswer(Scanner scanner) {
        while (true) {
            try {
                final String line = scanner.nextLine().toLowerCase();
                if (line.length() != 1) {
                    throw new Exception("Line length is not 1. Please enter a single letter");
                }

                if (line.charAt(0) == 'y' || line.charAt(0) == 'n') {

                    return line.charAt(0);
                } else {
                    throw new Exception("Enter only y/n");
                }


            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }

        }
    }

    public static String revealLetters(String word, String hiddenWord, char letter) {
        final char[] hiddenWordChars = hiddenWord.toCharArray();

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
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