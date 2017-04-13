import java.util.*;

/**
 * Created by Joseph on 16/09/2016.
 * Based on a predefined HashMap of candidates, users will be able to enter defined candidate integer codes and have them recorded as votes for the corresponding candidate. The program will then total the votes and find the winner.
 * UNTESTED WITH MORE THAN TWO CANDIDATES (but was coded with more than two in mind, so should be pretty easy to get working)
 *
 * (Generally speaking, if I'm learning a language, I try to do stuff that I haven't yet learnt for practice. I often do these things incorrectly, so bare with me.)
 * And in hindsight, I probably should have just used flat arrays to separately store the key press values (1, 2) and the list of candidates. But, you know, live and learn.
 *
 * hashMap: http://stackoverflow.com/questions/5122913/java-associative-array
 * hasNext: http://stackoverflow.com/questions/9633991/how-to-check-if-processing-the-last-item-in-an-iterator
 * itation: http://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
 */
public class Part1 {
    public static void main(String[] args) {
        // Define constants
        final int TERMINATOR = -1;
        Scanner scanner = new Scanner(System.in);


        // Define candidates (dunno if this makes any sense in Java, trying to do an associative array)
        Map<Integer, String> candidates = new HashMap<Integer, String>();
        candidates.put(1, "Tichen");
        candidates.put(2, "Orator");


        // Define candidate counts
        Map<Integer, Integer> candidateCounts = new HashMap<Integer, Integer>();
        candidates.forEach((k,v) -> candidateCounts.put(k, 0));


        // Build prompt and error messages
        String promptMessage = "Enter your vote <";
        String invalidInputMessage = "ERROR!! Please enter ";

        Iterator it = candidates.entrySet().iterator(); // ...This is the silliest way of doing an iteration. I swear it's easier in BASIC.
        boolean itFirst = true;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            promptMessage += (pair.getValue()+":"+pair.getKey()+", ");
            invalidInputMessage += ( // ...I don't know if this is a horrible style or not. I've written similar things in languages where there was no cleaner way.
                itFirst
                    ? ""
                    : it.hasNext()
                        ? ", "
                        : ", or "
            ) + pair.getKey();

            itFirst = false;
        }

        promptMessage += "end:" + TERMINATOR + ">: ";


        // Get input from user/stdin
        System.out.print(promptMessage);

        int input;
        while ((input = scanner.nextInt()) != TERMINATOR) {
            if (candidates.get(input) == null)
                System.out.println(invalidInputMessage);
            else
                candidateCounts.put(input, candidateCounts.get(input) + 1);

            System.out.print(promptMessage);
        }


        // Print totals/calculations
        int totalVotes = 0;
        for (int j : candidateCounts.values()) {
            totalVotes += j;
        }

        System.out.println("Total number of votes: \t" + totalVotes);

        for (Map.Entry<Integer, String> entry : candidates.entrySet()) {
            System.out.println(entry.getValue() + ": \t\t\t\t" + candidateCounts.get(entry.getKey()) + " (" + 100 * (float)candidateCounts.get(entry.getKey()) / totalVotes + "%)");
        }


        // Find winner name
        int maxEntry = -1;
        int[] winners = new int[10];
        int winnersPos = 0;

        for (Map.Entry<Integer, Integer> entry : candidateCounts.entrySet()) {
            if (entry.getValue().compareTo(maxEntry) > 0) {
                maxEntry = entry.getValue();

                Arrays.fill(winners, -1);
                winners[0] = entry.getKey();
                winnersPos = 0;
            }
            else if (entry.getValue().compareTo(maxEntry) == 0) {
                winners[++winnersPos] = entry.getKey();
            }
        }

        if (winnersPos > 0) {
            System.out.print("It is a tie between: ");

            for (int j : winners)
                if (j != -1)
                    System.out.print(candidates.get(j) + " "); // This should be more thorough, but I'm tired of this one so I'm leaving it unfinished
        }
        else {
            System.out.println(candidates.get(winners[0]) + " wins.");
        }
    }
}
