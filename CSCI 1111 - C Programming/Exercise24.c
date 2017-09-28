/**
 * Joseph T. Parsons
 * Demonstrates a loop that converts a string to all upper-case text. Assumes ASCII.
 * You know, I really can't decide whether to use two or four space indents. I usually use two, but I've been generally sticking to four for this class.
 * "At this point, most students of C begin realizing why pointers are considered one of the more difficult aspects of the language." You don't say?
*/

#include <stdio.h>
#include <stdbool.h>
#include <string.h>


/* You can easily change these to support any character set wherein lower and uppercase letters are all sequential.
 * For instance, EBCDIC will not work, because each case is divided up into three sequential blocks.
 * (As an aside, I do wonder whether pre-processor constants are better. These work a lot like the constants I'm familiar with, e.g. in Perl and PHP, which is why I'm using them.) */
const int CHAR_LOWERCASE_MIN = 65;
const int CHAR_LOWERCASE_MAX = 90;
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
        if (string[i] >= CHAR_UPPERCASE_MIN && string[i] <= CHAR_UPPERCASE_MAX) // Is string[i] a lowercase letter?
            string[i] = (char) (string[i] + -CHAR_LOWER_TO_UPPER_SHIFT); // If so, shift it to a certain range, making it uppercase. (I thought the book had a different way of doing this, but it was actually Python ord() and chr() I was thinking of.)
    }
}

/**
 * Modifies the passed array to shift all uppercase letters to lowercase.
 */
void asciiToLowercase(char string[])
{
    int i;

    for (i = 0; i < strlen(string); i++) {
        if (string[i] >= CHAR_LOWERCASE_MIN && string[i] <= CHAR_LOWERCASE_MAX) // Is string[i] an uppercase letter?
            string[i] = (char) (string[i] + CHAR_LOWER_TO_UPPER_SHIFT); // If so, shift it to a certain range, making it lowercase.
    }
}



/**
 * Reads an array and prints all values as well as their corresponding indexes.
 * Oh good, there are read-only arrays... I was getting really worried for a bit there.
 */
void printIntArray(const int array[], int length)
{
    int i;

    for (i = 0; i < length; i++) {
        printf("Index %d: %d\n", i, (int) array[i]);
    }
}



/**
 * Unit(?)-test to see if asciiToUppercase() and asciiToLowercase() works.
 * I had thought "driver" meant unit test. It doesn't, but I'm going to keep this one in. I would write one for the print function, but I have no idea how.
 * Also, I have no idea what a unit test is, and I've never used them (I'm a horrible coder with some things). So... this may or may not even be that.
 */
bool testAsciiToCase()
{
    char string[20] = "Hi??[]abCDasz65^hTu";
    char stringUpper[20] = "HI??[]ABCDASZ65^HTU";
    char stringLower[20] = "hi??[]abcdasz65^htu";

    asciiToUppercase(string);
    if (strcmp(string, stringUpper) != 0)
        return false;
        
    asciiToLowercase(string);
    if (strcmp(string, stringLower) != 0)
        return false;

    return true;
}



int main() {
    /* Test the ascii function, for funsies. */
    if (!testAsciiToCase()) {
        printf("Tests failed.");
    }
    else {
        char string[] = "Hi??[]abCDas65^";
        int ints[] = {1,5,11,255,65535};


        /* ASCII-To-Upper Test */
        printf("Original String: %s\n", string);
        asciiToUppercase(string);
        printf("Uppercase String: %s\n", string);
        asciiToLowercase(string);
        printf("Lowercase String: %s\n\n", string);


        /* Print Ints Test */
        printf("Array of Integers, {1,5,11,255,65535}, Contents:\n");
        printIntArray(ints, sizeof(ints) / sizeof(ints[0]));
    }
    
    return 0;
}
