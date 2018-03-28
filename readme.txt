/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *            COMP30024 Artificial Intelligence - Semester 1 2017            *
 *                   Project B - Playing a game of Slider          	     *
 *                                                                           *
 *                               comments.txt                                *
 *                                                                           *
 *        Submission by: Nguyen Ho <hon2> and Marko Mihic <mmihic>  	     *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
 
--------------------------------------------------------------------------------
=================
Package Structure
=================
*Aima.core.search.adverserial
The classes contained in this package were extracted from the aima java 
github repo: https://github.com/aimacode/aima-java. It constitutes files 
that helped us set up alpha beta pruning with iterative deepening. We have 
made many changes to the IterativeDeepeningAlphaBetaSearch.java class 
and adjusted Game.java so that we can use this package for our own agent.
 
*Aima.core.search.framework
This package was also extracted from the aima java github repo: 
https://github.com/aimacode/aima-java. 

*Aiproj.slider
This package was created by Matt Farrugia in order to implement the Slider 
game interface.

*AgentMarkHo
This class contains our implementation of the SliderPlayer interface, along 
with our implementation of the Game interface which determines the 
inputs into our Alpha beta pruning iterative deepening search.

*AgentMarkHo.board
This package contains all the data structures that help us store and explore 
the states of the board, including information on where all the pieces are. 
 
*AgentMarkHo.strategies
This class contains the interface MoveStrategy along with implementations 
for the H and V piece strategies accordingly. Each implementation contains 
the start game strategy for the given piece and the set of functions which 
help assess the features of a given state (utilized in our evaluation function 
for our alpha beta pruning iterative deepening search function).
--------------------------------------------------------------------------------

===================
Start Game Strategy
===================
There are far too many combinations of moves and board states that need to be 
evaluated in order to make specific start game strategies. For this reason, we 
have decided to create a generalized and well-performing start game strategy 
which can be used for each player and all board sizes. The strategy is 
implemented as follows. We will take the perspective of the H player but the 
strategy is exactly the same for the V player, except it will be in the vertical 
perspective rather than horizontal perspective:

Given an H piece, if there are no blocks that exist in the current row and the 
column with the same index as this row contains a V piece, we are interested in 
this H piece.  Next, we calculate the minimum number of moves n, it takes for the 
H piece to reach the diagonal and the minimum number of moves m, it takes for 
the corresponding V piece to reach the diagonal. Note n and m are dependent on 
whose turn it is in the given state and both H and V pieces share the same index. 
If n ? m we will add this to a queue of good moves that the H player can make. 
After completing the above process for all H pieces, we will order our queue such 
that m-n is sorted from smallest to largest. This is strategically desirable because 
these are the moves which are most critical in order for us to successfully block 
the V player’s progression. Note that if we have a tie in our queue, we break our 
tie by picking H pieces that are located higher up on the board, i.e on a higher 
index. This is more desirable because this piece is inching closer to reaching the 
end and preventing the opponent from reaching the end. Finally, if the queue is 
empty our start game strategy has exhausted its usefulness and we begin utilizing 
our mid game strategy.

Efficiency Discussion
=====================
The start game strategy is very efficient compared to our mid game strategy. This 
is extremely important for us because not only are we selecting moves faster but 
it means that when our start game strategy ends, the remaining board state will 
be much closer to the end state. This means that our mid game strategy, which 
uses iterative deepening with alpha beta pruning, will be able to make strategic 
moves efficiently – something it can’t do well at the start of the game where the 
search space is too large. As a result, the depth branching factor of the tree would 
have been cut significantly, allowing us to better utilize our remaining search 
time. 

Because we have generalized our start game strategy, we are guaranteed that our 
start game strategy will make good decisions across varying board sizes. Due to 
this generalization, we can use the same principles behind the strategy for both 
players, which is what we do. The best part about our starting game strategy is 
efficiency for finding moves, with a complexity that is O(n^2) at worst, where n is 
the number of pieces that a player starts with. For something as complex as 
strategic decision making, this performance rewards us in the next stage of the 
game.  
--------------------------------------------------------------------------------

=================
Mid-Game Strategy
=================

