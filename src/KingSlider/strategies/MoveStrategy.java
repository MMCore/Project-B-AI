package KingSlider.strategies;

import KingSlider.board.Board;
import aiproj.slider.Move;

/**
 * 
 *
 */
public interface MoveStrategy {
	


	/**
	 * Starting game strategy uses
	 * @param boardState
	 * @return
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
