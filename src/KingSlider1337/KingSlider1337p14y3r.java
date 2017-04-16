package KingSlider1337;

import aiproj.slider.SliderPlayer;
import aiproj.slider.Move;

// java -cp bin aiproj.slider.Referee 5 KingSlider1337.KingSlider1337p14y3r KingSlider1337.KingSlider1337p14y3r

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948) 
 * This is the Game class. The main function of the program is run from here.
 *  
 */
public class KingSlider1337p14y3r implements SliderPlayer {
	
	private Board board;
	private char player;
	
	public void init(int dimension, String board, char player){
		this.board = new Board(dimension, board);
		this.board.updateAllPieces();
		this.player = player;
	}

	public void update(Move move){
		board.movePiece(move.i, move.j, move.d);
	}
	
	public Move move(){
		return null;
	}
	

}