Alpha Beta Pruning Algorithm with Iterative Deepening
=====================================================
Due to the excessive quantity of game states that can be played out in slider, 
using the basic minimax algorithm for selecting optimal moves would not work. In 
fact, because cyclic move combinations are allowed, minimax may never actually 
reach the end of the tree. Even if we used minimax cut-off It would simply be far 
too taxing on time complexity, especially given that we have to make moves in a 
fixed amount of time. Thus, we decided to go two steps ahead and not only use 
alpha beta pruning but also perform iterative deepening search. Alpha beta 
pruning helps us maximize our search efficiency by eliminating large subgraphs of 
states that are not required to be explored. The reason we use iterative 
deepening is because we still want to utilize the power of the minimax algorithm 
and evaluating the strength of positions but we don’t necessarily want to play out 
the whole game. This is good because it gives us control over the amount of time 
it takes us to make moves and even if we don’t explore all states, based on our 
evaluation function and exploring to some closer depth we can still make good 
moves. Iterative deepening has the advantage of going to a larger depth when the 
search tree has a smaller branching factor, i.e when there are small number of 
possible moves. This occurs in late game where being able to see more moves 
ahead is vital to ensuring victory.

In summary, our midgame strategy extends from the great midgame state that is 
achieved by the start game strategy. Therefore, our adversarial search begins with 
a board state that is closer to the goal state. This is important because in this 
state, moves become far more critical and utilizing the power of recursive state 
searching has a much higher return for its cost.  Below you will find the set of 
features that we used for our evaluation function for adversarial search, as well as 
the ways in which we further optimized our search strategy to make it even 
better!

Features we use for the Evaluation Function
===========================================
Feature 1: The number of player pieces that are on the ‘end-line’
This feature is the number of pieces that are one move away from being removed 
from the board. Positively weighting pieces on the ‘end-line’ ensures that the 
progress gain of removing them from the board is considered lower than moving 
other pieces into better positions. This is desirable because pieces on the end-line 
cannot be blocked so leaving them in play whilst other good moves can be made 
has no negative effect.
Weight: 5

Feature 2: Minimum moves to win
This feature is the total number of moves a player must make to win, assuming no 
opponent pieces are in the way. Negatively weighting this value ensures that the 
agent preferences moves that either bring pieces forward or out of the way of 
block-pieces.
Weight: -9

Feature 3: Number of pieces directly blocking opponent pieces
This feature is the total number of player pieces directly in front of opponent 
pieces, blocking their forward movement. Positively weighting this feature 
ensures that the player maintains direct blocks, forcing the opponent to either 
move sideways or use a different piece.
Weight: 2

Feature 4: Number of pieces on or beyond a diagonal positon 
This feature counts the number of pieces that are on or have passed the bottom-
left to top-right diagonal. Positively weighting this feature ensures that pieces 
very far from the finish line are given preference to move forward. Additionally, 
any player pieces on or passed the diagonal that are blocked can reach the end 
line faster than the opponent piece maintaining this block.
Weight: 3

Feature 5: Number of opponent pieces trapped
This is the total number of opponent pieces trapped on the player ‘end-line’. (see 
below for trap definition). Positively weighting this ensures that the player will 
preference trapping over all other moves that do not lead to a definite loss. 
Trapping is desirable because doing so means the player will win if no other 
special scenarios (see below) are in play.
Weight: 10

Feature 6: Number of player pieces trapped by opponent
This is the total number of player pieces trapped on the opponent’s ‘end-line’. 
(see below for trap definition). Negatively weighting this ensures that the player 
will preference avoiding traps over all other moves. Avoiding traps is desirable 
because not doing so means the player will lose if no other special scenarios (see 
below) are in play.
Weight: -100

Feature 7: The value of pieces in the special zone of a special board
This is the total estimated value of pieces in a special board’s special zone (see 
below). If the player is blocking opponent positions in the bottleneck, these pieces 
have a positive estimated value. Conversely, if the opponent is blocking the player 
in the bottleneck, these pieces have a negatively estimated value. The weight of 
this feature must be positive as only the magnitude of the estimated value needs 
to be adjusted to cooperate with the evaluation function. 
Weight: 50


Special Scenarios
=================
***Traps***
If a V piece is blocked horizontally and vertically on the last column and the V 
piece is not positioned on the second to last row, then H has created a trap. We 
increment the ‘Trap count’ for the H player and the ‘Trapped count’ for V player. 
Note: If V was positioned on the second to last row and we had 2 H pieces 
blocking it, we would actually lose since V reaches the end first after being 
unblocked. The diagram below shows an example of the H player trapping the V 
player. 

