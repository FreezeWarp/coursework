/**
 * Joseph T. Parsons
 * Modifies Exercise17.c to add the getCardValue() function, which finds the blackjack value of a card. Still includes populate and shuffle deck functions for demonstration purposes.
 * Actual source is copied and modified from Assignment6.c.
 *
 * Meanwhile, I really need to figure out how pointers work. No other language I know uses them, but they seem like they would be helpful here.
*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

/* Maps cards to their suit value, for display to the user. */
char suits [4][9] = {
  "Hearts",
  "Diamonds",
  "Clubs",
  "Spades"
};

/* Maps cards to their string value, for display to the user. */
char faceValue [13][3] = {
  "A", "2", "3", "4", "5",
  "6", "7", "8", "9", "10",
  "J", "Q", "K"
};

/* Maps cards to their value in a blackjack hand. Does not yet account for A = 1. */
int blackjackNumericValue [13] = {
  11, 2, 3, 4, 5,
  6, 7, 8, 9, 10,
  10, 10, 10
};


/* Get's a card's blackjack value.
 * Note that while a switch statement is most likely how everyone else is going to do this, I have become quite fond of array lookup for this kind-of thing.
 * It obviates the need for a function (though one can certainly still be used for more advanced purposes -- in this case, the modulus and deck referrence), is cleaner than a switch, and the array itself can be modified by other functions.
 * Which is to say, I have never seen a real-life usage of this kind-of thing where a switch would have been better than an array, and frequently observe the opposite.
 * Special logic _will_ still need to be added for Ace-as-1, of-course. But as far as I know, the most effective way would be:
 ** while (acesInHand > 0 AND handValue > 21):
 *** acesInHand--
 *** handValue-=10
 * Which would then presumably be added to a handValue function. */
int getCardValue(int deck[], int id) {
    return blackjackNumericValue[deck[id]%13];    
}


/* Generate's a sequential deck array. If cards > 52, will create multiple sequential decks.  */
void populateDeck(int deck[], int cards) {
    int i;
    
    for (i = 0; i < cards; i++) {
        deck[i] = i % 52;
    }
}


/* Randomises an already populated deck. */
/* TODO: Find deckSize from deck[]. I don't know how, yet. C doesn't have array.length or array_length(array[]). */
void shuffleDeck(int deck[], int deckSize, int repetitions) {
    int i, pickACard, thisCard;
    srand(time(NULL));
    
    for (i = 0; i < repetitions; i++) {
        pickACard = rand() % deckSize;
        
        thisCard = deck[i % deckSize];
        deck[i % deckSize] = deck[pickACard];
        deck[pickACard] = thisCard;
    }
}

int main( )
{
  int deck[104], handSize = 5, i;
  
  populateDeck(deck, 104);
  shuffleDeck(deck, 104, 104); // Shuffle size is basically arbitrary, though matching decksize is likely perfectly random. I really, really don't know, however.

  for (i = 0; i < 104; i++) {
    printf("Card = %2d %s %s (Value: %d)\n", deck[i], faceValue[deck[i]%13], suits[deck[i]/13], getCardValue(deck, i));
  }

  return 0;
}
