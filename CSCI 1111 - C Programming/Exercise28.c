/**
 * Joseph T. Parsons
 * Tic Tac Toe and stuff.
*/


#include <stdio.h>
#include <string.h>


const short X = 1;
const short O = 2;
const short EMPTY = 0;

short ticTacToeBoard[3][3]; // Who knows if this is better as a global. Frankly, I don't even know if this _is_ a global, or if C has globals. But whatever.

/* Coordinates that equal "rows" -- that is, the three horizontal rows, the three vertical rows, and the two diagonal rows. A formula could do this instead, but I fancy look-up tables personally. */
short winningRows[8][3][2] = {
    {{0,0},{0,1},{0,2}},
    {{1,0},{1,1},{1,2}},
    {{2,0},{2,1},{2,2}},
    {{0,0},{1,0},{2,0}},
    {{0,1},{1,1},{2,1}},
    {{0,2},{1,2},{2,2}},
    {{0,0},{1,1},{2,2}},
    {{0,2},{1,1},{2,0}}
};



/**
 * Clear Board
 */
void setBoardEmpty(short board[3][3]) {
    int i, j;

    for (i = 0; i < 3; i++) {
        for (j = 0; j < 3; j++) {
            board[i][j] = EMPTY;
        }
    }
}


/**
 * Set a cell in the board.
 * Top-left is X=0, Y=0. Bottom-right is X=2, Y=2.
 */
void setBoardCell(int x, int y, short charValue, short board[3][3]) {
    board[y][x] = charValue;
}


/**
 * Checks for a winner.
 * Not checked for bugs. Probably doesn't work.
 */
void checkWinner(short board[3][3]) {
   int i, j;
   short tests[2] = {X, O};

   for (i = 0; i < 8; i++) {
       for (j = 0; j < 2; j++) { // for j in [X, O], foreach([X, O]) as test -- really, every other language has a better way for this.
           if (board[winningRows[i][0][0]][winningRows[i][0][1]] == tests[j]
            && board[winningRows[i][1][0]][winningRows[i][1][1]] == tests[j]
            && board[winningRows[i][2][0]][winningRows[i][2][1]] == tests[j])
                printf("%s wins.\n", (tests[j] == X ? "x" : "o"));
        }
   }
}


/**
 * Print a character, interpretting its decimal value.
 */
void printChar(short charValue) {
    if (charValue == X) putchar('x');
    else if (charValue == O) putchar('o');
    else putchar(' ');
}


/**
 * Prints the entire board, with special formatting.
 */
void printBoard(short board[3][3]) {
    int i, j;

    for (i = 0; i < 3; i++) {
        for (j = 0; j < 3; j++) {
            printChar(board[i][j]);
            if (j < 2) putchar('|');
        }
        putchar('\n');
        if (i < 2) printf("-----\n");
    }
}



int main() {
    short (*ptr)[3] = ticTacToeBoard; // ptr and ticTacToeBoard are now interchangable.
    
    /* Empty Board */
    setBoardEmpty(ptr);

    printf("Empty Board:\n");
    printBoard(ptr);


    /* Sim 3 Turns */
    setBoardCell(1, 1, X, ptr);
    setBoardCell(0, 0, O, ptr);
    setBoardCell(0, 1, X, ptr);

    printf("\nBoard After Three Moves:\n");
    printBoard(ptr);


    /* Sim 3 Turns */
    setBoardCell(2, 1, O, ptr);
    setBoardCell(1, 0, X, ptr);
    setBoardCell(2, 2, O, ptr);

    printf("\nBoard After Six Moves:\n");
    printBoard(ptr);
    checkWinner(ptr);


    /* Sim 3 Turns */
    setBoardCell(0, 2, X, ptr);
    setBoardCell(2, 0, O, ptr);
    //setBoardCell(1, 2, X, ticTacToeBoard);

    printf("\nBoard After Eight Moves:\n");
    printBoard(ptr);
    checkWinner(ptr);



    return 0;
}