+---+---+---+---+---+
|   |   |   |   |   |
+---+---+---+---+---+
|   |   |   |   | H |
+---+---+---+---+---+
|   |   |   | H | V |
+---+---+---+---+---+
|   |   |   |   |   |
+---+---+---+---+---+
|   |   |   |   |   |
+---+---+---+---+---+

This situation may also be applied in reverse with V trapping the H pieces on the 
uppermost row.

***Special Board Configuration***
Upon investigation of the board we found a special scenario, which can guarantee 
the win or loss for one of the players. The scenario occurs when a player has a 
piece in the top right corner of the board and there exists a block on the diagonal 
connecting to this position. We will look at this scenario from the perspective of 
the H player. Note: due to symmetry, the same logic can be applied from the V 
perspective.

Scenario 1: 
NOTE:
Y = Yellow Region
R = Red Region
+---+---+---+---+---+		+---+---+---+---+---+
|   |   |   | Y | H |		|   |   | Y | Y | H |
+---+---+---+---+---+		+---+---+---+---+---+
|   |   |   | B | R |		|   |   | Y | B | R |
+---+---+---+---+---+		+---+---+---+---+---+
|   | H |   |   | V |		|   | H | B | R | R |
+---+---+---+---+---+		+---+---+---+---+---+
|   |   |   |   |   |		|   |   |   |   | V |
+---+---+---+---+---+		+---+---+---+---+---+
|   |   |   |   |   |		|   |   |   |   |   |
+---+---+---+---+---+		+---+---+---+---+---+

Note: The board size does not have to be 5x5. It can be NxN, where N>3. The 
same argument applies in all cases. 

On our diagrams, it is clear that if the V piece enters the red region (R), as long as 
H maintains its position top-right, H is guaranteed a win. But there’s a catch, H 
cannot move any other pieces into the yellow region (Y) because then H has 
actually placed itself in a losing position.  So aside from this, H can just continue to 
move its other pieces off the board, in a down and rightward manner.

Scenario 2:
+---+---+---+---+---+
|   |   | Y | Y | V |
+---+---+---+---+---+
|   |   | Y | B | R |
+---+---+---+---+---+
|   | H | B | R | R |
+---+---+---+---+---+
|   |   | V |   |   |
+---+---+---+---+---+
|   |   |   |   |   |
+---+---+---+---+---+

If a V piece happens to be on the top right position, for an H piece to avoid loss, 
the H piece simply has to avoid going into the yellow region (Y). 

Our logic for dealing with both scenarios:
Whenever there is a piece in the top right corner, whether it is our piece or the 
opponent’s piece we want to avoid the yellow region (Y). To avoid this region, our 
evaluation function evaluates positions in the yellow region (Y) with an extremely 
negative weight. Thus, it would be unlikely for our pieces to end up in a yellow 
position (Y) given this scenario – especially with our iterative deepening checking 
outcomes for multiple states ahead of time. 

Finally, if our piece is on the top right corner and a V piece exists in the red region 
(R), we weigh that top right position extremely high in our evaluation function. 
Note, the weight of this position is even higher, when there are multiple V pieces 
in the red region (R).  

In cases where there is no piece in the top-right corner. Our evaluation function 
also checks to see if there is an opponent piece that can reach this position first 
before moving pieces into the yellow or red zones. This ensures that even if there 
is no direct block in the current board state, the player will not move pieces that 
may then be blocked in the future.

The evaluation function for this state also negatively weights V-Pieces on H’s ‘end-
line’ to force pieces that are not in the top right corner to move towards the left. 
(This counteracts the negative state value of moving onto a column with a block 
ahead)

Justifying the weighting of each feature
========================================
The weights for being inside a trap or a special board zone need to be the largest 
in magnitude because, although it is rare, it has the power to ensure a definite 
loss for the player if it is not avoided. 

The combination of minimum moves, diagonal pieces, and end-line weights needs 
to be below 0 or else the agent will never move pieces off the board and thus 
never win.

The blocking weight needs to be smaller than the diagonal weight or else the 
agent will maintain sub-optimal blocks (that is, blocks which occur before the 
diagonal which allow the blocked opponent piece to move off the board before 
the player’s blocking own piece).

Traps need to be weighted higher than minimum moves to ensure that the agent 
maintains these blocks for as long as possible in order to ensure victory.
 
All possible combinations of these weights need to be larger than the utility 
assigned to losing states and smaller than the utility assigned to winning states in 
order to not confuse the search algorithm.

