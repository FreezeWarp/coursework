/**
 * Copyright (c) 2015 Joseph T. Parsons <josephtparsons@gmail.com> (https://github.com/FreezeWarp)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 * Simulates Blackjack.
 * run with --hands=1 --sleep=0 for evaluation purposes (...I couldn't bring myself to make them default). Or, you know, enjoy being able to play multiple hands at once and my attempt at establishing rhythm.
 *
 * Dev Notes:
 * (In short: I tried to use everything that I haven't really used much of in the past, for the experiencies.)
 *
 * Since I haven't really held a good grasp of typedef thus far, and I like copying Cole whenever I can (because he's smarter than me),
 * I have made liberal use of TypeDefs for the 10th iteration of this assignment.
 *
 * Linked lists, man. They are somewhat confusing (I had never even heard of them before), but I figured they were the best to support dynamic hand allocation for splits.
 * After a bit of fusing, I implemented them with minimal modification to the original code -- instead of calling array[index], call seekHandArray(linkedList, index)->cards.
 * I didn't really do any optimisation to them (I don't really know how), but I did sacrifice performance for cleanliness -- the seek function will often run multiple times where it could only run once.
 *
 * Several macros are used to make statements shorter and more readable. These all deal with seekHand() in some way. (Also a few that help with playBall() and sleep nonsense).
 *
 * A single goto was used where it was tremendously cleaner to do so -- in handling a case where the game aborts if the dealer has 21 from the outset.
 *
 * Indeed, as far as cleanliness goes, a goto is probably not my worst sin here. The overall structure of things is... dirty. It gets the job done (incredibly well, in-fact),
 * but this is not a shining example of modularity. Sans Object-Oriented, I wouldn't even want to attempt it.
 *
 * Most constants are now pre-processor. The additional flexibility of this is nice. Command line flags are used for some of these:
 ** -u forces unicode on a Windows system (requires non-raster fonts set in console)
 ** -r will keep track of money across game sessions by writing to Assignment10Money.tmp.
 ** -c will show card counts on every action. (Kind-of cheaty, needless to say.)
 ** --money=x overrides the default amount of money.
 ** --hands=x overrides the default number of simulated hands.
 ** --sleep=x applies a multiplier to the sleep commands, effectively speeding up (<1) or slowing down (>1) the "animations." Use 0 to disable animations entirely.
 *
 * And overall, just a ludicrous amount of pointers. I like pointers now :) (stockholm syndrome and all that)
 *
 * A brief overview of supported features:
 ** Betting and optional memory of the betting sum.
 ** Proper support for standard split, double, and surrender bets. (Most casinos don't allow subsequent hits on split aces. I've always found that rule silly, and didn't implement it.)
 ** Partial support for insurance bets, and the game will abort when the dealer has 21 from outset. (In casino blackjack, partial insurance is possible.)
 ** Partial support for multiple simultaneous hands (all rely on the same bet).
 ** Considering the constraints, a rather polished teletype frontend.
 ** Rhythm that makes it easier to feel how a game is going without scrolling up constantly.
 *
 * Note that it does not successfully weigh 21 against a blackjack, since this is a rule I am unfamiliar with and didn't really want to have to implement it. Apparently it exists, though.
 *
 * Compiles successfully with GNU90, C99, and GNU99. Not really tested with GNU90, though. Also tested in both vanilla Windows and Cygwin -- didn't test in Linux explicitly,
 * but I assume it works.
*/

#include "Assignment10.h"

/* MAIN */

