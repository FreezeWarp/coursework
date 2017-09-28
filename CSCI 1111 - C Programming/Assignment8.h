/* Libs */
#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <locale.h>


/* Version Detection */
#if !defined(_WIN32) || defined(__CYGWIN__)
#define WINDOWS 0
#else
#define WINDOWS 1
#endif


/* Constants */
const int CARDS_TOTAL = 52; // The size of the deck, which will be held constant throughout. (A value of 104 will create two instances of every card. A value of 26 will only create hearts and diamonds, etc.)
const int CARDS_PER_SUIT = 13; // Modifying this will probably break things. It's more symbolic than, uh, practical.
const int CARDS_PER_DECK = 52; // Ditto.
const int CARD_INVALID = -1; // Hand array terminator.
const int BLACKJACK_VALUE = 21; // Ditto.
const int HAND_SIZE = 10;


/* Prototypes */
void populateDeck(int[]);
void shuffleDeck(int[], int);
int dealCard(int[]);

int getHandValue(const int[]);

void unicodeRoutine(int, char*[]);
bool isUnicode(void);
void setUnicode(bool);
int getCardValue(int);
void displayCard(int);


/* Program Functions */
#include "Assignment8Deck.c"
#include "Assignment8Hand.c"
#include "Assignment8Card.c"
