/**
 * Joseph T. Parsons
 *
 * Displays playing cards from a deck of variable size.
 *
 * Random development notes:
 * I do have some trouble with symbolic constants. There exists a school of thought that using an integer with any meaning, what-so-ever, is a no-no, and should be put into a named constant.
 * I, frankly, have some reservations about this. Sometimes integers are just easier to read. I considered changing 11 to ACE_VALUE, but this should be obvious.
 * Likewise, I really don't think 21 should be set to a constant, but I did anyway, because why take a class if not to try new things?
 *
 * There are a couple of different ways one may wish to keep track of which cards are dealt from a shuffled deck, four of which come to mind:
 ** First, one may choose to set the value of a dealt card, card[i], to an invalid constant like -1. To deal the next card, one must search the array for the first valid value, which is slow.
 ** Second, one may choose to rebuild the array entirely, such that on deal card[0] is removed, and card[i+1] becomes card[i]. To deal, one simply gets card[0]. But the rebuild is even slower.
 ** Third, one could have a bitfield such that $bitField & (2^cardNumber) is set true once a card is dealt. This field would be really super big (storable only by a long long), but is more efficient than using an array for the same purpose. One would then try randoms until a card is not considered "dealt."
 ** Fourth, one can simply have an integer keep track of where in a suffle deck we are. This is insanely fast, mimics real-life, and allows us to easily figure out which cards have already been dealt or are on the table.
 * Interestingly, the second doesn't require the deck to be pre-shuffled, since one can instead query card[randInt()%remainingCards], rebuilding the array without this card on deal. But it is also the slowest.
 * Well, in theory, the same is true of the first and third methods, insofar as one can keep trying randoms from a shuffled deck until a valid card is selected. But at the end of this deck, this requires too many randoms (up to 51 for the very last draw) to be efficient. Simulating multiple decks only makes this issue worse.
 *
 * As before, you can use the -fU flag to force unicode on a Windows environment (but you must be using non-raster fonts). Likewise, the script itself should work just fine with 2, 4, or a 20,000 decks (just change CARDS_TOTAL).
 * That said, Linux support of Unicode was not explicitly tested here. I think it will work. Dunno for sure.
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
const char suits [4][4] = {
    "\u2665",
    "\u2666",
    "\u2663",
    "\u2660",
};


/* Alternative suit display, for Windows. */
const char suitsAlt [4][4] = {
    "H",
    "D",
    "C",
    "S"
};
bool enableUnicode = true;


/* Maps cards to their string value, for display to the user. */
const char faceValue [13][3] = {
    "A", "2", "3", "4", "5",
    "6", "7", "8", "9", "10",
    "J", "Q", "K"
};


/* Maps cards to their value in a blackjack hand. Does not yet account for A = 11. */
const int blackjackNumericValue [13] = {
    1, 2, 3, 4, 5,
    6, 7, 8, 9, 10,
    10, 10, 10
};


/* Script Configuration */
const int CARDS_TOTAL = 52; // The size of the deck, which will be held constant throughout. (A value of 104 will create two instances of every card. A value of 26 will only create hearts and diamonds, etc.)
const int CARDS_PER_SUIT = 13; // Modifying this will probably break things. It's more symbolic than, uh, practical.
const int CARDS_PER_DECK = 52; // Ditto.
const int BLACKJACK_VALUE = 21; // Ditto.


/* Deck Position (used to keep track of which cards have been dealt and which are still in the deck -- effectively, cards before are in play or in garbage, cards after are in deck). */
int deckPosition = 0;



/* Prototypes. */
int getDeckPosition(const int[]);
int setDeckPosition(const int[], int);
bool isUnicodeOn(void);
void setUnicode(bool);
void unicodeRoutine(int, char*[]);
int getCardValue(const int[], int);
int getHandValue(const int[], int, int);
void populateDeck(int[]);
void shuffleDeck(int[], int);
void displayCard(const int[], int);
int dealCard(int[]);



/* MAIN */
int main(int argc, char *argv[]) // I have no idea if I'm doing this right.
{
    int deck[CARDS_TOTAL]; // Create an array for the deck, matching deck size.
    int i; // For loops.
    
    
    /* Windows Unicode Support */
    unicodeRoutine(argc, argv);


    /* Populate the deck, then display all cards in this deck. */
    printf("======Displaying Deck======\n");
    populateDeck(deck);
    for (i = 0; i < CARDS_TOTAL; i++)
        displayCard(deck, i);


    /* Shuffle the deck, then draw the top cards from it. */
    printf("======Drawing Cards from Shuffled Deck======\n");
    shuffleDeck(deck, CARDS_TOTAL);
    for (i = 0; i < 5; i++)
        displayCard(deck, dealCard(deck));

    // Get the full value of the displayed hand. Still a work-in-progress; will eventually need to be passed an array of card IDs for full compatability.
    // If we were further into the deck, 0, 5 would change. Right now, because we aren't actually simulating anything, we just hardcode those 5 cards.
    printf("\nTotal Value = %d\n", getHandValue(deck, 0, 5));


    /* Display Remainder of Deck */
    printf("======Displaying Remainder of Deck======\n");
    for (i = getDeckPosition(deck); i < CARDS_TOTAL; i++)
        displayCard(deck, i);


    /* Return */
    return 0;
}



