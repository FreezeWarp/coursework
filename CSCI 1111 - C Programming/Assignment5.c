/**
 * Joseph T. Parsons
 * Runs through three different random examples, displaying counts and (if a program variable is modified) listing each result that is calculated.
 *
 * Some random development notes:
 *
 * I tried to use string arrays. I may or may not have succeeded. Can I be honest with you, person who may or may not be reading this?
 * C strings suck. Like, I understand how they are super-duper memory efficient and all that. But, like, we've all got a crap ton of memory these days.
 *
 * Also, no default function arguments? C'mon! (I feel like this is half the reason that so many languages are written in C. C is great for the
 * low-level stuff, but less than amazing for the high-level stuff.)
 *
 * C90 is stupid. I'm not going to write for it. End-of-line comments work with GNU90 and C99, but not C90. Similarly, variable length arrays (array[var])
 * are a no-go. For the most part, I'm going to try to get my assignments to compile with both gnu89 and c99 standards (a previous problem I had was c99 does not
 * define the math_pi constant, so I had to define it myself to support both standards).
 *
 * There is a pretty clear bias against, for instance, 4 and 5 in a random range of 0-5. This makes sense when you consider how modulo works.
 * I played with a float() random function, but then I ran a billion repetitions of the modulo random function in about 20 seconds on a
 * i7-3630QM (not an amazing processor, though not shabby either). You know what? Screw randomness, that's effing impressive.
 *
 * Some Chi2 analysis expands on this former point. When using sizes of 2 or 4, the Chi2 value is tiny (representing a > 99% certainty of randomness),
 * while for numbers that do not exponentiate evenly into RAND_MAX, Chi2 values usually hover around >80% certainty of randomess. After all, the values are still
 * pretty damn close. But they are not, ultimately, random.
 *
 * Oddly, the Chi2 values don't seem reliable at less than about ~1,000,000 repetitions. Thereafter, they gradually (and reliably) increase if not exponentiating to
 * RAND_MAX, and decrease (towards 0) for 2, 4, 16, 256, 65536, etc. Admittedly, I haven't tested for 256 and later, But I'm fairly sure this holds true.
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>


/**
 * Calculates the area of a rectangle based on width and height parameters.
 * I considered trying to do a slower, more accurate version, but floating-point math (n*rand()/RAND_MAX) isn't really any better. Other options are just silly. Really, if you need a true rand, you should probably be using a better library than stdlib.
 */
int fastRandRangeInt(int min, int max)
{
    return min + (rand() % (max-min+1)); 
}


/**
 * Generates a bunch of random values, counting the results and outputting them.
 */
void genericRandom(int randomMin, int randomMax, int repetitions, char randomStrings[20][20], int seed, int verbose)
{
  /* Define Variables (Long syntax for readability.) */
  int i;
  int randomValue;
  int count[randomMax - randomMin + 1]; // Doesn't work in C90.
  double chi2_expected = (double)repetitions / (randomMax-randomMin+1);
  double chi2 = 0;


  /* Seed Random. */
  srand(seed);


  /* Pre-fill Count Array */
  for (i = 0; i <= randomMax-randomMin; i++) {
      count[i] = 0;
  }
  
  
  /* Generate Years, using 2010 + a random number between 0 and 9. */
  for(i = 0; i < repetitions; i++) {
    randomValue = fastRandRangeInt(randomMin, randomMax);
    count[randomValue-randomMin]++; // This is... kinda a stupid way of doing this, I know.
    
    if (verbose) printf("Random Value (%d-%d) = %s\n", randomMin, randomMax, randomStrings[randomValue-randomMin]); // This syntax seems to work. Not sure if it's proper.
  }
  
  
  /* Output Random Count */
  for (i = 0; i <= randomMax-randomMin; i++) {
    printf("%s: %d (%.2f%%)\n", randomStrings[i], count[i], 100 * (double)count[i] / repetitions);
    
    chi2 += pow(count[i]-chi2_expected, 2) / chi2_expected;
  }
  
  printf("Chi2: %f", chi2);
}


