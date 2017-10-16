#include <stdio.h> // Input/output
#include "sequence.h" // Sequence functions.

int main(int argc, char *argv[])
{
    /* Basic Tests */
    int sequence1a[] = {7, 5, 9, 13, 12, -1};
    int sequence1b[] = {4, 2, 25, 33, 26, -1};
    int sequence2a[] = {12, -1};
    int sequence2b[] = {8, -1};
    int sequence3a[] = {9, 5, 7, 13, 12, -1};
    int sequence3b[] = {25, 2, 4, 33, 26, -1};
    int sequence4a[] = {12, 5, 9, 13, 7, -1};
    int sequence4b[] = {4, 2, 26, 33, 25, -1};

    printf("{7, 5, 9, 13, 12} and {4, 2, 25, 33, 26}: %s\n", orderEquivilent(sequence1a, sequence1b) ? "yes" : "no");
    printf("{12} and {8}: %s\n", orderEquivilent(sequence2a, sequence2b) ? "yes" : "no");
    printf("{9, 5, 7, 13, 12} and {25, 2, 4, 33, 26}: %s\n", orderEquivilent(sequence3a, sequence3b) ? "yes" : "no");
    printf("{12, 5, 9, 13, 7} and {4, 2, 26, 33, 25}: %s\n", orderEquivilent(sequence4a, sequence4b) ? "yes" : "no");

    /* Input */
    int inputSequence[100] = {0};
    int inputSequence1Length; // Find the half point of the sequence (rounding up) 
    int inputSequence2Length; // Find the remaining length of the sequence (rounding down)
    
    printf("Input your sequence members, seperated by spaces (terminate with 0 or negative; maximum 100):\n");
    scanSequence(inputSequence, &inputSequence1Length, &inputSequence2Length);
    
    int equivilent = orderEquivilentWithLengths(inputSequence, inputSequence1Length, inputSequence + inputSequence1Length, inputSequence2Length);
    printSequence(inputSequence, inputSequence1Length); // Print the first sequence (the first half of the input)
    printf(" and ");
    printSequence(inputSequence + inputSequence1Length, inputSequence2Length);  // Print the second sequence (the second half of the input) 
    printf(": %s\n", equivilent ? "yes" : "no");    

    return 0;
}
