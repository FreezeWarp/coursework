/**
 * Joseph T. Parsons
*/


#include <stdio.h>
#include <string.h>


/* Who knows if this is better as a global. Frankly, I don't even know if this _is_ a global, or if C has globals. But whatever. */
int ticTacToeBoard[3][3] = {
    {'x', 'o', 'x'},
    {'o', 'x', 'o'},
    {'x', 'o', 'x'}
};


void printBoard() {
    int i, j;

    for (i = 0; i < 3; i++) {
        for (j = 0; j < 3; j++) {
            putchar(ticTacToeBoard[i][j]);
            if (j < 2) putchar('|');
        }
        putchar('\n');
        if (i < 2) printf("-----\n");
    }
}



int main() {
    printBoard();
}
