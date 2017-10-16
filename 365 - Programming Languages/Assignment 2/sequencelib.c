#include <stdio.h> // Input/output
#include "sequence.h" // Sequence functions.

/**
 * Scan a sequence from space-seperated numbers on a single line.
 *
 * @param sequence Where the sequence should be stored.
 * @param maxValues The maximum number of values that may be read.
 */
void scanSequence(int sequence[], int maxValues) {
    int inputValue;
    int numberInput = 0;

    while (scanf("%d", &inputValue),
           inputValue != 0 && numberInput < maxValues) {
        if (inputValue < 0) continue; // ignore negative values

        sequence[numberInput++] = inputValue;
    }
}


/**
 * Sorts an array of integers using bubble sort logic.
 *
 * @param sequence The array to sort.
 * @param sequenceLength The number of entries in the array to sort.
 * @param sortOrder The order of the sort, ascending or descending.
 *
 * @return a pointer to the start of the sorted array
 */
int* bubbleSort(int sequence[], int sequenceLength, enum order sortOrder) {
    // Do this once for every element in the list except for one.
    for (int i = sequenceLength - 1; i > 0; i--) {

        // Start at the i-nth element, going to the second-to-last element, and skipping all elements before the i-nth element.
        // (We skip the last element, because will be comparing a pair of elements each time, and the last element will be part of the last pair.)
        for (int j = 0; j < i; j++) {

            // If the order is ascending, then we swap elements when the second is *less* than the first (and thus in descending order).
            if (sortOrder == ASC
                && compareInts(sequence[j+1], sequence[j]) < 0) {
                swapInts(&sequence[j+1], &sequence[j]);
            }

            // If the order is descending, then we swap elements when the second is *greater* than the first (and thus is ascending order).
            else if (sortOrder == DESC
                && compareInts(sequence[j+1], sequence[j]) > 0) {
                swapInts(&sequence[j+1], &sequence[j]);
            }
        }
    }

    return sequence;
}


/**
 * Calculates the size of a sequence of integers, where any number less than or equal to 0 marks the end of the sequence.
 * For instance, sequenceSize({1, 2, 3, 0}) = 3.
 *
 * @param sequence Array of positive numbers, terminated by 0/negative.
 *
 * @return The total number of numbers.
 */
int sequenceSize(int sequence[]) {
    int size = 0;

    while (sequence[size++] > 0)
        ;

    return size - 1;
}


/**
 * Prints a sequence of a certain length in the format {1, 2, 3}
 *
 * @param sequence The array of integers to print.
 * @param sequenceLength The size of the array.
 */
void printSequence(int sequence[], int sequenceLength) {
    printf("{");

    // Print first n-1 elements with seperator
    for (int i = 0; i < sequenceLength - 1; i++) {
        printf("%d, ", sequence[i]);
    }

    // Print last element without seperator
    if (sequenceLength > 0) {
        printf("%d", sequence[sequenceLength - 1]);
    }

    printf("}");
}


/**
 * Compares two integers, returning 0 if equal, 1 if the first is greater, and -1 if the second is greater.
 * For instance, compareInts(1, 1) = 0, compareInts(1, 2) = -1, and compareInts(2, 1) = 1.
 *
 * @param a The first number to compare.
 * @param b The second number to compare.
 *
 * @return 0 if a==b, 1 if a>b, -1 if b>a.
 */
int compareInts(int a, int b) {
    if (a == b) {
        return 0;
    }
    else {
        return a > b ? 1 : -1;
    }
}



/**
 * Swap two integers in memory.
 *
 * @param a The first number to swap.
 * @param b The second number to swap.
 */
void swapInts(int* a, int* b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}
