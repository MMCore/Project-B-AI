package Slider;

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948) 
 * This is the Game class. The main function of the program is run from here.
 *  
 */
public class Game {
	
	/**
	 *  A board is initialised. Legal HPiece moves and legal VPiece moves available on this board are then printed.
	 */
	public static void main(String[] args){
		Board board = new Board();
		board.updateAllPieces();
		System.out.println(board.getNumLegalHMoves());
		System.out.println(board.getNumLegalVMoves());
	}
	

}
