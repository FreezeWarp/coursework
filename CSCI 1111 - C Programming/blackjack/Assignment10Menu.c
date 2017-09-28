/** Command Menu
 * Validates input, and returns a PlayerAction corresponding to the choosen action. */
PlayerAction showMenu(HandArray currentHand) {
    int inputChoice;

    // List actions that can be performed on all hands.
    printf("Choose an action:\n");
    printf("H - Hit\n");
    printf("S - Stay\n");
    printf("D - Double\n");

    // List actions that can only be taken on initial hand.
    if (showMenuCanSurrender(currentHand)) printf("U - Surrender\n");
    if (showMenuCanSplit(currentHand))     printf("P - Split\n");

    // Get Input
    inputChoice = toupper(getchar());
    getchar(); // For enter and what-not.

    // Input Error Checking
    if (inputChoice == 'U' && !showMenuCanSurrender(currentHand)) {
        printf("Invalid choice -- you are not able to surrender for this hand.\n\n");
        return showMenu(currentHand); // I debated whether a recursive showMenu is better than a goto here. In truth, the only real upside here is to avoid getting yelled at by people who (somewhat defensively) deride goto as entirely useless in post-BASIC languages. The downside being that goto is most likely faster and more memory efficient, depending on the compiler.
    }
    else if (inputChoice == 'P' && !showMenuCanSplit(currentHand)) {
        printf("Invalid choice -- you are not able to split for this hand.\n\n");
        return showMenu(currentHand);
    }
    else if (inputChoice != 'H' && inputChoice != 'S' && inputChoice != 'D' && inputChoice != 'P' && inputChoice != 'U') { // ...In any other language, inputChoice !in {'H', 'S', 'D'}
        printf("Invalid choice -- unrecognised symbol.\n\n");
        return showMenu(currentHand);
    }

    printf("\n");

    // Return Coded Input
    switch(inputChoice) {
        case 'H': return HIT;       break;
        case 'S': return STAY;      break;
        case 'D': return DOUBLED;    break;
        case 'U': return SURRENDER; break;
        case 'P': return SPLIT;     break;
    }

    assert(false);
    return 0;
}
bool showMenuCanSurrender(HandArray currentHand) {
    return getHandArraySize(currentHand) == 2;
}
bool showMenuCanSplit(HandArray currentHand) {
    return getHandArraySize(currentHand) == 2 && getCardValue(currentHand[0]) == getCardValue(currentHand[1]);
}


/** Bet Menu
 * Sort-of validates input (...you probably shouldn't enter a string) and returns an int corresponding with the specified bet.
 * Does prevent negative bets as well as bets over the user's current cash.
*/
int showBetMenu(int currentMoney, int hands) {
    int bet;

    printf("You currently have %d. How much would you like to bet this round (0 to quit)? ", currentMoney);
    scanf("%d", &bet);
    getchar(); // For enter and what-not.
    printf("\n\n");

    if (bet > currentMoney / hands) {
        printf("You can't bet that much!\n");
        return showBetMenu(currentMoney, hands);
    }
    else if (bet < 0) {
        printf("You must bet more than that!\n");
        return showBetMenu(currentMoney, hands);
    }

    return bet;
}


/** Insurance Menu
 * For when the dealer has an ace.
 * Returns char 'Y' or 'N' */
char showInsuranceMenu() {
    char insuranceCheck;

    printf("Dealer has Ace up. Would you like insurance (y/n)? ");
    insuranceCheck = toupper(getchar());
    getchar(); // Enter and stuff.

    if (insuranceCheck != 'Y' && insuranceCheck != 'N') {
        printf("Please enter 'y' or 'n'.\n");
        insuranceCheck = showInsuranceMenu();
    }

    return insuranceCheck;
}
