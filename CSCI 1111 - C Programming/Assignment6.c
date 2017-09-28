/**
 * Joseph T. Parsons
 *
 * Displays playing cards from a deck of variable size.
 *
 * Random development notes:
 * Getting Windows console to use Unicode is... difficult. It seems to be reliably possible in C++, but not in C, due to a lack of available libraries.
 * You can use system("chcp 65001") to force the console to display unicode, but it doesn't work if the console is using raster fonts (which is default).
 * I've experimented with a flag, -fU, that will force Windows to use unicode. It will execute the chcp command, but relies on the user switching the console to use non-raster fonts (like Lucida Console).
 * Additionally, while playing with this, I found that on a tested Windows system, a failed system() call returns 1. On a tested Linux system, it returns -1. A successful call on Windows returns 0. So that's silly.
 *
 * Seeing as unicode didn't exist until '91, it's not especially surprising that C89 doesn't support them. They appear to work correctly as a GNU89 extension, though one assumes this extension was added well after '89.
 * Interestingly, the WIN32 constant is defined in both GNU89 and GNU99, but not C99. _WIN32, which is I assume identical, is defined in all three.
*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>
#include <locale.h>

#ifndef _WIN32
#define _WIN32 0
#endif


/* Maps cards to their suit value, for display to the user. */
char suits [4][4] = {
    "\u2665",
    "\u2666",
    "\u2663",
    "\u2660",
};


/* Alternative suit display, for Windows. */
char suitsAlt [4][4] = {
    "H",
    "D",
    "C",
    "S"
};
bool disableUnicode = 0;


/* Maps cards to their string value, for display to the user. */
char faceValue [13][3] = {
    "A", "2", "3", "4", "5",
    "6", "7", "8", "9", "10",
    "J", "Q", "K"
};


/* Maps cards to their value in a blackjack hand. Does not yet account for A = 11. */
int blackjackNumericValue [13] = {
    1, 2, 3, 4, 5,
    6, 7, 8, 9, 10,
    10, 10, 10
};


/* Script Configuration */
const int DECKSIZE = 52; // The size of the deck, which will be held constant throughout. (A value of 104 will create two instances of every card. A value of 26 will only create hearts and diamonds, etc.)



/* Prototypes. */
int getCardValue(int[], int);
int getHandValue(int[], int, int);
void populateDeck(int[]);
void shuffleDeck(int[], int);
void displayCard(int[], int);



/* MAIN */
int main(int argc, char *argv[]) // I have no idea if I'm doing this right.
{
    int deck[DECKSIZE]; // Create an array for the deck, matching deck size.
    bool forceUnicode = false; // Unicode display; see below.
    int i; // For loops.

    
    /* Disable unicode display on Windows. I'm testing ways to support unicode when the console uses non-raster fonts, but I haven't figured out a way to detect for this.
     * (In the mean time, pass the fU flag -- which is not correctly detected, I realise -- to force unicode.) */
    if (_WIN32) {
        if (argc > 1) {
            if (strcmp(argv[1], "-fU") == 0) forceUnicode = true; // If the first argument if -fU, toggle on unicode.
        }
        
        if (forceUnicode)
            system("chcp 65001"); // If we toggle on unicode, we need to execute a command for it to work correctly on Windows.
        else
            disableUnicode = true; // Otherwise, toggle on the global $disableUnicode.
    }
    

    /* Populate the deck, then display all cards in this deck. */
    printf("======Displaying Deck======\n   ");
    populateDeck(deck);
    for (i = 0; i < DECKSIZE; i++)
        displayCard(deck, i);
    

    /* Shuffle the deck, then draw the top cards from it. */
    printf("======Drawing Cards from Shuffled Deck======\n");
    shuffleDeck(deck, 52);
    for (i = 0; i < 5; i++)
        displayCard(deck, i);
    

    /* Get the full value of the displayed hand. Still a work-in-progress; will eventually need to be passed an array of card IDs for full compatability. */
    printf("\nTotal Value = %d", getHandValue(deck, 0, 5)); // If we were further into the deck, 0, 5 would change. Right now, because we aren't actually simulating anything, we just hardcode those 5 cards.
    

    /* Return */
    return 0;
}


/* Gets the value of a card in blackjack.
 * Aces are treated as 11. */
int getCardValue(int deck[], int cardId) {
    return blackjackNumericValue[deck[cardId]%13];
}


/* Get's a hand's value (which is supposed to be a sequence of cards somewhere in the deck).
 * This assumption would not work in some games, like poker, but will generally work in, say, blackjack
 * Though this has yet account for splits -- I'm still mulling over how to handle that one. */
int getHandValue(int deck[], int start, int end) {
    int totalValue = 0, aces = 0, cardValue;

    for (start = start; start < end; start++) { // I'm honestly not sure if "start = start" is better than just "start" or even "void." But it's the only one that doesn't throw a warning of some kind.
        cardValue = getCardValue(deck, start);

        totalValue += cardValue;
        if (cardValue == 11) aces++;

    }

    while (totalValue > 21 && aces > 0) {
        totalValue -= 10;
        aces--;
    }

    return totalValue;
}


/* Generate's a sequential deck array. If cards > 52, will create multiple sequential decks.
 * If DECKSIZE % 52 != 0, then the cards used will be predictable (first aces, twos, up to kings, and first hearts, then diamonds, clubs, and then spades), and will not be random. */
void populateDeck(int deck[]) {
    int i;

    for (i = 0; i < DECKSIZE; i++)
        deck[i] = i % 52;
}


/* Randomises an already populated deck.
 * Shuffle repetitions is basically arbitrary, though matching DECKSIZE is likely perfectly random. I would omit it entirely, but I feel it better simulates real life, which could be fun in some circumstances.
 */
void shuffleDeck(int deck[], int repetitions) {
    int i, pickACard, thisCard;
    srand(time(NULL));

    for (i = 0; i < repetitions; i++) {
        pickACard = rand() % DECKSIZE;

        thisCard = deck[i % DECKSIZE];
        deck[i % DECKSIZE] = deck[pickACard];
        deck[pickACard] = thisCard;
    }
}


/* Displays a card.
 * Will use unicode if enabled, otherwise will use non-unicode (resulting in what appears to be crooked cards). Regardless, cards will have the same dimensions - 5x3.
 */
void displayCard(int deck[], int cardId) {
    printf(" ___ \n");
    printf("|%s%s%s|\n",
      faceValue[deck[cardId]%13],
      strlen(faceValue[deck[cardId]%13]) == 1 ? " " : "", // If the face value is two characters (that is, "10"), then don't pad. For everything else, pad.
      disableUnicode ? suitsAlt[deck[cardId]/13] : suits[deck[cardId]/13]);

    if (disableUnicode) printf("-----\n");
    else printf(" \u203e\u203e\u203e\n");
}
