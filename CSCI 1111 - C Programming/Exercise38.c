/* Joseph T. Parsons
 * Bitwise. */

#include <stdio.h>

void printBase(long num, int base) {
    if (num > (base - 1))
        printBase(num / base, base);

    printf("%lld", num % base);
}

int main()
{
    int num = 13; // 1101 (8+4+1)
    
    printf("Number:");
    printBase(num, 2);
    putchar('\n');
    
    printf("Number:");
    printBase(num = num<<2, 2);
    putchar('\n');

    printf("Number:");
    printBase(num = num>>2, 2);
    putchar('\n');

    return 0;
}
