/**
 * Oy. Vey.
 * This was an experience. I cobbled together an initial version of this quickly, but then took a shot at modifying it here to, theoretically, support user input. The result is interesting.
 * It works. Frankly, I see why pointers are important now. I'm not yet keen on that whole malloc character, but it is nice to have low-level control of memory.
 * Ultimately, I'm not entirely sure _why_ it works. Each array is a three-dimensional list of pointers, and is referrenced by using pointer notation. That much is fairly obvious. But why they interact the way they do... well, I'm sure I'll figure it out eventually.
 */

#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

void printArray(int ***ar, int ROWS, int COLUMNS, int THREEDDD);
void populateArray(int ***ar, int ROWS, int COLUMNS, int THREEDDD);

int main(int argc, char *argv[ ])
{
  int i, j;
  int ROWS = 4;
  int COLUMNS = 2;
  int THREEDDD = 3;
  int ***ar;

  printf("Rows?: ");
  scanf("%d", &ROWS);
  printf("\nColumns?: ");
  scanf("%d", &COLUMNS);
  printf("\nTHREE Ds?: ");
  scanf("%d", &THREEDDD);
  
  /* I have no idea. I really, really don't. I _tried_ malloc(sizeof(int**) * ROWS * COLUMNS * THREEDDD). It didn't work. */
  ar = malloc(sizeof(int**) * ROWS);
  for (i = 0; i < ROWS; i++) {
     ar[i] = malloc(sizeof(int*) * COLUMNS);
     for (j = 0; j < COLUMNS; j++) {
       ar[i][j] = malloc(sizeof(int) * THREEDDD);
     }
  }

  if (ar != NULL){
    populateArray(ar, ROWS, COLUMNS, THREEDDD);
    printArray(ar, ROWS, COLUMNS, THREEDDD);
    free(ar);
  }
  else
    printf("Malloc( ) request failed!\n");

  return 0;
}


//killmenow
// I honestly wonder how many of these for-loops-to-traverse-array I've written in my life. It's gotta be a good 500. Mind you, a lot of languages make it less painful with foreach (PHP) or for i in (Python), but if you're using Javascript or apparently C? It just becomes a chore.
void populateArray(int ***ar, int ROWS, int COLUMNS, int THREEDDD) {
  int r, c, t;

  for (r = 0; r < ROWS; r++)
    for (c = 0; c < COLUMNS; c++)
      for (t = 0; t < THREEDDD; t++)
        *(*(*(ar + r) + c) + t) = r * 100 + c * 10 + t; // For extra credit, write this in octal!

}

void printArray(int ***ar, int ROWS, int COLUMNS, int THREEDDD)
{
  int r, c, t;

  for(r=0; r<ROWS; r++) {
    /* Headers */
    printf("           ");
    for(t=0; t<THREEDDD; t++)
      printf("  [%d][ ][%d]", r, t);
    printf("\n");


    for(c=0; c<COLUMNS; c++) {
        printf("ar[%d][%d] =      ", r, c);
        for (t = 0; t < THREEDDD; t++) printf("%03d        ", *(*(*(ar + r) + c) + t));
        printf("\n");
    }
    printf("\n");
  }

  return;
}
