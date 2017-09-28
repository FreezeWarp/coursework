/* Libs
 * I like 'em! */
#include <stdio.h> // Input/output
#include <stdlib.h> // rand()
#include <string.h> // memset
#include <time.h> // Seed for rand()
#include <locale.h> // Unicode support.
#include <assert.h> // Sanity checking in some places.
#include <stdbool.h> // Bool constants
#include <ctype.h> // toupper


/* (Mostly) Symboilic Constants */
#if !defined(ASSIGNMENT_10_h)
    #define ASSIGNMENT_10_h
    #define HAND_SIZE 12 // The maximum number of cards per hand (plus one to account for terminating value). 12 is the theoretical max -- 11*2=22 (plus terminating value). If only playing with one deck, 2+2+2+2+3+3+3+3+4=24 is the lowest possible hand, meaning a HAND_SIZE of 9+1=10 would be sufficient.
    #define CARDS_TOTAL 52 // The size of the deck, which will be held constant throughout. (A value of 104 will create two instances of every card. A value of 26 will only create hearts and diamonds, etc.)
    #define CARDS_PER_SUIT 13
    #define CARDS_PER_DECK 52
    #define CARD_INVALID -1
    #define BLACKJACK_VALUE 21
    #define ACE_VALUE 11
    #define DEALER_LIMIT 17
    #define MULT_BLACKJACK 1.5
    #define MULT_DOUBLED 2
    #define MULT_SURRENDER .5
#endif


/* Windows Version Detection */
#if !defined(WINDOWS)
    #if !defined(_WIN32) || defined(__CYGWIN__)
        #define WINDOWS 0
        #include <unistd.h> // sleep()
        #define wait(t) usleep(t * 1000 * 1000);
    #else
        #define WINDOWS 1
        #include <Windows.h> // sleep()
        #define wait(t) Sleep(t * 1000);
    #endif
#endif


/* Typedefs */
typedef enum PlayerAction {HIT, STAY, DOUBLED, SURRENDER, SPLIT} PlayerAction;
typedef int Card;
typedef Card DeckArray[CARDS_TOTAL];
typedef Card HandArray[HAND_SIZE];

// Linked list of hands. Designed to be passed as a deck array with a resolution function, e.g. seekHand($hands, x) returns the cards entry in the xth linked Hands struct.
typedef struct Hands {
    unsigned int bet; // TODO: Support per-hand bets.
    unsigned int incrementor; // An incrementor that can be used to keep track of how far we've searched into HandArray.
    HandArray cards;
    struct Hands *next; // Pointer to next element in list.
    PlayerAction state; // Set to DOUBLE or SURRENDER if the player has choosen one of these actions. Others are ignored.
} Hands;


/* Prototypes */
// Main
float getBetMult(const Hands*);
int playBall(int, int, DeckArray, int, bool, float);
void storeData(const char*, int);
int readData(const char*);

// Assignment10Deck.c
void populateDeck(DeckArray);
void shuffleDeck(DeckArray, int);
int dealCard(DeckArray);

// Assignment10Hand.c
int getHandValue(const HandArray);
Hands* createHands(unsigned int);
Hands* appendHand(Hands*);
void cleanHands(Hands*);
Hands* seekHandArray(Hands*, unsigned int);
void displayHand2(Hands*, unsigned int, bool);

// Assignment10Card.c
void unicodeRoutine(int, char*[]);
bool isUnicode(void);
void setUnicode(bool);
int getHandArraySize(const HandArray);
int getCardValue(Card);
int getCardsValue(const HandArray);
void displayCard(Card);
void displayCards(const HandArray, bool);

// Assignment10Menus.c
bool showMenuCanSurrender(HandArray);
bool showMenuCanSplit(HandArray);
PlayerAction showMenu(HandArray);
int showBetMenu(int, int);
char showInsuranceMenu();


/* Shorthand Macros That Assume:
 ** hands is a Hands* variable storing the a linked list head.
 ** l is the integer corresponding with the current hand.
 * If these assumptions are not met, these macros will not work.
 */
#define getHandState(i) seekHand(hands, i)->state // gets the state paramater of hand i
#define getHandInc(i) seekHand(hands, i)->incrementor // gets the incrementor value of hand i
#define getHandArray(i) seekHand(hands, i)->cards // gets the HandArray belonging to hand i
#define getHandValue(i) getCardsValue(getHandArray(i)) // get the value of the HandArray belong to hand i
#define addCardToHand(i) getHandArray(i)[getHandInc(i)++] = dealCard(deck); // adds a new card to hand i
#define displayHand(i) displayHand2(hands, i, false) // displays hand i, with heading and cards
#define displayDealer(showDealer) displayHand2(hands, 0, showDealer) // displays the dealer's hand (0), choosing whether to show or hide its first card.



/* Assignment 10 Included Files */
#include "Assignment10Deck.c"
#include "Assignment10Hand.c"
#include "Assignment10Card.c"
#include "Assignment10Menu.c"
