/**
 * Joseph T. Parsons
 * Demonstrates usage of switch and pointers. Modification of Exercise13.c.
 */

#include <stdio.h>
#define PI 3.1415

int rectangleArea(int *width, int *height) {
    return *width * *height;
}

double triangleArea(int *width, int *height) {
    return .5 * rectangleArea(width, height); // Truthfully, I'm not sure if this is really good practice. But it makes sense.
}

double circleArea(int *radius) {
    return PI * *radius * *radius;
}

int main( )
{
  int ch = 0, height, width, radius;

  /* Loop to quit on upper or lower case Q */
  while (ch != 'q' && ch != 'Q') {
    /* Display menu of choices */
    printf("R- Rectangle\n");
    printf("S- Square\n");
    printf("T- Triangle\n");
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

        printf("Rectangle Area: %d\n\n", rectangleArea(&width, &height));
        break;

      case 's': case 'S':
        printf("Height? ");
        scanf("%d", &height);

        printf("Square Area: %d\n\n", rectangleArea(&height, &height));
        break;

      case 't': case 'T':
        printf("Height? ");
        scanf("%d", &height);

        printf("Width? ");
        scanf("%d", &width);

        printf("Triangle Area: %.1f\n\n", triangleArea(&width, &height));
        break;

      case 'c': case 'C':
        printf("Radius? ");
        scanf("%d", &radius);

        printf("Circle Area: %.2f\n\n", circleArea(&radius));
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
