import java.util.Scanner;

/**
 * A collection of prompts to obtain user input, borrowed from https://github.com/FreezeWarp/372Groupwork-Project1/blob/master/src/UserInterfacePrompts.java.
 *
 * @author  Eric Fulwiler, Daniel Johnson, Joseph T. Parsons, and Cory Stadther
 */
public class UserInterfacePrompts {
    /**
     * Prompts the user for a line of text.
     *
     * @param promptText The text to display for the prompt.
     *
     * @return A string of text, up to (but not including) a new line.
     */
    public static String promptLine(String promptText) {
        Scanner s = new Scanner(System.in);

        System.out.print(promptText);

        String input = s.nextLine();

        return input;
    }
}