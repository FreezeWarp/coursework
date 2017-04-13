import java.util.*;

/**
 * Created by Joseph on 16/09/2016.
 * Based on a predefined array of candidate names, the program will ask for a number of precincts and then ask for the total votes each predefined candidate received in each of the x precincts.
 *
 * UNTESTED WITH MORE THAN TWO CANDIDATES (but was coded with more than two in mind, so should be pretty easy to get working)
 */
public class Part2 {
    public static void main(String[] args) {
        // Define constants
        final int TERMINATOR = -1;
        final String candidates[] = new String[]{"Polly Tichen", "Ernest Orator"};
        Scanner scanner = new Scanner(System.in);


        // Ask for precincts
        System.out.print("Number of precincts? ");
        int precincts = scanner.nextInt();


        // Some quick assertions
        assert(candidates.length >= 2);
        assert(precincts >= 1);


        // Allocate preinct counts variable
        int[][] precinctCounts = new int[precincts][candidates.length];


        // Iterate through precincts, asking for counts
        for (int precinctNo = 0; precinctNo < precincts; precinctNo++) {
            System.out.println("Precinct " + (precinctNo + 1) + ": ");

            for (int candidateNo = 0; candidateNo < candidates.length; candidateNo++) {
                System.out.print("Votes for " + candidates[candidateNo] + "? \t");

                precinctCounts[precinctNo][candidateNo] = scanner.nextInt();
            }
        }


        // Calculate totals
        int totalVotes = 0;

        int[] candidateTotals = new int[candidates.length];
        int[] candidateCarries = new int[candidates.length + 1]; // Last element for ties

        for (int precinctNo = 0; precinctNo < precincts; precinctNo++) {
            int leadingCandidateNo = 0;
            int leadingCandidateShare = 0;
            boolean leadingCandidateTied = false;

            for (int candidateNo = 0; candidateNo < candidates.length; candidateNo++) {
                totalVotes += precinctCounts[precinctNo][candidateNo];
                candidateTotals[candidateNo] += precinctCounts[precinctNo][candidateNo];

                if (candidateNo == 0 || precinctCounts[precinctNo][candidateNo] > leadingCandidateShare) {
                    leadingCandidateShare = precinctCounts[precinctNo][candidateNo];
                    leadingCandidateNo = candidateNo;
                    leadingCandidateTied = false;
                }
                else if (precinctCounts[precinctNo][candidateNo] == leadingCandidateShare) {
                    leadingCandidateTied = true;
                }
            }

            if (leadingCandidateTied)
                candidateCarries[candidates.length]++;
            else
                candidateCarries[leadingCandidateNo]++;
        }


        // Display per-candidate totals
        for (int candidateNo = 0; candidateNo < candidates.length; candidateNo++) {
            System.out.println("Total votes for " + candidates[candidateNo] + ": \t" + candidateTotals[candidateNo] + " (" + 100 * (float)candidateTotals[candidateNo] / totalVotes + "%)");
        }


        // Find & display winner
        int winnerNo = 0;
        int winnerTotal = candidateTotals[0];
        for (int candidateNo = 1; candidateNo < candidates.length; candidateNo++) {
            if (candidateTotals[candidateNo] > winnerTotal) {
                winnerTotal = candidateTotals[candidateNo];
                winnerNo = candidateNo;
            }
        }

        System.out.println("Winner: \t\t\t\t" + candidates[winnerNo]);


        // Display per-candidate carries
        for (int candidateNo = 0; candidateNo < candidates.length; candidateNo++) {
            System.out.println("Number of precincts won by " + candidates[candidateNo] + ": " + candidateCarries[candidateNo]);
        }
        System.out.println("Number of precincts with tie: " + candidateCarries[candidates.length]);
    }
}
