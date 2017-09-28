/**
 * This one... gave me a lot more trouble than I expected. There's a decent chance I did it wrong regardless (I could have easily just done a normal VLA, insisted on malloc instead).
 * In terms of instruction, I also understand why this assignment exists -- it would be tricky indeed to write the variable length mallocs without first writing explicit length mallocs.
 * And speaking of, I do have one line that will need to be changed to support more than THREE Ds. But if a input is only solicited for columns and rows, this function will fairly easily work with it.
 */

#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

#define ROWS 4
#define COLUMNS 2
#define THREEDDD 3

void printArray(int ar[ROWS][COLUMNS][THREEDDD]);
void populateArray(int ar[ROWS][COLUMNS][THREEDDD]);

int main(int argc, char *argv[ ])
{
  int (*ar)[COLUMNS][THREEDDD];

    /* Allocate the appropriate number of bytes */
    ar = (int(*)[COLUMNS][THREEDDD])malloc(sizeof(int) * ROWS * COLUMNS * THREEDDD);

    if (ar != NULL){
      populateArray(ar);
      printArray(ar);
      free(ar);
    }
    else
      printf("Malloc( ) request failed!\n");

  return 0;
}

//killmenow
// (you know, in truth, I only really give languages that I seriously like a hard-time. I can't really take the piss on, say, PHP because there's no joy in using it.)
// I honestly wonder how many of these for-loops-to-traverse-array I've written in my life. It's gotta be a good 500. Mind you, a lot of languages make it less painful with foreach (PHP) or for i in (Python), but if you're using Javascript or apparently C? It just becomes a chore.
void populateArray(int ar[ROWS][COLUMNS][THREEDDD]) {
  int r, c, t;

  for (r = 0; r < ROWS; r++)
    for (c = 0; c < COLUMNS; c++)
      for (t = 0; t < THREEDDD; t++)
        ar[r][c][t] = r * 100 + c * 10 + t; // For extra credit, write this in octal!

}

void printArray(int ar[ROWS][COLUMNS][THREEDDD])
{
  int r, c, t;

  for(r=0; r<ROWS; r++) {
    /* Headers */
    printf("           ");
    for(t=0; t<THREEDDD; t++)
      printf("  [%d][ ][%d]", r, t);
    printf("\n");


    for(c=0; c<COLUMNS; c++) {
        // Is this "future-proof." Of-course not. It's a hack that makes the code much easier to read, understand, and is still easy to modify. It's a hack, but unless you have good reason, I don't think its a hack to avoid.
        printf("ar[%d][%d] =      %03d        %03d        %03d \n", r, c, ar[r][c][0], ar[r][c][1], ar[r][c][2]);
    }
    printf("\n");
  }

  return;
}
