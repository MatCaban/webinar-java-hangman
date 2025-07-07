
import java.util.*;


// TODO: draw the actual hangman

// TODO: add colors somehow (very optional - you don't have to do this)


public class Main {
    public static void main(String[] args) {
        final String[] words = {"java", "hangman", "skillmea", "academy", "computer"};
        final Random random = new Random();
        String wordToGuess = selectRandomWord(random, words);

        String hiddenWord = generateHiddenWord(wordToGuess);

        final int MAX_INCORRECT_GUESSES = 6;
        int incorrectGuessesCounter = 0;
        List<Character> alreadyGuessed = new ArrayList<>();

        String[][] hangman = createHangman();



        final Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Hangman");
        System.out.println("Guess the word: " + hiddenWord);


        while (true) {

            if (incorrectGuessesCounter == MAX_INCORRECT_GUESSES || !hiddenWord.contains("_")) {
                System.out.println("");
                if (!hiddenWord.contains("_")) {
                    System.out.println("Congratulations, you guessed it: " + wordToGuess);
                } else {
                    System.out.println("Sorry, you have run out of guesses. It was " + wordToGuess);
                }


                if (!doUserWantToContinue(scanner)) {
                    break;
                } else {
                    System.out.println("=".repeat(30));
                    System.out.println("Welcome to new game");
                    incorrectGuessesCounter = 0;
                    wordToGuess = selectRandomWord(random, words);
                    hiddenWord = generateHiddenWord(wordToGuess);
                    alreadyGuessed.clear();
                    hangman = createHangman();
                }
            }


            System.out.print("Enter a letter: ");
            final char guess = scanLetter(scanner);


            if (hiddenWord.contains(String.valueOf(guess))) {
                System.out.println("This letter is already revealed");
            } else if (wordToGuess.contains(String.valueOf(guess))) {
                hiddenWord = revealLetters(wordToGuess, hiddenWord, guess);
                System.out.println("Correct guess! Updated word: " + hiddenWord);
            } else if (alreadyGuessed.contains(guess)) {
                System.out.println("You already try this letters");
                for (char c : alreadyGuessed) {
                    System.out.print(c + " ");
                }
                System.out.println("");

            } else {
                alreadyGuessed.add(guess);
                incorrectGuessesCounter++;
                System.out.println("Incorrect guess, you have (" + (MAX_INCORRECT_GUESSES - incorrectGuessesCounter) + ") guesses left");
                System.out.println("=".repeat(30));
                updateHangman(hangman, incorrectGuessesCounter);
                printHangman(hangman);
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

    public static void printHangman(String[][] hangman) {
        for (String[] row : hangman) {
            for (String col : row) {
                System.out.print(col);
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