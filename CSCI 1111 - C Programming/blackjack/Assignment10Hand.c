/** Creates a Hands linked list.
 * Code specifically is concerned with the first entry, but the size parameter will use appendHand (below) to add additional entries.
 * Note that the first hand is _always_ the dealer's, and as such certain parameters can be uninitialised, mainly state and bet.
 * Initialises cards to CARD_INVALID. */
Hands* createHands(unsigned int size) {
    unsigned int i;

    // Create initial hand.
    Hands *hands = (Hands*)malloc(sizeof(Hands));
    Hands *nextHands = hands;

    // Set defaults
    memset(nextHands->cards, CARD_INVALID, sizeof(HandArray));
    nextHands->incrementor = 0; // Fun-fact: not setting this line here and in appendHand() broke the program on subsequent deals (not the first deal) on Unix systems. Uninitalised memory = bad.
    nextHands->next = NULL;

    // For each additional hand, add a node.
    for (i = 1; i < size; i++) {
        nextHands = appendHand(nextHands);
    }

    return hands;
}


/** Appends a node to a Hands linked list.
 * Initialises state to HIT and cards to CARD_INVALID. */
Hands* appendHand(Hands *currentHands) {
    Hands* headHand = currentHands;

    // Seek last hand.
    while (headHand->next != NULL)
        headHand = headHand->next;

    // Append to last hand.
    headHand->next = (Hands*)malloc(sizeof(Hands));

    // Set defaults
    memset(headHand->next->cards, CARD_INVALID, sizeof(HandArray));
    headHand->next->incrementor = 0;
    headHand->next->state = HIT;
    headHand->next->next = NULL;

    // Return head hand
    return headHand->next;
}


/** Frees the memory allocated by a Hands linked list.
 * Should be done once a round is complete, especially to clean up, e.g., extra hands from splits.
 * More-or-less from http://stackoverflow.com/questions/7025328/linkedlist-how-to-free-the-memory-allocated-using-malloc*/
void cleanHands(Hands *head) {
    Hands *p, *next;
    for (p = head; NULL != p; p = next) {
        next = p->next;
        free(p);
    }
}


/* So, quick cavaet: I have zero prior experience with data structures (though ironically quite a bit with database, which may help at least). This is probably not the "right" way of doing this.
 * That out-of-the-way, this seeks into a Hands linked list and returns the position-th node. */
Hands* seekHand(Hands *h, unsigned int position) {
    unsigned int i;
    Hands *soughtHand = h;

    for (i = 0; i < position; i++) {
        assert(soughtHand->next != NULL); // Is this a correct usage of assert? Not... entirely sure. I've heard of them, but never used them myself.

        soughtHand = soughtHand->next;
    }

    return soughtHand;
}


/* Display cards and some extra formatting. */
void displayHand2(Hands* hands, unsigned int handNum, bool showDealer) {
    if (handNum == 0)
        printf("======Dealer's Hand======\n");
    else
        printf("========Hand %d:=======\n", handNum);

    /* Display Cards */
    displayCards(getHandArray(handNum), handNum == 0 && !showDealer);

    /* Display Total Value and Padding*/
    if (handNum != 0 || showDealer)
        printf("Total Value: %d\n\n\n", getHandValue(handNum));
    else
        printf("\n\n\n");
}
