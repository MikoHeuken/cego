# Cego
This java project is a little help to count your points in the game Cego.
Cego has a little complicated way to count points, so let´s look at it:
In this example I´ll use 6 players and a bet amount of 10 points.
* In the beginning, every player gives a fixed amount of points (bet) in the checkpot. (6 player * 10 points = 60 points in checkpot)
* Every player gets his 4 cards, he can decide, what to do next:
  * Change cards. In this case, he has to play.
  * Not playing. But, a minimum of 3 players has to play.
* After the round has finished, every player with a stitch, gets 1/4th (15 points) from the checkpot.
* Every player which didn´t get a stitch, gives the total amount in the checkpot alone. (Player 1 and 2 skipped the round, Player 3 didn´t get a stitch, Player 4 got 2 stitches, Player 5 and 6 got 1 stitch - Player 1 and 2 just gave the starting 10 points so their total is -10P. Player 3 will pay the next checkpot, so his total is -70. Player 4 has 20P and Player 5 and 6 got 5P)
* If two or more players didn´t got any stitches, they all have to pay the whole checkpot (everybody 60 points). The checkpot will be much higher --> bigger reward for a stitch in the next round. But bigger loss if you don´t get a stitch.
* A game can be finished, if in a round everybody gets a stitch and everybody would pay in the next round.
