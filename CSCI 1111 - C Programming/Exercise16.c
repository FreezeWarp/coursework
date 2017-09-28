/*     FILE: Cards4.c     */

/* Simulate a deck of playing cards.
   
   - Produce the suit & face values 
     (kind of).
                                         */

#include <stdio.h>
#include <string.h>

char suits [4][9] = { "Hearts",
                      "Diamonds",
                      "Clubs",
                      "Spades" };

char faceValue [13][3] = {
  "A", "2", "3", "4", "5",
  "6", "7", "8", "9", "10",
  "J", "Q", "K"
};

void populateDeck(int deck[], int cards) {
  int i;

  for (i = 0; i < cards; i++) {
    deck[i] = i;
  }
}

int main( )
{
  int deck[52], i;
  
  populateDeck(deck, 52);

  for (i = 0; i < 52; i++) {
    if (strcmp(faceValue[deck[i%13]], "A") == 0 || deck[i%13] == 0) // The OR is purely for domenstration purposes. The two statements are completely equivilent, and only one is neccessary. (Additionally, == 0 is used instead of ! because ! really should only be used for boolean returns. Neither modulo nor strcmp returns boolean, even though the two comparisons would in-fact be identical.)
      printf("Card = %2d %s %s\n", deck[i], faceValue[deck[i]%13], suits[deck[i]/13]);
  }
  
  return 0;
}
