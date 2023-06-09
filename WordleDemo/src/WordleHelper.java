import java.io.IOException;
import java.nio.file.*;;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * This class contains the code that will operate the Wordle game. It includes a method for randomly
 * generating a word from a text file as an answer to the game, as well as a method for allowing input from the user
 * to guess words until the end of the game. It also handles faulty input from the user accordingly.
 * @author Liam Smith
 */

public class WordleHelper {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_DEFAULT = "\u001B[0m";

    /**
    * Parses random 5-letter words from a file to an array of Strings.
     * @param fileName name of file that contains 5757 random 5-letter words as potential answers.
     * @return String [] randomWordsArray - array of 5-letter words
     */
    public static String[] randomWordsToArray(String fileName) {
        List<String> randomWords = null;
        try {
            randomWords = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] randomWordsArray = randomWords.toArray(new String[0]);
        return randomWordsArray;
    }
    /**
     * Generates a random word from a file to be used as the answer to the Wordle game.
     * @param randomWordsArray array of words from which a random word will be generated.
     * @return String answer - the random word that will be used in the current Wordle game as the answer
     */
    public static String generateAnswer(String [] randomWordsArray){
        String answer = randomWordsArray[new Random().nextInt(randomWordsArray.length)].toUpperCase();
        return answer;
    }
    /**
     * Checks if a String is contained within a String array of words
     * @param word the String that will be compared against different Strings in the array
     * @param randomWordsArray the array of Strings that will be traversed to check for a given String
     * @return true if the String word equals one of the Strings in the array, false otherwise
     */

    public static boolean isValidWord(String word, String[] randomWordsArray){
        for (int i = 0; i < randomWordsArray.length; i++){
            if (word.equals(randomWordsArray[i].toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks how many times a given char appears in a given String
     * @param word the word that will be scanned through for its characters
     * @param c the char that the user wishes to check for its number of instances within the word
     * @return int count - the number of times the char appears in the word
     */

    public static int charCount(String word, char c){
    int count = 0;
        for(int i = 0; i < word.length(); i++){
            if (word.charAt(i) == c){
                count++;
            }
        }
        return count;
    }

    /**
     * Allows for user to input their guesses and returns a certain message depending on the result of the game.
     * Has the ability to handle errors depending on the type of improper input from the user.
     */
    public static void gamePlay(){
        boolean correctAnswer = false;
        String [] randomWordsArray = randomWordsToArray("C:\\Users\\Liam\\CS213\\WordleDemo\\src\\randomWords.txt");
        String answer = generateAnswer(randomWordsArray);
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 6; i++) {
            System.out.println("Guess " +Integer.toString(i+1) + " of 6");
            System.out.println("Enter your 5-letter guess: ");
            String guess = scanner.nextLine().toUpperCase();
            while (guess == null || !guess.matches("[a-zA-Z]+") || guess.length() != 5 || !isValidWord(guess,randomWordsArray)) {
                if (guess.length() != 5){
                    System.out.println("Your guess must be 5 letters!");
                }
                else {
                    System.out.println("Your guess must be a valid word!");
                }
                System.out.println("Enter your 5-letter guess: ");
                guess = scanner.nextLine().toUpperCase();
            }
            char guessChar;
            StringBuilder coloredResult = new StringBuilder();
            for (int guessIndex = 0; guessIndex < guess.length(); guessIndex++) {

                guessChar = guess.charAt(guessIndex);
                if (guessChar == answer.charAt(guessIndex)) {
                    coloredResult.append(ANSI_GREEN + guessChar + ANSI_DEFAULT);
                } else if (answer.contains(Character.toString(guessChar))) {
                    if ((charCount(answer, guessChar)) < (charCount(guess, guessChar))) {
                        coloredResult.append(guessChar);
                    } else {
                        coloredResult.append(ANSI_YELLOW + guessChar + ANSI_DEFAULT);
                    }
                }
                else {
                    coloredResult.append(guessChar);
                }
            }
            System.out.println(coloredResult.toString());
            coloredResult = new StringBuilder();
            if (guess.equals(answer)){
                System.out.println("Congratulations! You guessed correctly.");
                correctAnswer = true;
                break;
            }
        }
        if (!correctAnswer) {
            System.out.println("Sorry, you did not answer correctly.\nThe correct answer was " + answer);
        }
        System.out.println("Would you like to play again? Enter 'Y' or 'Yes': ");
        String response = scanner.nextLine();
         if (response.toUpperCase().equals("Y") || response.toUpperCase().equals("YES")){
             gamePlay();
         }
         else {
             System.out.println("You entered '" +response+ "' thanks for playing!");
         }
    }

}
