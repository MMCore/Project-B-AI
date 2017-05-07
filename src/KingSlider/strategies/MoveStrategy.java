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
	 * Counts the number of pieces on the bottom-left to top-right diagonal
	 * 
	 * @param boardState  the current board state
	 * @return			  number of pieces on the diagonal
	 */
	public int totalDiagonal(Board boardState);
	
	/**
	 * Counts the number of pieces beyond the bottom-left to top-right diagonal
	 * 
	 * @param boardState  the current board state
	 * @return			  number of pieces beyond the diagonal
	 */
	public int totalBeyondDiagonal(Board boardState);


	public int calculateSpecialBoardValue(Board boardState);
	
	
	
}
