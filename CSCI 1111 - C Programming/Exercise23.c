/**
 * Joseph T. Parsons
 * Demonstrates a loop that converts a string to all upper-case text. Assumes ASCII.
*/

#include <stdio.h>
#include <string.h>

void asciiToUppercase(char string[]) {
    int i;
    
    for (i = 0; i < strlen(string); i++) {
        if (string[i] >= 97 && string[i] <= 122) // Is string[i] a lowercase letter?
            string[i] = string[i] - 32; // If so, shift it down 32 characters, making it uppercase. (I believe the book had a different way of doing this. Dunno which is better.)
    }
}

int main( )
{
    char string[] = "Hi??[]abCDas65^";
    printf("Original String: %s\n", string);
    
    asciiToUppercase(string);
    printf("New String: %s", string);
    
    return 0;
}