Optimisation our Strategy
=========================
***Optimising Search Time of the Algorithm***
Each expanded node within the search tree explores forward actions before 
sideways actions. Using the assumption that in most cases, a forward movement 
is optimal, more subtrees will be pruned earlier by alpha-beta search due to this 
sorting. This has the added functionality of preferring forward movements in the 
case of minimax evaluation ties. 

***Optimising Response to Expected Game Loss***
Our algorithm does not weigh all losing terminal states equally. Instead, it deducts 
a loss utility value from our normal evaluation function. This utility value is large 
enough to ensure that all possible resultant values of losing states are smaller 
than unfinished games.

When the search tree is fully expanded and all paths lead to losing states, our 
algorithm continues to evaluate these losing states to see which position is closest 
to victory. This ensures that if our opponent does not make what our algorithm 
considers optimal moves, we may be able to turn the game around and win.

Our algorithm considers cyclic board states a valid sequence of moves and will 
preference these in scenarios where our opponent will win with any other 
sequence of moves. This ensures the result of the game will be a stalemate 
instead of a loss in scenarios where we expect to lose.

***Optimising Iterative Deepening Timeout for Board Sizes: 5,6,7***
Due to the recursive nature and extreme node depth that our iterative deepening 
function can reach, we are required to set an optimized timeout value to ensure 
we do not run out of processing time. 

The start-game strategy is very efficient and will definitely not take longer than 
500ms to execute. An iterative deepening timeout value of 500ms ensures we 
have almost 29 mid-game moves to make in a board of size 5 (where the 
processing limit is 15 seconds) and 59 mid-game moves to make in a board of size 
6 or 7 (where the processing limit is 30 seconds). A size 5, 6 or 7 game board 
requires at least 20, 25 or 30 forward movements respectively. The extra move 
buffer coupled with the time saved by start-game strategy’s moves means a 
timeout of 500ms is sufficient for acyclic games. 
--------------------------------------------------------------------------------

=============================================
Other Creative Ideas that we did not Include:
=============================================

Taking account of the opponent’s evaluation function for certain moves:
=======================================================================
We had this idea that we may want to keep track of the evaluation function (or 
part thereof) of our opponent because although we are maximizing our 
evaluation function over theirs using our iterative deepening alpha beta pruning, 
we may only be exploring a segment of the tree of nodes. Therefore, we may not 
be making the best decisions. So, an idea was that at each evaluation step, we 
would check how much this move would improve or reduce the other player’s 
utility. This is useful in the following scenario. Let’s say we can make move A 
giving us +40 utility and B giving us utility + 60 but doing A gives the opponent a 
utility of +10 and B gives the opponent a utility of +50. Maybe it would be wiser to 
make move A. So, doing this would help us in such a scenario. 

There are two main reasons why we decided to abandon this idea. Firstly, by 
altering our evaluation of a state by account for the opponent we would be 
required to tweak our weights. However, tweaking weights is a hard task, 
especially with additional features. Therefore, it would be questionable if our new 
feature + weight set would actually improve the overall evaluation of states. 
Secondly, there is a tradeoff between number of features and time to calculate 
features. So, in this case, by accounting for the other player’s utility, we would 
require additional computation. We did however include checking both players 
for traps since this was an important enough scenario to include regardless.

Machine learning:
=================
We contemplated the idea of using machine learning to optimize the weight set 
for our agent. The first problem that we encountered when thinking about this 
was: how can we find or create a large enough data set for training? We thought 
about one interesting way that we could achieve this.

The first thing we could do is randomly generate a very large quantity of legal 
board states. For a given state, 1 player would likely be in a stronger position than 
the other. So, for each of these states, we would play our agent against a version 
of itself, using machine learning to tweak the weights. So, our new machine 
learned agent would be good against our original agent in a variety of losing and 
winning states. We would then do this exact process except now we would play a 
machine learned version against itself and against our original agent. By 
repeatedly doing this we would end up with our final agent that was optimized 
against all our previous machine learned agents as well as the original agent. 

However, we were not sure, even after making such a data set and training our 
agent, whether our resulting agent would perform well. Sure, it would perform 
well against our original agent. But it is entirely possible that we optimized our 
weights in some local maximum of game scenarios. In fact, on the global scale of 
all possible game scenarios, perhaps our original agent may be even better! Due 
to this uncertainty, we decided not to go forward with this idea.
--------------------------------------------------------------------------------


/*	     Dear marker, we know this was a lot of words to read, 
  	     but hopefully you enjoyed reading our report :)		      */

