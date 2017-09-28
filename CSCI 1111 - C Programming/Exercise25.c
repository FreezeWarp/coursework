/**
 * Joseph T. Parsons
 * Demonstrates a loop that converts a string to all upper-case text. Assumes ASCII. Uses pointer notation instead of array notation. Which is easy, because these are single-dimension arrays. Multi-dimension pointer notation is nonsensical to me.
 * Note: Exercise24.c is considered the "proper" version of this file. Please refer to it if looking for examples of stuff. Though, this does add the multi-dimensional array test.
*/


#include <stdio.h>
#include <string.h>


/* You can easily change these to support any character set wherein lower and uppercase letters are all sequential.
 * For instance, EBCDIC will not work, because each case is divided up into three sequential blocks.
 * (As an aside, I do wonder whether pre-processor constants are better. These work a lot like the constants I'm familiar with, e.g. in Perl and PHP, which is why I'm using them.) */
const int CHAR_UPPERCASE_MIN = 97;
const int CHAR_UPPERCASE_MAX = 122;
const int CHAR_LOWER_TO_UPPER_SHIFT = 32;



/**
 * Modifies the passed array to shift all lowercase letters to uppercase.
 */
void asciiToUppercase(char string[])
{
    int i;

    for (i = 0; i < strlen(string); i++) {
        if (*(string + i) >= CHAR_UPPERCASE_MIN && *(string + i) <= CHAR_UPPERCASE_MAX) // Is string[i] a lowercase letter?
            *(string + i) = (*(string + i) + -CHAR_LOWER_TO_UPPER_SHIFT); // If so, shift it to a certain range, making it uppercase.
    }
}



/**
 * Reads an array and prints all values as well as their corresponding indexes.
 * length is a pointer for demonstrative purposes. It really shouldn't be.
 */
void printIntArray(const int array[], int *length)
{
    int i;

    for (i = 0; i < *length; i++)
        printf("Index %d: %d\n", i, (int) *(array + i)); // Single-statement loops are fun. Also possibly bad style.
}



/**
 * Reads an array and prints all values as well as their corresponding indexes.
 * length is a pointer for demonstrative purposes. It really shouldn't be.
 */
void print2DIntArray(int length, int depth, const int array[length][depth])
{
    int i, j;

    for (i = 0; i < length; i++) {
        printf("Index %d:\n", i);
        for (j = 0; j < depth; j++)
            printf("    Index %d,%d: %d\n", i, j, (int) *(*(array + i) + j));
    }
}



int main() {
    char string[] = "Hi??[]abCDas65^";
    int ints[] = {1,5,11,255,65535};
    int moreInts[3][10] = {
        {1,5,11,255,65535},
        {2,3,5,7,11,13,17,23},
        {1,1,2,3,5,8,13,21,34,55}
    };
    int intsSize = sizeof(ints) / sizeof(ints[0]); // We're passing this as a pointer for demonstrative purposes. In reality, it makes no sense to be a pointer.


    /* ASCII-To-Upper Test */
    printf("Original String: %s\n", string);
    asciiToUppercase(string);
    printf("Uppercase String: %s\n", string);


    /* Print Ints Test */
    printf("\nArray of Integers, {1,5,11,255,65535}, Contents:\n");
    printIntArray(ints, &intsSize);


    /* Print More Ints Test */
    printf("\nMulti-dimensional Array of Integers Contents:\n");
    print2DIntArray(sizeof(moreInts) / sizeof(moreInts[0]), sizeof(moreInts[0]) / sizeof(moreInts[0][0]), moreInts); // Got an incompatible pointer type warning here (program still works as expected). Not sure of the cause yet.
}
