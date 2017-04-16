/* * * * * * * * *
 * Slider game player interface specification. Your program should implement
 * this interface so that the Referee can conduct it to play a game of Slider.
 * 
 * Each of the methods in this interface are described in detail in the project 
 * part B specification. Please read this specification carefully to ensure you 
 * fully understand what is required of your program.
 *
 * created for COMP30024 Artificial Intelligence 2017
 * by Matt Farrugia <matt.farrugia@unimelb.edu.au>
 */
package aiproj.slider;

/**
 * Slider game player interface specification. Your program should implement
 * this interface so that the Referee can conduct it to play a game of Slider.
 */
public interface SliderPlayer {

	/** 
	 * Prepare a newly created SliderPlayer to play a game of Slideron a given
	 * board, as a given player.
	 * 
	 * @param dimension The width and height of the board in cells
	 * @param board A string representation of the initial state of the board,
	 * as described in the part B specification
	 * @param player 'H' or 'V', corresponding to which pieces the player will
	 * control for this game ('H' = Horizontal, 'V' = Vertical)
	 */
	public void init(int dimension, String board, char player);

	/**
	 * Notify the player of the last move made by their opponent. In response to
	 * this method, your player should update its internal representation of the
	 * board state to reflect the result of the move made by the opponent.
	 *
	 * @param move A Move object representing the previous move made by the 
	 * opponent, which may be null (indicating a pass). Also, before the first
	 * move at the beginning of the game, move = null.
	 */
	public void update(Move move);

	/** 
	 * Request a decision from the player as to which move they would like to
	 * make next. Your player should consider its options and select the best 
	 * move available at the time, according to whatever strategy you have
	 * developed.
	 * 
	 * The move returned must be a legal move based on the current
	 * state of the game. If there are no legal moves, return null (pass).
	 *
	 * Before returning your move, you should also update your internal
	 * representation of the board to reflect the result of the move you are
	 * about to make.
	 *
	 * @return a Move object representing the move you would like to make
	 * at this point of the game, or null if there are no legal moves.
	 */
	public Move move();
}
