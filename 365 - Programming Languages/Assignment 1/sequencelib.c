/**
 * Scan a sequence from space-seperated numbers on a single line.
 *
 * @param sequence Where the sequence should be stored.
 * @param sequenceFirstHalf A pointer delimiting the end of the first sequence and start of the second.
 * @param sequenceFirstHalf A pointer delimiting the end of the second sequence.
 */ 
void scanSequence(int sequence[], int* sequenceFirstHalf, int* sequenceSecondHalf) {
    int inputValue;
    int numberInput = 0;
    while (scanf("%d", &inputValue), inputValue > 0) {
        sequence[numberInput++] = inputValue;	
    }

    *sequenceFirstHalf = numberInput / 2 + numberInput % 2; // Find the half point of the sequence (rounding up) 
    *sequenceSecondHalf = numberInput / 2; // Find the remaining length of the sequence (rounding down)
}


/**
 * Compares the order equivilence of two sequences, where a sequence is an integer array of the given length.
 *
 * @param sequence1 The first sequence.
 * @param sequence1Length The number of entries in the first sequence.
 * @param sequence2 The second sequence.
 * @param sequence2Length The number of entries in the second sequence.
 *
 * @return 1 if order equivilent, 0 if not.
 */
int orderEquivilentWithLengths(int sequence1[], int sequence1Length, int sequence2[], int sequence2Length) {
    // Find the greater of the two sequence lengths.
    int numEntries = sequence1Length > sequence2Length ? sequence1Length : sequence2Length;

    for (int i = 0; i < numEntries; i++) {
        for (int j = i; j < numEntries; j++) {
            if (j >= sequence1Length) {
                printf("Miss: no value for sequence1[%d]\n", j);
                return 0;
            }

            else if (j >= sequence2Length) {
                printf("Miss: no value for sequence2[%d]\n", j);
                return 0;
            }

            else if (compareInts(sequence1[i], sequence1[j])
                != compareInts(sequence2[i], sequence2[j])) {
                printf("Miss: sequence1[%d] = %d, sequence1[%d] = %d, sequence2[%d] = %d, sequence2[%d] = %d\n", i, sequence1[i], j, sequence1[j], i, sequence2[i], j, sequence2[j]);
                return 0;
            }
        }
    }

    return 1;
}


/**
 * Compares the order equivilence of two sequences, where a sequence is an integer array terminated by a value < 1.
 *
 * @param sequence1 The first sequence.
 * @param sequence2 The second sequence.
 *
 * @return 1 if order equivilent, 0 if not.
 */
int orderEquivilent(int sequence1[], int sequence2[]) {
    return orderEquivilentWithLengths(sequence1, sequenceSize(sequence1), sequence2, sequenceSize(sequence2));
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

    return size;
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