/**
 * Dummy functions for future use.
 */
int getDeckPosition(const int deck[]) {
    return deckPosition;
}
int setDeckPosition(const int deck[], int position) {
    return deckPosition = position;
}
bool isUnicodeOn() {
    return enableUnicode;
}
void setUnicode(bool state) {
    enableUnicode = state;
}



/**
 * Enables unicode on Windows with the -fU flag.
 */
void unicodeRoutine(int argc, char *argv[]) {
    /* Disable unicode display on Windows. I'm testing ways to support unicode when the console uses non-raster fonts, but I haven't figured out a way to detect for this.
     * (In the mean time, pass the fU flag -- which is not correctly detected, I realise -- to force unicode.) */
    bool forceUnicode = false; // Unicode display; see below.

    if (_WIN32) {
        if (argc > 1) {
            if (strcmp(argv[1], "-fU") == 0) forceUnicode = true; // If the first argument if -fU, toggle on unicode.
        }

        if (forceUnicode) {
            setUnicode(true);
            system("chcp 65001"); // If we toggle on unicode, we need to execute a command for it to work correctly on Windows.
        }
        else {
            setUnicode(false); // Otherwise, turn it off.
        }
    }
    else {
        setUnicode(true);
    }
}



/**
 * Gets the value of a card in blackjack.
 * Aces are treated as 11.
 */
int getCardValue(const int deck[], int cardId) {
    return blackjackNumericValue[deck[cardId]%13];
}



/**
 * Get's a hand's value (which is supposed to be a sequence of cards somewhere in the deck).
 * This assumption would not work in some games, like poker, but will generally work in, say, blackjack
 * Though this has yet account for splits -- I'm still mulling over how to handle that one.
 */
int getHandValue(const int deck[], int start, int end) {
    int totalValue = 0, aces = 0, cardValue;

    for (start = start; start < end; start++) { // I'm honestly not sure if "start = start" is better than just "start" or even "void." But it's the only one that doesn't throw a warning of some kind.
        cardValue = getCardValue(deck, start);

        totalValue += cardValue;
        if (cardValue == 11) aces++;

    }

    while (totalValue > BLACKJACK_VALUE && aces > 0) {
        totalValue -= 10;
        aces--;
    }

    return totalValue;
}



/**
 * Generate's a sequential deck array. If cards > 52, will create multiple sequential decks.
 * If CARDS_TOTAL % CARDS_PER_DECK != 0, then the cards used will be predictable (first aces, twos, up to kings, and first hearts, then diamonds, clubs, and then spades), and will not be random.
 */
void populateDeck(int deck[]) {
    int i;

    for (i = 0; i < CARDS_TOTAL; i++)
        deck[i] = i % CARDS_PER_DECK;
}



/**
 * Randomises an already populated deck.
 * Shuffle repetitions is basically arbitrary, though matching CARDS_TOTAL is likely perfectly random. I would omit it entirely, but I feel it better simulates real life, which could be fun in some circumstances.
 */
void shuffleDeck(int deck[], int repetitions) {
    int i, pickACard, thisCard;
    srand(time(NULL));

    for (i = 0; i < repetitions; i++) {
        pickACard = rand() % CARDS_TOTAL;

        thisCard = deck[i % CARDS_TOTAL];
        deck[i % CARDS_TOTAL] = deck[pickACard];
        deck[pickACard] = thisCard;
    }
    
    setDeckPosition(deck, 0); // Reset the deck position.
}



/**
 * Displays a card.
 * Will use unicode if enabled, otherwise will use non-unicode (resulting in what appears to be crooked cards). Regardless, cards will have the same dimensions - 5x3.
 */
void displayCard(const int deck[], int cardId) {
    printf(" ___ \n");
    printf("|%s%s%s|\n",
      faceValue[deck[cardId]%CARDS_PER_SUIT],
      strlen(faceValue[deck[cardId]%CARDS_PER_SUIT]) == 1 ? " " : "", // If the face value is two characters (that is, "10"), then don't pad. For everything else, pad.
      isUnicodeOn() ? suits[deck[cardId]/CARDS_PER_SUIT] : suitsAlt[deck[cardId]/CARDS_PER_SUIT]);

    if (isUnicodeOn()) printf(" \u203e\u203e\u203e\n");
    else printf("-----\n");
}



/**
 * "Deals" a card by returning a new, randomised card ID.
 */
int dealCard(int deck[]) {
    if (deckPosition >= CARDS_TOTAL) { // If the current deck position is out-of-bounds, shuffle the deck (which resets its position to 0).
        shuffleDeck(deck, CARDS_TOTAL);
    }
    
    return setDeckPosition(deck, getDeckPosition(deck) + 1) - 1; // Set deck position to current deck position plus one, then return previous deck position as the dealt card's value.
}
