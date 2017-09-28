/* A function that determines whether x has the factor y.
 * Used stdbool for true, false, and bool. Oddly, this works in C89, but it's not supposed to be part of the standard until C99. So I'm not sure what's going on. */

#include <stdio.h>
#include <stdbool.h>

bool isFactor(int, int);     /*   Parameters: 2 ints
                                 Return value: a bool */

int main( )
{
  int integer, factor;


  printf("Enter integer: ");
  scanf("%d", &integer);

  printf("Enter factor: ");
  scanf("%d", &factor);


  if (isFactor(integer, factor))
    printf("%d is a factor of %d\n", factor, integer);
  else
    printf("%d is not a factor of %d\n", factor, integer);


  return 0;
}


bool isFactor(int integer, int factor)
{
  return (integer % factor == 0) ? true : false; // Alternative, just !(integer%factor) or (integer%factor != 0). But this way is more obvious.
}
