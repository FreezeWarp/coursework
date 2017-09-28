/**
 * Joseph T. Parsons
 *
 * Displays playing cards from a deck of variable size.
 *
 * Dev Notes:
 *
 * DisplayCard() is now relegated to debug purposes. It will continue to work, but DisplayCards() is used for most purposes. It should be fairly obvious why.
 *
 * Number of hands simulated can be easily changed as with everything else here. I used 10 a lot to get a lot of test data at once.
 ** Part of the motivation here was to support splits. If it's not part of the final assignment spec, I might just ignore them, but they should be possible with only a little bit of extra work.
 ** (In effect, the assignment would be built with the expectation of three hands -- one for the dealer, two for the user to play simultaneously. Whenever the user splits a hand, a new hand is created and simulated as well. We could have the user only have one hand to start, but without extensive testing of multiple, it would be more or less impossible to correctly implement splits.)
 *
 * Hand.c has been merged into Card.c.
*/

#include "Assignment9.h"

/* MAIN */
int main(int argc, char *argv[])
{
    int deck[CARDS_TOTAL]; // Create an array for the deck, matching deck size.
    int (*hands)[HAND_SIZE] = (int(*)[HAND_SIZE])malloc(sizeof(int) * NUM_HANDS * HAND_SIZE); // Create an array to store hands, matching the number of hands and the max size of each hand.
    int *handsIncrementer = (int(*))calloc(NUM_HANDS, sizeof(int)); // Create an incrementer for each hand for drawing. This could be replaced with a function call getNextHandPosition() returning the first index which stores CARD_INVALID. This is also faster.
    int k, l; // For loops.
    
    memset(hands, CARD_INVALID, sizeof(int) * NUM_HANDS * HAND_SIZE); // Fill Hand Arrays with Empty Data
    

    /* Windows Unicode Support */
    unicodeRoutine(argc, argv);


    /* Populate and shuffle the deck. */
    populateDeck(deck);
    shuffleDeck(deck, CARDS_TOTAL);


    /* Populate and Show Hands */
    for (l = 0; l < NUM_HANDS; l++) {
        for (handsIncrementer[l] = 0; handsIncrementer[l] < 2; handsIncrementer[l]++)
            hands[l][handsIncrementer[l]] = dealCard(deck);

        printf("=======Starting Hand %d:=======\n", l+1);
        displayCards(hands[l]);
        printf("\n\n");
    }


    /* Draw For Each Hand (You Can Merge With Above Loop To Change Output Flow) */
    for (l = 0; l < NUM_HANDS; l++) {
        printf("\n\n======Drawing For Hand %d=======\n", l+1);
        for (k = 0; k < 3; k++) { // Keep drawing until 3 cards are drawn (per spec) or 21/bust. Should really be a while loop, but whatever.
            if (getCardsValue(hands[l]) < BLACKJACK_VALUE) { // 17 for dealer. This loop wouldn't exist at all for the user, who would hit/stay manually.
                hands[l][handsIncrementer[l]++] = dealCard(deck);
                displayCards(hands[l]);
            }

            // A good compiler will optimise the second duplicate getCardsValue() call away. It is debatable whether a good programmer would do so herself at the cost of readability.
            if (getCardsValue(hands[l]) == BLACKJACK_VALUE) {
                printf("Hand %d is a winner!\n", l+1);
                break;
            }
            else if (getCardsValue(hands[l]) > BLACKJACK_VALUE)
            {
                printf("Hand %d is bust.\n", l+1);
                break;
            }
        }
    }


    /* Return */
    return 0;
}
