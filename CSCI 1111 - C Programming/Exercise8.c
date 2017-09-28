/**
 * Joseph T. Parsons
 *
 * Simple program that calculates the quadratic discriminant, as well as the full quadratic formula if no imaginary numbers are involved.
 */

#include <stdio.h>
#include <math.h>

double discriminant(double a, double b, double c) {
    return b*b - 4*a*c;
}

int main(void)
{
    double a, b, c, discriminantResult, value1, value2;
    
    printf("This program calculates the discriminant for the quadratic formula, equal to b^2 - 4ac. You will be asked for each of these values. Negatives and decimals are allowed.\n\n");


    /* Prompt Dimensions */    
    printf("a? ______\b\b\b\b\b\b");
    scanf("%lf", &a);
    
    printf("b? ______\b\b\b\b\b\b");
    scanf("%lf", &b);
    
    printf("c? ______\b\b\b\b\b\b");
    scanf("%lf", &c);


    /* Calculate and Display Discriminant */
    discriminantResult = discriminant(a, b, c);            

    printf("\nDiscriminant: %.2f\n", discriminantResult);


    /* Calculate and Display Values, if the Discriminant > 0 */
    if (2*a != 0 && discriminantResult > 0) {
        value1 = (-b + sqrt(discriminantResult)) / (2*a),
          value2 = (-b - sqrt(discriminantResult)) / (2*a);
        
        printf("\nValue 1: %.2f\n", value1);
        printf("Value 2: %.2f\n", value2);
    }
    
    
    /* Return */
    return 0;
}
