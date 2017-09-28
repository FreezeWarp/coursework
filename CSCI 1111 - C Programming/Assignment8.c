/**
 * Joseph T. Parsons
 *
 * Displays playing cards from a deck of variable size.
 *
 * Dev Notes:
 *
 * I considered having the hand arrays point to elements in the global deck array. However, the only advantage practical advantage of doing this -- space saving -- is effectively non-existent when we're dealing with something that can basically be expressed with 8-bit integers.
 * The big disadvantage of doing this, then, is that the hand array would not remain valid if the deck array is shuffled. So it makes sense to treat the hands as independent mini-decks, and not subsets of the global deck array.
 *
 * Split things up into a header file and three sets of functions roughly corresponding to objects.
 * The include statements deal with everything on their own (I'm not including a make file), and honestly, I have no idea if I did that right either. I'm kinda following PHP convention, which is probably the worst convention to follow.
 *
 * ...On the suggestion of the notes given for the previous assignment, I removed all functions dealing with deckPosition, and just made it a static variable inside of dealCard(). Not... super happy about this solution, since you can no longer easily see how far you are into the deck, but whatever.
 * (It would be trivial to add a parameter to return the current deckPosition from within dealCard.)
 *
 * I annotated main() with how you would do things in OOP, since at least for me this makes it more obvious how to most cleanly implement something in non-OOP (see misc changes below).
 *
 * Other changes since Assignment 7:
 ** getHandValue() now takes a hand array instead of a deck array and parameters to specify a "slice" of the deck.
 ** isUnicodeOn --> isUnicode
 ** displayCard() and getCardValue() now only take a card value, instead of an array and index that correspond to a card value (that was silly of me).
 ** dealCard() now returns the card value, not its position in the deck. That was also silly of me.
*/

#include "Assignment8.h"



/* MAIN */
int main(int argc, char *argv[]) // I have no idea if I'm doing this right.
{
    int deck[CARDS_TOTAL]; // Create an array for the deck, matching deck size.
    int hand1[HAND_SIZE],
        hand2[HAND_SIZE]; // Create two arrays for hand values. These are effectively of the deck type -- while I considered pointing to deck values, I want these to be exist after deck shuffle, which happens outomatically if out of cards. A hand could, in theory, have values 49, 49, 49, 49, and 49 -- a deck can only have one of these.
    int i; // For loops.
    
    
    /* Fill Hand Arrays with Empty Data */
    memset(hand1, CARD_INVALID, sizeof hand1); // $hand1 = new Hand() in OOP.
    memset(hand2, CARD_INVALID, sizeof hand2);


    /* Windows Unicode Support */
    unicodeRoutine(argc, argv);


    /* Populate the deck, then display all cards in this deck. */
    printf("======Displaying Fresh New Deck======\n");
    populateDeck(deck); // $deck = new Deck() in OOP
    for (i = 0; i < CARDS_TOTAL; i++) // CARDS_TOTAL as $deck->SIZE in OOP.
        displayCard(deck[i]); // $deck->getCard(i)->display() in OOP


    /* Populate Two Hands, Show Them */
    printf("======Drawing Cards from Shuffled Deck======\n");
    shuffleDeck(deck, CARDS_TOTAL); // $deck->shuffle() in OOP.
    
    for (i = 0; i < 5; i++) {
        // $hand1->setCard(i, $deck->deal()) or $hand1->getCard(i)->replace($deck->deal()) in OOP, where $hand1 is a DECK (or extended DECK type HAND) object, and $hand1->get() returns a CARD object
        hand1[i] = dealCard(deck); // cards 0, 2, 4, 6, 8
        hand2[i] = dealCard(deck); // cards 1, 3, 5, 7, 9
    }

    for (i = 0; i < 5; i++)
        displayCard(hand1[i]);
    printf("\nTotal Hand 1 Value = %d\n", getHandValue(hand1)); // getHandValue as $hand->getBlackjackValue() in OOP.
        
    for (i = 0; i < 5; i++)
        displayCard(hand2[i]);
    printf("\nTotal Hand 2 Value = %d\n", getHandValue(hand2));


    /* Display Remainder of Deck */
    printf("======Displaying Remainder of Deck======\n");
    for (i = 10; i < CARDS_TOTAL; i++)
        displayCard(deck[i]);


    /* Return */
    return 0;
}
