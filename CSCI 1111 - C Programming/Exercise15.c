/**
 * Joseph T. Parsons
 *
 * Simple program that calculates the volume of a cylinder based on inputted dimensions.
 */

#include <stdio.h>

/**
 * Modified version of get_long() from CPlusPrimer/Ch8/checking.c
*/
int get_int(void)
{
    int input;
    char ch;

    while (scanf("%d", &input) != 1)
    {
        while ((ch = getchar()) != '\n') // dispose of bad input
            putchar(ch);
        printf(" is not an integer.\n");
        printf("Please enter an integer value, such as 25, -178, or 3: ");
    }
    
    while ((ch = getchar()) != '\n') // dispose of bad input
        continue;

    return input;
}


/**
 * MAIN */
int main(void)
{
    int radius, height;
    double result_volume;
    const double PI = 3.14159; // Really, you _should_ just import math and use M_PI. Which, if you wanted to, could make this line "const float PI = M_PI"


    printf("This program calculates the volume of a cylinder. You will be asked to enter the radius of its face and its height.\n\n");


    /* Prompt Dimensions */
    printf("Radius? ______\b\b\b\b\b\b");
    radius = get_int();

    printf("Height? ______\b\b\b\b\b\b");
    height = get_int();


    /* Calculate Values */
    result_volume = PI * radius * radius * height;


    /* Display Calculations */
    printf("Volume: %.2lf",   result_volume);


    /* Return */
    return 0;
}
