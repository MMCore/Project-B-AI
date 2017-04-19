package KingSlider;

import aiproj.slider.SliderPlayer;
import KingSlider.board.Board;
import KingSlider.strategies.MoveStrategy;
import KingSlider.strategies.PlayerHStrategy;
import KingSlider.strategies.PlayerVStrategy;
import aiproj.slider.Move;

// java -cp bin aiproj.slider.Referee 5 KingSlider.KingSliderPlayer KingSlider.KingSliderPlayer

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948) 
 * This is the Game class. The main function of the program is run from here.
 *  
 */

public class KingSliderPlayer implements SliderPlayer  {
	
	/**
	 *  A board is initialised. Legal HPiece moves and legal VPiece moves available on this board are then printed.
	 */
	
	private char player;
	private static Board gameBoard;
	

	@Override
	public void init(int dimension, String board, char player) {
		KingSliderPlayer.gameBoard = new Board(dimension, board,player);
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
	
	
	
	public static Board getGameBoard() {
		return gameBoard;
	}


}
