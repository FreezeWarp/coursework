/*     FILE: arith_3.c     */

/* More arithmetic operators with nicer output */
/* Joseph T. Parsons Changes:
 ** Input first and second numbers.
 ** Display division as floating point, because how can you _not_ do that?
 ** Place calculations in different variables instead of just overwriting sum.
 ** Added second quotient. */

#include <stdio.h>

int main( )
{
  int first, second;
  
  
  /* Prompt Inputs */
  printf("First Number? ______\b\b\b\b\b\b");
  scanf("%d", &first);
  
  printf("Second Number? ______\b\b\b\b\b\b");
  scanf("%d", &second);


  /* Calculations */
  int sum = first + second,
    difference1 = first - second,
    difference2 = second - first,
    product = first * second;
  float quotient1 = (float) first / (float) second,
    quotient2 = (float) second / (float) first;
  
  
  /* Output */
  printf("%d + %d = %d\n",   first,  second, sum);
  printf("%d - %d = %d\n",   first,  second, difference1);
  printf("%d - %d = %d\n",   second, first,  difference2);
  printf("%d * %d = %d\n",   first,  second, product);
  printf("%d / %d = %.2f\n", first,  second, quotient1);
  printf("%d / %d = %.2f\n", second, first,  quotient2);


  /* Return */
  return 0;
}
