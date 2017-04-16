package KingSlider1337;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948) 
 * This is the Game class. The main function of the program is run from here.
 *  
 */
public class KingSlider1337p14y3r implements SliderPlayer  {
	
	/**
	 *  A board is initialised. Legal HPiece moves and legal VPiece moves available on this board are then printed.
	 */
	
	private char player;
	private Board gameBoard;
	
	
	@Override
	public void init(int dimension, String board, char player) {
		this.gameBoard = new Board(dimension, board);
		this.player = player;
		
		
		gameBoard.updateAllPieces();
		System.out.println(gameBoard.getNumLegalHMoves());
		System.out.println(gameBoard.getNumLegalVMoves());
		
	}

	@Override
	public void update(Move move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Move move() {
		
		MoveStrategy playerStrategy;
		
		if(player == 'H'){
			playerStrategy = new PlayerHStrategy();
		}else if(player == 'V'){
			playerStrategy = new PlayerVStrategy();
		}else{
			System.out.println("Invalid player provided");
			return null;
		}
		
		
		return playerStrategy.makeMove(gameBoard);
		
	}
	

}
