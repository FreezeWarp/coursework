C Blackjack
===========

This is a fairly simple, albeit still substantial, C programme that simulates basic blackjack in the console, but does so with a bang: timing events are used to make the game feel more natural (one hand will appear, a delay of a few seconds, and the next hand will appear), and unicode is used to the greatest extent possible to try and make the game seem slightly more believable. You can play multiple hands, your earnings will persist overtime if you use the -r flag, and if you feel like training a bit, you can use -c to count cards. Notably, this game uses one or more fixed decks: when a card is dealt, it is removed from the deck, and when a deck runs out of cards, the whole deck is reshuffled. This means that you could easily use this program as a card-counting trainer, as it simulates a real deck (or decks!) as completely.

Command Flags
-------------
-  `-u` forces unicode on a Windows system (requires non-raster fonts set in console)
-  `-r` will keep track of money across game sessions by writing to Assignment10Money.tmp.
-  `-c` will show card counts on every action. (Kind-of cheaty, needless to say.)
-  `--money=x` overrides the default amount of money.
-  `--hands=x` overrides the default number of simulated hands.
-  `--sleep=x` applies a multiplier to the sleep commands, effectively speeding up (<1) or slowing down (>1) the "animations." Use 0 to disable animations entirely.

Other Options -- Modify Assignment10.h and Recompile
----------------------------------------------------
`CARDS_TOTAL` is the number of total cards simulated. By default, only one deck (52) is used, but you can go as high as you want.
`DEALER_LIMIT` defines where the dealer stops. Consider changing from 17 to 16.
`MULT_*` defines the bonuses from doubling, blackjack, and surrendering.
