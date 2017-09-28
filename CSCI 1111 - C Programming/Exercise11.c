/**
 * Joseph T. Parsons
 *
 * Simple program that demonstrates the usage of rand.
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main(void)
{
  /* Define Variables */
  int i;

  /* Seed Random. */
  srand(time(NULL));

  /* Generate Years, using 2010 + a random number between 0 and 9. */
  for(i = 0; i < 5; i++) {
    printf("Random Year (2010-2019) = %d\n", 2010 + rand()%10);
  }
  
  /* Return */
  return 0;
}