int main(int argc, char *argv[])
{
    int money = 100000,
        bet = -1,
        hands = 5,
        i,
        tmp,
        deck[CARDS_TOTAL]; // Create an array for the deck, matching deck size.
    float sleepTime = 1;
    bool forceUnicode = false;
    bool showCounts = false;
    bool rememberMoney = false;


    /* Console Flags */
    for (i = 0; i < argc; i++) {
        if (strncmp("--hands=", argv[i], 8) == 0) sscanf(argv[i], "--hands=%d", &hands); // Simulate More or Fewer Hands
        else if (strncmp("--money=", argv[i], 8) == 0) sscanf(argv[i], "--money=%d", &money); // Different Initial Money Pool
        else if (strncmp("--sleep=", argv[i], 8) == 0) sscanf(argv[i], "--sleep=%f", &sleepTime); // Sleep between turns.
        else if (strcmp("-c", argv[i]) == 0) showCounts = true; // Show Card Counts
        else if (strcmp("-u", argv[i]) == 0) forceUnicode = true; // Force Unicode on Windows

        // Remember Money
        else if (strcmp("-r", argv[i]) == 0) {
            rememberMoney = true;
            tmp = readData("Assignment10Money.tmp");
            money = tmp ? tmp : money;
        }
        
        // For all arguments beyond the first (which is expected to be the file name), alert if the argument is unrecognised.
        else if (i >= 1) {
            fprintf(stderr, "Unrecognised command line flag: %s\n", argv[i]);
        }
    }
    
    if (WINDOWS) { // Windows Unicode Support, Continued
        if (forceUnicode) { setUnicode(true); system("chcp 65001"); } // If we toggle on unicode, we need to execute a command for it to work correctly on Windows.
        else setUnicode(false); // Otherwise, turn it off.
    }


    /* Populate and shuffle the deck. */
    populateDeck(deck);
    shuffleDeck(deck, CARDS_TOTAL);
    

    /* Each playBall() call is a new hand for the dealer. At the end of the call cleanup happens, and by the next call its variables are re-initalised. */
    while (money > 0 && ((bet = showBetMenu(money, hands)) != 0))
        money = playBall(money, bet, deck, hands, showCounts, sleepTime);
    
    
    /* Cookies, basically. */
    if (rememberMoney) storeData("Assignment10Money.tmp", money);


    /* Return */
    return 0;
}


/* Shorthands Macros for playBall() */
#define adjustedBet (int) (bet * getBetMult(seekHand(hands, l)))
#define lose()\
    money -= adjustedBet;\
    printf("%d was lost.", adjustedBet)
#define win()\
    money += adjustedBet;\
    printf("%d was won.", adjustedBet)


/* Get the multiplier corresponding to a particular action taken on a hand. */
float getBetMult(const Hands *hands) {
    float mult = 1;
    
    if (hands->state == DOUBLED) mult *= MULT_DOUBLED;
    if (hands->state == SURRENDER) mult *= MULT_SURRENDER;
    
    if (getHandArraySize(hands->cards) == 2 && getCardsValue(hands->cards) == 21) // If the hand is a blackjack...
        mult *= MULT_BLACKJACK;
    
    return mult;
}


/** playBall() -- simulates the core blackjack game.
 * In a shining example of how not to modularise your code, this function stimulates the entire game, more or less.
 * But it does so rather cleanly. There are four key sections -- initial hand population (plus insurance), user commands, the dealer routine, and finally checking to see which hands the user won/lost.
 */
