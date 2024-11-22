import java.util.Scanner;
import java.util.Random;

class Game {
  private static final int MIN=1;
  private static final int MAX=100;

  private static final String WELCOME_MESSAGE = """
    Welcome to the Number Guessing Game!
    I'm thinking of a number between 1 and 100.
    You have few chances to guess the correct number based on the difficulty level.   
    """;

  private Scanner scanner;
  private Random random;

  public Game() {
    this.scanner = new Scanner(System.in);
    this.random = new Random();
  }

  private static void printWelcomeMessage() {
    System.out.println(WELCOME_MESSAGE);
  }

  private static void printDifficultyLevelChoices() {
    System.out.println("Please select the difficulty level:");
    for (int i = 0; i < DifficultyLevel.values().length; i++) {
      System.out.println((i + 1) + ". " + DifficultyLevel.values()[i]);
    }
  }

  private DifficultyLevel getDifficultyLevel() {
    printDifficultyLevelChoices();
    DifficultyLevel selectedLevel = null;
    while (selectedLevel == null) {
      System.out.print("Select your choice (default - 1): ");
      String choice = scanner.nextLine();
      char c = choice.isEmpty() ? '1' : choice.charAt(0);
      if (!Character.isDigit(c)) {
        System.err.println("Invalid choice. Try again");
        continue;
      }
      int d = (int)(c - '1');
      if (d < 0 || d >= DifficultyLevel.values().length) {
        System.err.println("Invalid choice. Try again");
        continue;
      }
      selectedLevel = DifficultyLevel.values()[d];
    }
    return selectedLevel;
  }

  public void start() {
    printWelcomeMessage();
    var selectedLevel = getDifficultyLevel();
    printSelectedDifficultyLevelInformation(selectedLevel);
    int answer = thinkOfANumber();
    int guessCount = 0;
    int guessedNumber = -1;
    do {
      guessedNumber = guessANumber();
      ++guessCount;
      if (guessedNumber < answer) {
        System.out.println("Incorrect! The number is greater than " + guessedNumber + ".");
      } else if (guessedNumber > answer) {
        System.out.println("Incorrect! The number is less than " + guessedNumber + ".");
      } else {
        System.out.println("Congratulations! You guessed the correct number in " + guessCount + " attempts.");
      }
    } while (guessCount < selectedLevel.getNumberOfChances() && guessedNumber != answer);
    if (guessedNumber != answer) {
      System.out.println("\nBetter luck next time");
    }
  }

  private void printSelectedDifficultyLevelInformation(DifficultyLevel level) {
    System.out.println("\nGreat! You have selected the " + level.getName() + " difficulty level. You will get " + level.getNumberOfChances() + " chances to guess the number.");
    System.out.println("Let's start the game");
  }

  private int thinkOfANumber() {
    return random.nextInt(MIN, MAX + 1);
  }

  private int guessANumber() {
    while (true) {
      System.out.print("\nEnter your guess: ");
      String guess = scanner.next();
      try {
        return Integer.parseInt(guess);
      } catch (NumberFormatException e) {
        System.err.println("Invalid number " + guess + ". Try again!");
      }
    }
  }
}

enum DifficultyLevel {

  EASY("Easy", 10),
  MEDIUM("Medium", 5),
  HARD("Hard", 3);

  private String name;
  private int numberOfChances;

  DifficultyLevel(String name, int numberOfChances) {
    this.name = name;
    this.numberOfChances = numberOfChances;
  }

  public String getName() {
    return this.name;
  }

  public int getNumberOfChances() {
    return this.numberOfChances;
  }

  @Override
  public String toString() {
    return getName() + " (" + getNumberOfChances() + " chances)";
  }
}

public class GuessTheNumber {
  private static Scanner scanner = new Scanner(System.in);
  public static void main(String[] args) {
    System.out.println("\nLoading the game 🎮\n");
    while (true) {
      new Game().start();

      // Ask the user if they want to continue the game or not
      boolean shouldQuit = doesUserWantToQuit();
      if (shouldQuit) {
        System.out.println("\nExiting the game. Bye 👋.");
        break;
      } else {
        System.out.println("\nReloading the game 🎮\n");
      }
    }
    scanner.close();
  }

  private static boolean doesUserWantToQuit() {
    System.out.print("\nDo you want to play again(Y/N)? (Default - N): ");
    String choice = scanner.nextLine();
    return choice.isEmpty() || choice.charAt(0) == 'N';
  }
}

