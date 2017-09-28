/* Joseph T. Parsons
 * Bitwise. */

#include <stdio.h>


/**
 * Modified version of my printBinary function from Exercise19.c. Uses bitshifts instead of exponentiation. (I never understood bitshifts until now.)
 * Could be modified fairly easily to return instead of print, I believe.
 */
void printBinary(long num, int base) {
    if ((num >> (base - 1)) & 1) printf("1");
    else printf("0");

    if (base > 1) printBinary(num, --base);
}

int bitToggle(int value, int bit) {
    return value ^ bit;
}

int main()
{
    int num = 128 + 32 + 8 + 1; // 10101001

    printf("Original:     "); printBinary(num, 8);
    printf("\nFlipping 128: "); printBinary(num = bitToggle(num, 128), 8);
    printf("\nFlipping 64:  "); printBinary(num = bitToggle(num, 64),  8);
    printf("\nFlipping 2:   "); printBinary(num = bitToggle(num, 2),   8);
    printf("\nFlipping 16:  "); printBinary(num = bitToggle(num, 16),  8);
    printf("\nFlipping 128: "); printBinary(num = bitToggle(num, 128), 8);
    printf("\nFlipping 4:   "); printBinary(num = bitToggle(num, 4),   8);

    return 0;
}
