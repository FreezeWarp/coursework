/**
 * Joseph T. Parsons
 * Reads characters into an array displays them.
 * A modification of array3.c. */

#include <stdio.h>

main( )
{
  char ar[10];
  int i;

  for(i=0; i<10; i++)
  {
    printf("Enter char %d of %d: ", i+1, 10);
    scanf("%c", &ar[i]);
    getchar(); // For the ever-annoying enter-key.
  }

  for(i=0; i<10; i++)
    printf("%c\n", ar[i]);

  return 0;
}



/*    OUTPUT: Exercise22.c

Enter char 1 of 10: 1
Enter char 2 of 10: a
Enter char 3 of 10: '
Enter char 4 of 10: /
Enter char 5 of 10: A
Enter char 6 of 10: 4
Enter char 7 of 10: \
Enter char 8 of 10: 1
Enter char 9 of 10: s
Enter char 10 of 10: m
1
a
'
/
A
4
\
1
s
m

--------------------------------
Process exited after 31.14 seconds with return value 0
Press any key to continue . . .

*/
