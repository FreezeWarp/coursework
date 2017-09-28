/**
 * Get's a hand's value (which is supposed to be a sequence of cards somewhere in the deck).
 * This assumption would not work in some games, like poker, but will generally work in, say, blackjack
 * Though this has yet account for splits -- I'm still mulling over how to handle that one.
 */
int getHandValue(const int hand[]) {
    int totalValue = 0, aces = 0, cardValue,
        i = 0;

    while (hand[i] != CARD_INVALID) { // CARD_INVALID marks the end of the hand array.
        cardValue = getCardValue(hand[i]);

        totalValue += cardValue;
        if (cardValue == 11) aces++;

        i++;
    }

    while (totalValue > BLACKJACK_VALUE && aces > 0) {
        totalValue -= 10;
        aces--;
    }

    return totalValue;
}
