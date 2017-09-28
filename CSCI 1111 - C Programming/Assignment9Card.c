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


/* Maps cards to their value in a blackjack hand. */
const int blackjackNumericValue [13] = {
    11, 2, 3, 4, 5,
    6, 7, 8, 9, 10,
    10, 10, 10
};


/**
 * Enables unicode on Windows with the -fU flag.
 */
void unicodeRoutine(int argc, char *argv[]) {
    /* Disable unicode display on Windows. I'm testing ways to support unicode when the console uses non-raster fonts, but I haven't figured out a way to detect for this.
     * (In the mean time, pass the fU flag -- which is not correctly detected, I realise -- to force unicode.) */
    bool forceUnicode = false; // Unicode display; see below.

    if (WINDOWS) {
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

bool isUnicode() {
    return enableUnicode;
}
void setUnicode(bool state) {
    enableUnicode = state;
}


/**
 * Gets the value of a card in blackjack.
 * Aces are treated as 11.
 */
int getCardValue(int cardValue) {
    return blackjackNumericValue[cardValue%13];
}


/**
 * Get's a the sum of the values for an array of cards, simulating a blackjack hand (aces are only 11 if under 22).
 */
int getCardsValue(const int cardValues[]) {
    int totalValue = 0, aces = 0, cardValue,
        i = 0;


    /* Sum Each Array Entry */
    while (cardValues[i] != CARD_INVALID) { // CARD_INVALID marks the end of the hand array.
        cardValue = getCardValue(cardValues[i]);

        totalValue += cardValue;
        if (cardValue == 11) aces++;

        i++;
    }


    /* Account for Ace=11->1 if Over 21 */
    while (totalValue > BLACKJACK_VALUE && aces > 0) {
        totalValue -= 10;
        aces--;
    }

    return totalValue;
}


/**
 * Displays a card.
 * Will use unicode if enabled, otherwise will use non-unicode (resulting in what appears to be crooked cards). Regardless, cards will have the same dimensions - 5x3.
 * Currently deprecated in favour of displayCards().
 */
void displayCard(int cardValue) {
//    extern int CARDS_TOTAL, CARDS_PER_SUIT, CARDS_PER_DECK, CARD_INVALID, BLACKJACK_VALUE, HAND_SIZE;
    
    printf(" ___ \n");
    printf("|%s%s%s|\n",
      faceValue[cardValue%CARDS_PER_SUIT],
      strlen(faceValue[cardValue%CARDS_PER_SUIT]) == 1 ? " " : "", // If the face value is two characters (that is, "10"), then don't pad. For everything else, pad.
      isUnicode() ? suits[cardValue/CARDS_PER_SUIT] : suitsAlt[cardValue/CARDS_PER_SUIT]);

    if (isUnicode()) printf(" \u203e\u203e\u203e\n");
    else printf("-----\n");
}


/**
 * Displays multiple cards on a single group of teletype lines using magic (and silly loops).
 * If a GUI existed, this function should be replaced with one that just displays a single card.
 */
void displayCards(int cardValues[]) {
    int cardCount = 0, i;


    /* Figure Out How Many Cards We Have */
    while (cardValues[cardCount] != -1)
        cardCount++;


    /* Display Top Row */
    for (i = 0; i < cardCount; i++)
        printf(" ___ ");
    printf("\n");


    /* Display Middle Row */
    for (i = 0; i < cardCount; i++)
        printf("|%s%s%s|",
            faceValue[cardValues[i]%CARDS_PER_SUIT],
            strlen(faceValue[cardValues[i]%CARDS_PER_SUIT]) == 1 ? " " : "", // If the face value is two characters (that is, "10"), then don't pad. For everything else, pad.
            isUnicode() ? suits[cardValues[i]/CARDS_PER_SUIT] : suitsAlt[cardValues[i]/CARDS_PER_SUIT]);
    printf("\n");
    
    
    /* Display Buttom Row */
    for (i = 0; i < cardCount; i++)
        printf(isUnicode()
            ? " \u203e\u203e\u203e "
            : "-----");
    printf("\n");
    
    
    /* Display Total Value */
    printf("Total Value: %d\n", getCardsValue(cardValues));
}
