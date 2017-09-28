/**
 * Joseph T. Parsons
 * Demonstrates usage of loops.
 */

#include <stdio.h>

void prompt() {
    printf("Please enter an integer (any letter to quit): ");
}

int main( )
{
  int integer, counter = 0, accumulator = 0, maxValue, minValue;

  prompt();

  /* Loop to quit on empty input. */
  while (scanf("%d", &integer) > 0) {

    /* Update Calculations */
    counter++;
    accumulator += integer;

    // The counter test is pretty dirty, I realise. But it works.
    // Basically, if this is the first run of the loop, automatically set both max and minValue equal to the first integer value).
    if (counter == 1 || integer > maxValue) maxValue = integer;
    if (counter == 1 || integer < minValue) minValue = integer;


    /* Display Values */
    printf("\nTotal Inputs: %d", counter);
    printf("\nSum: %d", accumulator);
    printf("\nAverage: %.3f", (float)accumulator / counter);
    printf("\nMax Value: %d", maxValue);
    printf("\nMin Value: %d\n\n", minValue);

    prompt();

  }

  return 0;
}

