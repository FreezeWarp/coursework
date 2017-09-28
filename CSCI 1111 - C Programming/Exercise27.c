/**
 * Joseph T. Parsons
 * Tic Tac Toe and stuff.
*/


#define smallint short // Was curious if this works. It does, will remove it in future iterations of this assignment. But I'm gonna keep it here.
#include <stdio.h>
#include <string.h>


const smallint X = 1;
const smallint O = 2;
const smallint EMPTY = 0;

smallint ticTacToeBoard[3][3]; // Who knows if this is better as a global. Frankly, I don't even know if this _is_ a global, or if C has globals. But whatever.

/* Coordinates that equal "rows" -- that is, the three horizontal rows, the three vertical rows, and the two diagonal rows. A formula could do this instead, but I fancy look-up tables personally. */
smallint winningRows[8][3][2] = {
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
void setBoardEmpty(smallint board[3][3]) {
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
void setBoardCell(int x, int y, smallint charValue, smallint board[3][3]) {
    board[y][x] = charValue;
}


/**
 * Checks for a winner.
 * Not checked for bugs. Probably doesn't work.
 */
void checkWinner(smallint board[3][3]) {
   int i, j;
   smallint tests[2] = {X, O};
   
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
void printChar(smallint charValue) {
    if (charValue == X) putchar('x');
    else if (charValue == O) putchar('o');
    else putchar(' ');
}


/**
 * Prints the entire board, with special formatting.
 */
void printBoard(smallint board[3][3]) {
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
    /* Empty Board */
    setBoardEmpty(ticTacToeBoard);

    printf("Empty Board:\n");
    printBoard(ticTacToeBoard);

    
    /* Sim 3 Turns */
    setBoardCell(1, 1, X, ticTacToeBoard);
    setBoardCell(0, 0, O, ticTacToeBoard);
    setBoardCell(0, 1, X, ticTacToeBoard);
    
    printf("\nBoard After Three Moves:\n");
    printBoard(ticTacToeBoard);


    /* Sim 3 Turns */
    setBoardCell(2, 1, O, ticTacToeBoard);
    setBoardCell(1, 0, X, ticTacToeBoard);
    setBoardCell(2, 2, O, ticTacToeBoard);

    printf("\nBoard After Six Moves:\n");
    printBoard(ticTacToeBoard);
    checkWinner(ticTacToeBoard);


    /* Sim 3 Turns */
    setBoardCell(0, 2, X, ticTacToeBoard);
    setBoardCell(2, 0, O, ticTacToeBoard);
    //setBoardCell(1, 2, X, ticTacToeBoard);

    printf("\nBoard After Eight Moves:\n");
    printBoard(ticTacToeBoard);
    checkWinner(ticTacToeBoard);

    
    
    return 0;
}
