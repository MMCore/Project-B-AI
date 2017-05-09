package KingSlider.strategies;

import KingSlider.board.Board;
import aiproj.slider.Move;

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948) 
 * This is the MoveStrategy interface. It lays out the functions needed to determine the program's next move.
 *  
 */
public interface MoveStrategy {
	

	/**
	 * Starting game strategy for our agent. The agents attempts to select
	 * moves that will a piece forward, such that it can block opponent pieces
	 * along the diagonal. Pieces are strategically picked in order of priority,
	 * i.e pieces that have to be moved urgently for blocking are chosen first. 
	 * The method will return null if no good moves are possible. This will
	 * lead to the end of the start game strategy.`
	 * @param boardState
	 * @return Move
	 */
	public Move startGameStrategy(Board boardState);
	
	
	
	
	/**
	 * Counts the number of pieces on the final board-line
	 * 
	 * @param boardState  the current board state
	 * @return			  number of end-line pieces
	 */
	public int countEndlinePieces(Board boardState);
	
	
	/**
	 * Calculates the minimum number of moves required to win. Does not account for opponent pieces.
	 * @param boardState  the current board state
	 * @return			  the minimum number of moves to win
	 */
	public int minimumMovesToWin(Board boardState);
	
	
	
	/**
	 * Calculates the number of pieces being used to block the opponent
	 * 
	 * @param boardState  the current board state
	 * @return			  the number of pieces blocking the opponent
	 */
	public int totalBlocks(Board boardState);
	
	/**
	 * Counts the number of pieces on or beyond the bottom-left to top-right diagonal
	 * 
	 * @param boardState  the current board state
	 * @return			  number of pieces beyond the diagonal
	 */
	public int totalBeyondDiagonal(Board boardState);


	/**
	 * Checks if a boardstate contains end diagonal blocks and estimates the value of pieces within.
	 * See comments.txt for more information
	 * 
	 * @param boardState  the current board state
	 * @return			  the value of pieces in the special zone if the board is a special zone;
	 * 					  0 if the board is not a special configuration
	 */
	public int calculateSpecialBoardValue(Board boardState);
	
	/**
	 * Counts the number of opponent pieces trapped at the edge of the board
	 * 
	 * @param boardState  the current board state
	 * @return			  the number of opponent pieces trapped
	 */
	public int trapCount(Board boardState);
	
	/**
	 * Counts the number of player pieces trapped by the opponent at the edge of the board
	 * 
	 * @param boardState  the current board state
	 * @return			  the number of own pieces trapped
	 */
	public int trappedCount(Board boardState);
	
	
	
}
