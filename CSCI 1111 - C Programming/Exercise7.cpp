/**
 * Simple program that calculates the volume of a cylinder based on inputted dimensions.
 * A modification of exercise 5 to use a function for cylinder volume.
 *
 * @author Joseph T. Parsons <josephtparsons@gmail.com>
 */

#include <stdio.h>


/* Function Declarations (Defined Later) */
float cylinderVolume(float, float);


/* Main Function */
int main(void)
{
    float radius, height;
    
    printf("This program calculates the volume of a cylinder. You will be asked to enter the radius of its face and its height.\n\n");
    
    
    /* Prompt Dimensions */
    printf("Radius? ______\b\b\b\b\b\b");
    scanf("%f", &radius);

    printf("Height? ______\b\b\b\b\b\b");
    scanf("%f", &height);
    
    
    /* Generate Output */
    printf("Volume: %.2f", cylinderVolume(height, radius));
    
    
    /* Return */
    return 0;
}


/**
 * Calculates Cylinder Volume
 *
 * @param float height The height of the cylinder.
 * @param float radius The radius of the cylinder's face.
 *
 * @return float The calculated volume for the cylinder.
*/
float cylinderVolume(float height, float radius) {
    /* Define Constants */
    const float PI = 3.14159; // Really, you _should_ just import math and use M_PI. Which, if you wanted to, could make this line "const float PI = M_PI"

    /* Result Calculation */
    float result_volume = PI * radius * radius * height;

    /* Return */
    return result_volume;
}
