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
extern const int NUM_HANDS; // The number of hands to simulate.
extern const int CARDS_TOTAL; // The size of the deck, which will be held constant throughout. (A value of 104 will create two instances of every card. A value of 26 will only create hearts and diamonds, etc.)
extern const int CARDS_PER_SUIT; // Modifying this will probably break things. It's more symbolic than, uh, practical.
extern const int CARDS_PER_DECK; // Ditto.
extern const int CARD_INVALID; // Hand array terminator.
extern const int BLACKJACK_VALUE; // Ditto.
extern const int HAND_SIZE; // The maximum number of cards per hand (plus one to account for terminating value). 12 is the theoretical max -- 11*2=22 (plus terminating value). If only playing with one deck, 2+2+2+2+3+3+3+3+4=24 is the lowest possible hand, meaning a HAND_SIZE of 9+1=10 would be sufficient.


/* Prototypes */
void populateDeck(int[]);
void shuffleDeck(int[], int);
int dealCard(int[]);

int getHandValue(const int[]);

void unicodeRoutine(int, char*[]);
bool isUnicode(void);
void setUnicode(bool);
int getCardValue(int);
int getCardsValue(const int[]);
void displayCard(int);
void displayCards(const int[]);
