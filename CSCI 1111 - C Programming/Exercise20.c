/**
 * Joseph T. Parsons
 * ...Pointers. They are weird.
*/

#include <stdio.h>

void doubler(int *value) {
    printf("Number Pointer: %p\n", value);
    
    *value = *value * 2; // ...This makes no sense.
}

int main( )
{
    int number = 11;

    printf("Number: %d\n", number);
    
    printf("Number Pointer: %p\n", &number);

    doubler(&number);

    printf("Number Doublered: %d\n", number);
    
    return 0;
}
