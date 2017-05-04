/* Code extacted from aima java github repo:
 * https://github.com/aimacode/aima-java
 * 
 * Adapted for use by Marko Mihic and Nguyen Ho
 */

package aima.core.search.adversarial;

import java.util.List;

import KingSlider.strategies.MoveStrategy;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 165.<br>
 * <br>
 * A game can be formally defined as a kind of search problem with the following
 * elements: <br>
 * <ul>
 * <li>S0: The initial state, which specifies how the game is set up at the
 * start.</li>
 * <li>PLAYER(s): Defines which player has the move in a state.</li>
 * <li>ACTIONS(s): Returns the set of legal moves in a state.</li>
 * <li>RESULT(s, a): The transition model, which defines the result of a move.</li>
 * <li>TERMINAL-TEST(s): A terminal test, which is true when the game is over
 * and false TERMINAL STATES otherwise. States where the game has ended are
 * called terminal states.</li>
 * <li>UTILITY(s, p): A utility function (also called an objective function or
 * payoff function), defines the final numeric value for a game that ends in
 * terminal state s for a player p. In chess, the outcome is a win, loss, or
 * draw, with values +1, 0, or 1/2 . Some games have a wider variety of possible
 * outcomes; the payoffs in backgammon range from 0 to +192. A zero-sum game is
 * (confusingly) defined as one where the total payoff to all players is the
 * same for every instance of the game. Chess is zero-sum because every game has
 * payoff of either 0 + 1, 1 + 0 or 1/2 + 1/2 . "Constant-sum" would have been a
 * better term, but zero-sum is traditional and makes sense if you imagine each
 * player is charged an entry fee of 1/2.</li>
 * </ul>
 * 
 * @author Ruediger Lunde
 * 
 * @param <STATE>
 *            Type which is used for states in the game.
 * @param <ACTION>
 *            Type which is used for actions in the game.
 * @param <PLAYER>
 *            Type which is used for players in the game.
 */
public interface Game<STATE, ACTION, PLAYER> {

	/**
	 * Determines which player's turn it is
	 * @param state  the current game state
	 * @return		 the next player to move
	 */
	PLAYER getPlayer(STATE state);

	/**
	 * Returns a list of valid actions that can be performed on a give state
	 * @param state  the current game state
	 * @return       a list of valid actions
	 */
	List<ACTION> getActions(STATE state);

	/**
	 * Returns the resultant state after an action is completed.
	 * @param state	  the current game state
	 * @param action  the action performed
	 * @return		  the resultant game state
	 */
	STATE getResult(STATE state, ACTION action);

	/**
	 * Checks whether a state is terminal
	 * @param state  a game state
	 * @return       <code>true</code> if the user is a member of the group;
	 *         		 <code>false</code> otherwise
	 */
	boolean isTerminal(STATE state);

	/**
	 * Calculates the utility of a terminal state
	 * 
	 * @param state   a game state
	 * @param player  the player being evaluated
	 * @return        the state's utility
	 */
	double getUtility(STATE state, PLAYER player);
	
	/**
	 * Prints out a game state
	 * 
	 * @param state  a game state
	 */
	void printGame(STATE state);

	/**
	 * Calculates an estimation of the numeric value of a given state using a given strategy
	 * 
	 * @param  state     a game state
	 * @param  strategy  the evaluation strategy
	 * @return           estimated value of a game state
	 */
	int evaluateState(STATE state, MoveStrategy strategy);
}
