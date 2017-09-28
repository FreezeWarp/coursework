/**
 * Joseph T. Parsons
 *
 * Simple program that demonstrates loops.
 */

#include <stdio.h>

int main(void)
{
    int x = 100, y = 100; // Seriously, how am I supposed to use descriptive variables here? Is "whileVar" and "forVar" better than just x and y?


    /* Do Stuff */
    while (x > 0) {
        printf("%d ", x);

        x -= 5;
    }
    printf("\n");


    for (y = 100; y > 0; y -= 5) { // Setting "y" again for readability. In other languages I wouldn't define "y" at all until now.
        printf("%d ", y);
    }
    printf("\n");


    /* This also works for C99 versions. I have no idea where 199901L comes from; just copying from Wikipedia (http://en.wikipedia.org/wiki/C99#Version_detection). */
    #if __STDC_VERSION__ >= 199901L
    for (int z = 100; z > 0; z -= 5) {
        printf("%d ", z);
    }
    printf("\n");
    #endif



    /* Return */
    return 0;
}