void randomYears(int repetitions, int seed, int verbose)
{
    char strings[20][20];
    strcpy(strings[0], "2010");
    strcpy(strings[1], "2011");
    strcpy(strings[2], "2012");
    strcpy(strings[3], "2013");
    strcpy(strings[4], "2014");
    strcpy(strings[5], "2015");
    strcpy(strings[6], "2016");
    strcpy(strings[7], "2017");
    strcpy(strings[8], "2018");
    strcpy(strings[9], "2019");

    genericRandom(2010, 2019, repetitions, strings, seed, verbose);
}


void rollDice(int repetitions, int seed, int verbose)
{
    char strings[20][20] = {
      "*     ",
      "**    ",
      "***   ",
      "****  ",
      "***** ",
      "******"
    };

    genericRandom(1, 6, repetitions, strings, seed, verbose);
}


void flipCoins(int repetitions, int seed, int verbose)
{
    char strings[20][20] = {"Heads", "Tails"};

    genericRandom(0, 1, repetitions, strings, seed, verbose);
}


/**
 * MAIN
 */
int main(void)
{
    printf("==========Generate Years==========\n");
    randomYears(10, 1928, 1);
    
    printf("\n\n==========Roll Dice==========\n");
    rollDice(10, 80085, 1); // I may not be super mature at times.
    
    printf("\n\n==========Flip Coins==========\n");
    flipCoins(10, 8008135, 1); // ...Ditto.
    
    return 0;
}


/** Documented Runs **/
/** Using seeds 1928, 80085, and 8008135 respectively. **/
/**
========Generate Years========
2010: 0 (0.00%)
2011: 1 (10.00%)
2012: 1 (10.00%)
2013: 0 (0.00%)
2014: 2 (20.00%)
2015: 2 (20.00%)
2016: 1 (10.00%)
2017: 1 (10.00%)
2018: 2 (20.00%)
2019: 0 (0.00%)


========Roll Dice========
*     : 1 (10.00%)
**    : 1 (10.00%)
***   : 3 (30.00%)
****  : 1 (10.00%)
***** : 1 (10.00%)
******: 3 (30.00%)


========Flip Coins========
Heads: 4 (40.00%)
Tails: 6 (60.00%)

--------------------------------
Process exited after 0.00778 seconds with return value 0
Press any key to continue . . .
*/
/**
========Generate Years========
2010: 7 (7.00%)
2011: 12 (12.00%)
2012: 13 (13.00%)
2013: 8 (8.00%)
2014: 17 (17.00%)
2015: 7 (7.00%)
2016: 14 (14.00%)
2017: 5 (5.00%)
2018: 10 (10.00%)
2019: 7 (7.00%)


========Roll Dice========
*     : 16 (16.00%)
**    : 27 (27.00%)
***   : 14 (14.00%)
****  : 17 (17.00%)
***** : 13 (13.00%)
******: 13 (13.00%)


========Flip Coins========
Heads: 44 (44.00%)
Tails: 56 (56.00%)

--------------------------------
Process exited after 0.007513 seconds with return value 0
Press any key to continue . . .
*/
/**
========Generate Years========
2010: 94 (9.40%)
2011: 114 (11.40%)
2012: 116 (11.60%)
2013: 113 (11.30%)
2014: 113 (11.30%)
2015: 93 (9.30%)
2016: 95 (9.50%)
2017: 77 (7.70%)
2018: 89 (8.90%)
2019: 96 (9.60%)


========Roll Dice========
*     : 173 (17.30%)
**    : 161 (16.10%)
***   : 149 (14.90%)
****  : 204 (20.40%)
***** : 166 (16.60%)
******: 147 (14.70%)


========Flip Coins========
Heads: 479 (47.90%)
Tails: 521 (52.10%)

--------------------------------
Process exited after 0.007988 seconds with return value 0
Press any key to continue . . .
*/
/**
========Generate Years========
2010: 969 (9.69%)
2011: 1011 (10.11%)
2012: 1011 (10.11%)
2013: 1049 (10.49%)
2014: 1003 (10.03%)
2015: 958 (9.58%)
2016: 957 (9.57%)
2017: 1006 (10.06%)
2018: 988 (9.88%)
2019: 1048 (10.48%)


========Roll Dice========
*     : 1681 (16.81%)
**    : 1522 (15.22%)
***   : 1720 (17.20%)
****  : 1694 (16.94%)
***** : 1648 (16.48%)
******: 1735 (17.35%)


========Flip Coins========
Heads: 4931 (49.31%)
Tails: 5069 (50.69%)

--------------------------------
Process exited after 0.007061 seconds with return value 0
Press any key to continue . . . */
/**
========Generate Years========
2010: 10169 (10.17%)
2011: 10076 (10.08%)
2012: 10046 (10.05%)
2013: 10141 (10.14%)
2014: 9962 (9.96%)
2015: 9865 (9.87%)
2016: 9930 (9.93%)
2017: 9915 (9.91%)
2018: 9838 (9.84%)
2019: 10058 (10.06%)


========Roll Dice========
*     : 16623 (16.62%)
**    : 16710 (16.71%)
***   : 16647 (16.65%)
****  : 16624 (16.62%)
***** : 16627 (16.63%)
******: 16769 (16.77%)


========Flip Coins========
Heads: 49988 (49.99%)
Tails: 50012 (50.01%)

--------------------------------
Process exited after 0.01062 seconds with return value 0
Press any key to continue . . .
*/
/**
========Generate Years========
2010: 100345 (10.03%)
2011: 99913 (9.99%)
2012: 100088 (10.01%)
2013: 99929 (9.99%)
2014: 99808 (9.98%)
2015: 99958 (10.00%)
2016: 99714 (9.97%)
2017: 100153 (10.02%)
2018: 99927 (9.99%)
2019: 100165 (10.02%)


========Roll Dice========
*     : 167124 (16.71%)
**    : 167010 (16.70%)
***   : 166470 (16.65%)
****  : 166308 (16.63%)
***** : 166409 (16.64%)
******: 166679 (16.67%)


========Flip Coins========
Heads: 499922 (49.99%)
Tails: 500078 (50.01%)

--------------------------------
Process exited after 0.05295 seconds with return value 0
Press any key to continue . . .
*/
/**
========Generate Years========
2010: 999821 (10.00%)
2011: 1000266 (10.00%)
2012: 1000064 (10.00%)
2013: 1000261 (10.00%)
2014: 999145 (9.99%)
2015: 1000020 (10.00%)
2016: 999663 (10.00%)
2017: 1001776 (10.02%)
2018: 1001157 (10.01%)
2019: 997827 (9.98%)


========Roll Dice========
*     : 1666918 (16.67%)
**    : 1668180 (16.68%)
***   : 1666729 (16.67%)
****  : 1667097 (16.67%)
***** : 1666471 (16.66%)
******: 1664605 (16.65%)


========Flip Coins========
Heads: 4999812 (50.00%)
Tails: 5000188 (50.00%)

--------------------------------
Process exited after 0.4569 seconds with return value 0
Press any key to continue . . .
*/