int playBall(int money, int bet, DeckArray deck, int numHands, bool showCounts, float sleepTime) {
    int activeHands = numHands + 1; // The num of hands being simulated. Starts with the NUM_HANDS constant plus one for dealer.
    Hands *hands = createHands(activeHands); // A linked list of hands being simulated. Head is always the dealer's hand.
    int k, l; // For loops.
    int insuranceBet;


    /* Populate Hands */
    for (k = 0; k < 2; k++)
        for (l = 0; l < activeHands; l++)
            addCardToHand(l);


    /* Show Dealer's Hand */
    displayDealer(false);
    wait(sleepTime);


    /* Check Dealer for Insurance */
    if (getCardValue(getHandArray(0)[1]) == 11) {
        // Payout the insurance bet for every hand simulated. (In casino blackjack, partial insurance is possible, as is taking insurance only for certain hands. For simplicity, I ignore this.)
        if (showInsuranceMenu() == 'Y') {
            insuranceBet = (activeHands - 1) * bet;

            if (getCardValue(getHandArray(0)[0]) == 10) {
                money += insuranceBet;
                printf("Insurance taken, dealer has 21, win %d.\n\n", insuranceBet);
            }
            else {
                money -= insuranceBet;
                printf("Insurance taken, dealer does not have 21, lose %d.\n\n", insuranceBet);
                wait(sleepTime);
            }
        }
    }


    /* Check Dealer for 21 */
    if (getHandValue(0) == 21) {
        printf("Dealer has 21.\n\n");
        wait(sleepTime);
        goto scoreUserHands;
    }


    /* Show Player's Hands */
    for (l = 1; l < activeHands; l++) {
        displayHand(l);
        wait(sleepTime * .5);
    }
    
    wait(sleepTime);


    /* Have the User Do Stuff */
    for (l = 1; l < activeHands; l++) {
        displayHand(l);

        while (getHandValue(l) < BLACKJACK_VALUE && getHandState(l) != STAY && getHandState(l) != SURRENDER && getHandState(l) != DOUBLED) { // Repeat while hand < 21 and user's last action is non-ending.
            if (showCounts) countCards(deck);
            
            switch (showMenu(getHandArray(l))) {
                case HIT:
                addCardToHand(l);
                displayHand(l);

                wait(sleepTime * .5);
                if (getHandValue(l) >= BLACKJACK_VALUE) wait(sleepTime * 1.5);
                break;

                case STAY:
                getHandState(l) = STAY;

                wait(sleepTime);
                break;

                case DOUBLED:
                getHandState(l) = DOUBLED;
                addCardToHand(l);
                displayHand(l);

                wait(sleepTime);
                break;

                case SURRENDER:
                getHandState(l) = SURRENDER;

                wait(sleepTime);
                break;

                case SPLIT:
                // New hand
                appendHand(hands);
                activeHands++;
                getHandArray(activeHands-1)[0] = getHandArray(l)[1];
                getHandInc(activeHands-1) = 1;
                addCardToHand(activeHands-1);

                // Existing hand.
                wait(sleepTime);
                getHandArray(l)[1] = dealCard(deck);
                displayHand(l);
                break;

                default:
                printf("Something else.");
                break;
            }
        }
    }


    /* Have the Dealer Do Stuff */
    displayDealer(true);
    while (getHandValue(0) < DEALER_LIMIT) {
        wait(sleepTime * .5);
        printf("Dealer hits.\n\n");
        addCardToHand(0);
        displayDealer(true);
    }
    wait(sleepTime * .5);


    /* Calculate User Wins/Losses */
    scoreUserHands:
    for (l = 1; l < activeHands; l++) {
        displayHand(l);
        
        if (getHandState(l) == SURRENDER) {
            printf("Hand %d surrendered.\n", l);
            lose();
        }
        else if (getHandValue(l) > BLACKJACK_VALUE) {
            printf("Hand %d is bust.\n", l);
            lose();
        }
        else if (getHandValue(0) > BLACKJACK_VALUE) {
            printf("Dealer busts, Hand %d is a winner.\n", l);
            win();
        }
        else if (getHandValue(l) < getHandValue(0)) {
            printf("Hand %d is a loser.\n", l);
            lose();
        }
        else if (getHandValue(l) > getHandValue(0)) {
            printf("Hand %d is a winner!\n", l);
            win();
        }
        else if (getHandValue(l) == getHandValue(0)) {
            printf("Hand %d ties.\n", l);
        }
        printf("\n\n");
        
        wait(sleepTime * 1.5);
    }


    /* Clean-Up */
    cleanHands(hands);


    /* Return Moneys */
    return money;
}


/* Exceedingly Basic Data Store (EBDS) */
void storeData(const char* fileName, int data) {
    FILE* fp;
    fp = fopen(fileName, "w+");
    fprintf(fp, "%d", data);
    fclose(fp);
}
int readData(const char* fileName) {
    int data;
    FILE* fp;

    if ((fp = fopen(fileName, "r")) != NULL) {
        fscanf(fp, "%d", &data);
        fclose(fp);

        return data;
    }
    else {
        fclose(fp);
        return 0;
    }
}
