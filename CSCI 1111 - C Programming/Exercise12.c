/**
 * Joseph T. Parsons
 *
 * Simple program that calculates the area of a square based on inputted dimensions.
 */

#include <stdio.h>
int main(void)
{
    double length, width, area; // Unsigned because dimensions can't be signed.
    
    printf("This program calculates area of a square. You will be asked to enter its dimensions.\n\n");



    /* Prompt Dimensions */    
    printf("Length? ______\b\b\b\b\b\b");
    scanf("%lf", &length);

    printf("Width? ______\b\b\b\b\b\b");
    scanf("%lf", &width);
	


    /* Calculate Values */
    area = length * width;
	
		

    /* Display Calculations
     * Interstingly, this syntax (which I know from PHP) seems to compile. No idea if it's standard. */
    if (length == width)
        printf("Square Area: %lf", area);
    else
        printf("Rectangle Area: %lf", area);
        
        
        
    /* For demonstrative purposes. */
    /* As opposed to testing whether or not length equals width this (I believe) sets length equal to width,
     * then tests with length is boolean true (if non-zero) or boolean false (if zero). In other words,
     * the printf call will not execute if width is 0. */
    /* Interstingly, I get a Wparantheses error as opposed to Wall. So, uh, I don't really understand that, but cool. */
    if (length = width)
        printf("\n\nAnd now everything's a square!");
    
    
    
    /* Return */
    return 0;
}
