/**
 * Joseph T. Parsons
 * Demonstrates two forms of a recursive printBinary function. Both were done before reading the book's example, and seem to be unique from it. Both are likely also less efficient as a result.
 * Also added a generic method that works with any base, taken from class.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

long pow2(int base) {
    int value = 1;

    for (base; base > 1; base--) {
        value *= 2;
    }

    return value;
}

/* Recursive function to print binary like a computer. */
void printBinary(long num, int base) {
    if (num & pow2(base)) printf("1"); // I mean, I could play by the rules and do some silly subtraction stuff. That's how I convert to binary in my head. But this is a computer!
    else printf("0");

    if (base > 1) printBinary(num, --base);
}

/* Recursive function to print binary like a silly billy. */
void printBinarySillyBilly(long num, int base) {
    if ((num - pow2(base)) >= 0) {
        printf("1");

        if (base > 1) printBinarySillyBilly(num - pow2(base), base - 1);
    }
    else {
        printf("0");

        if (base > 1) printBinarySillyBilly(num, base - 1);
    }
}

/* Recursive function to print any base-10 number in another base. */
void printBase(long num, int base) {
    if (num > (base - 1))
        printBase(num / base, base);

    printf("%lld", num % base);
}

int main( )
{
    long number;
    const int BINARYSIZE = 32;

    printf("Enter an integer to get it's %d-bit binary represenation. Enter any letter to quit.\n\n", BINARYSIZE);

    while (scanf("%d", &number) == 1) {
        printf("Normal Method:      ");
        printBinary(number, BINARYSIZE);
        printf("\nSilly Billy Method: ");
        printBinarySillyBilly(number, BINARYSIZE);
        printf("\nClass Method:       ");
        printBase(number, 2);
        printf("\n\n");
    }
}
