/* Joseph T. Parsons
 * Bitwise. */

#include <stdio.h>

int main()
{
    unsigned int num = 1094861636U, z;

    for (int i = 0; i < sizeof(int); i++) {
        // ...I'm an idiot. I kept trying 11111111 instead of 255. And I've worked with bitwise before.
        z = (num >> (8 * (sizeof(int) - i - 1))) & 255; // There is probably a slightly cleaner way of doing this, but this is basically efficient. So that's nice.
        
        printf("byte %d from left: %d (%c)\n", i, z, z);
    }
    
    return 0;
}
