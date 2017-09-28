/**
 * Joseph T. Parsons
 *
 * Simple program that calculates the volume of a cylinder based on inputted dimensions.
 */

#include <stdio.h>
int main(void)
{
    float radius, height, result_volume;
    const float PI = 3.14159; // Really, you _should_ just import math and use M_PI. Which, if you wanted to, could make this line "const float PI = M_PI"

    printf("This program calculates the volume of a cylinder. You will be asked to enter the radius of its face and its height.\n\n");


    /* Prompt Dimensions */
    printf("Radius? ______\b\b\b\b\b\b");
    scanf("%f", &radius);

    printf("Height? ______\b\b\b\b\b\b");
    scanf("%f", &height);


    /* Calculate Values */
    result_volume = PI * radius * radius * height;


    /* Display Calculations */
    printf("Volume: %.2f",   result_volume);


    /* Return */
    return 0;
}
