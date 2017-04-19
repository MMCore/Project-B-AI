package KingSlider1337;

import aiproj.slider.SliderPlayer;
import aiproj.slider.Move;

// java -cp bin aiproj.slider.Referee 5 KingSlider1337.KingSlider1337p14y3r KingSlider1337.KingSlider1337p14y3r

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
		
	}

	@Override
	public void update(Move move) {
		if (move == null){
			System.out.println("attempting an invalid move");
		}
		else{
			System.out.println(move.toString());
			gameBoard.movePiece(move.i, move.j, move.d);
		}
		
		
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
