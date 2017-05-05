package KingSlider.strategies;

import KingSlider.board.Board;
import aiproj.slider.Move;

/**
 * 
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
	 * 
	 * @param boardState
	 * @return
	 */
	public int countEndlinePieces(Board boardState);
	
	
	/**
	 * Sum(horizontal or vertical distance of each piece + 1 for this piece if block in way)
	 * @param boardState
	 * @return
	 */
	public int minimumMovesToWin(Board boardState);
	
	
	
	/**
	 * @param boardState
	 * @return
	 */
	public int totalBasicBlocks(Board boardState);
	
	
	
	/**
	 * @param boardState
	 * @return
	 */
	public int totalDiagonalBlocks(Board boardState);
	
	
	
}
