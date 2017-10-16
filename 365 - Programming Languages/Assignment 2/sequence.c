#include <stdio.h> // Input/output
#include "sequence.h" // Sequence functions.

/**
 * This program takes in an input of numbers and performs a bubble sort on them.
 * It also takes in the sort direction to use.
 */

int main(int argc, char *argv[])
{
    /* Basic Tests */
    int sequence1[] = {7, 5, 9, 13, 12, 4, 2, 25, 33, 26, -1};
    int sequence2[] = {12, -1};
    int sequence3[] = {25, 2, 4, 33, 26, 25, 2, 4, 33, 26, -1};

    int* sequences[] = {sequence1, sequence2, sequence3};
    int numSequences = 3;

    for (int i = 0; i < numSequences; i++) {
        printSequence(sequences[i], sequenceSize(sequences[i]));
        printf("\n  sorted ASC: ");
        printSequence(bubbleSort(sequences[i], sequenceSize(sequences[i]), ASC), sequenceSize(sequences[i]));
        printf("\n  sorted DESC: ");
        printSequence(bubbleSort(sequences[i], sequenceSize(sequences[i]), DESC), sequenceSize(sequences[i]));
        printf("\n\n");
    }


    /* Input Sequence */
    int inputSequence[101] = {0};

    printf("Input your sequence members, separated by spaces (terminate with 0 or negative; maximum 100):\n");
    scanSequence(inputSequence, 100);


    /* Input Sort Order
     * This is obviously imperfect, but we basically keep scanning characters until one is either an "A" for ascending or "D" for descending. */
    printf("\nWould you like to sort ascendingly or descendingly? ");
    char order;
    while (scanf("%c", &order),
           (order != 'a' && order != 'A' && order != 'd' && order != 'D'));


    /* Convert Inputted Sort Order to Enum Value */
    enum order sortOrder;
    if (order == 'a' || order == 'A')
        sortOrder = ASC;
    if (order == 'd' || order == 'D')
        sortOrder = DESC;


    /* Sort & Print */
    printSequence(inputSequence, sequenceSize(inputSequence));
    printf(" sorted: ");
    printSequence(bubbleSort(inputSequence, sequenceSize(inputSequence), sortOrder), sequenceSize(inputSequence));
    printf("\n");

    return 0;
}
