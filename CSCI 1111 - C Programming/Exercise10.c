/**
 * Joseph T. Parsons
 *
 * Simple program that demonstrates the usage of the math library.
 * Apparently they dropped M_E and M_PI in C99. I have no effing clue why.
 * Really, I probably shouldn't even bother targetting multiple C versions. But I'm so used to it from web development.
 */

#include <stdio.h>
#include <math.h>
#ifndef M_E
#define M_E 2.718281828459045
#endif

/**
 * Demonstration function of LOG and LOG10.
 * @param double number A number that will be used for demonstration.
 */
void mathDemo(double number)
{
    /* Calculate variables. */
    double lnCalc = log(number),
      log10Calc = log10(number);


    /* Display Calculations */
    printf("LN(%.1f):       %.3f\n", number, lnCalc);
    printf("LOG10(%.1f):    %.3f\n", number, log10Calc);
    printf("LN(%.1f)^E:     %.1f\n", number, pow(M_E, lnCalc));
    printf("LOG10(%.1f)^10: %.1f\n", number, pow(10,  log10Calc));
}


/**
 * MAIN
 */
int main(void)
{
    /* Define number. */
    double number = 75;

    /* Call function. */
    mathDemo(number);

    /* Return */
    return 0;
}