/**
========Generate Years========
2010: 10004531 (10.00%)
2011: 10002640 (10.00%)
2012: 9995552 (10.00%)
2013: 9996987 (10.00%)
2014: 10001454 (10.00%)
2015: 9998771 (10.00%)
2016: 10000283 (10.00%)
2017: 10005928 (10.01%)
2018: 9998198 (10.00%)
2019: 9995656 (10.00%)


========Roll Dice========
*     : 16669504 (16.67%)
**    : 16674187 (16.67%)
***   : 16666481 (16.67%)
****  : 16667554 (16.67%)
***** : 16664032 (16.66%)
******: 16658242 (16.66%)


========Flip Coins========
Heads: 50000027 (50.00%)
Tails: 49999973 (50.00%)

--------------------------------
Process exited after 4.565 seconds with return value 0
Press any key to continue . . .
*/

/**
========Generate Years========
2010: 100015642 (10.00%)
2011: 100006859 (10.00%)
2012: 100005292 (10.00%)
2013: 99990048 (10.00%)
2014: 99993431 (10.00%)
2015: 100008180 (10.00%)
2016: 100001590 (10.00%)
2017: 100015615 (10.00%)
2018: 99983853 (10.00%)
2019: 99979490 (10.00%)


========Roll Dice========
*     : 166687079 (16.67%)
**    : 166677491 (16.67%)
***   : 166659825 (16.67%)
****  : 166666345 (16.67%)
***** : 166653199 (16.67%)
******: 166656061 (16.67%)


========Flip Coins========
Heads: 499999837 (50.00%)
Tails: 500000163 (50.00%)

--------------------------------
Process exited after 45.14 seconds with return value 0
Press any key to continue . . .
*/
