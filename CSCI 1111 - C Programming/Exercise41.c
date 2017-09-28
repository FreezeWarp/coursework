/* Joseph T. Parsons
 * Uh.... Unions, I guess. */

#include <stdio.h>

union endian {
    unsigned char b[4];
    int i;
};

int main()
{
    union endian nux;
    nux.i = 255;
    
    printf("int = %d\n\n", nux.i);
    for (int i = 0; i < 4; i++)
        printf("char[%d] = %X\n", i, nux.b[i]);

    printf("\nSize: %d\n\n", sizeof(nux));
    
    if (nux.b[0] == 255) printf("Little-Endian");
    else if (nux.b[sizeof(int) - 1] == 255) printf("Big-Endian");
    else printf("Something Weird");

    return 0;
}
