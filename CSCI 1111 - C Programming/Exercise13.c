/**
 * Joseph T. Parsons
 * Demonstrates usage of switch. Modifcation of switch.c from instructor's notes.
 */

#include <stdio.h>
#define PI 3.1415

int main( )
{
  int ch = 0, height, width, radius;

  /* Loop to quit on upper or lower case Q */
  while (ch != 'q' && ch != 'Q') {
    /* Display menu of choices */
    printf("R- Rectangle\n");
    printf("S- Square\n");
    printf("C- Circle\n");

    printf("\nQ- to quit\n");
    printf("\nChoice: ");

    ch = getchar( );


    /* Process Choice */
    switch(ch) {
      case 'r': case 'R':
        printf("Height? ");
        scanf("%d", &height);
        
        printf("Width? ");
        scanf("%d", &width);
        
        printf("Rectangle Area: %d\n\n", height * width);
        break;
        
      case 's': case 'S':
        printf("Height? ");
        scanf("%d", &height);

        printf("Square Area: %d\n\n", height * height);
        break;
         
      case 'c': case 'C':
        printf("Radius? ");
        scanf("%d", &radius);

        printf("Circle Area: %.2f\n\n", (float) radius * radius * PI);
        break;
        
      case 'q': case 'Q': // Valid, but will end the loop on next cycle.
        break;

         
      default:
         printf("Invalid choice- '%c'.\n", ch);
         break;
    }

    getchar(); //strip trailing newline
  }

  return 0;
}

