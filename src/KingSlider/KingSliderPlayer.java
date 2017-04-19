package KingSlider;

import aiproj.slider.SliderPlayer;
import KingSlider.board.Board;
import KingSlider.board.HPiece;
import KingSlider.board.Piece;
import KingSlider.board.VPiece;
import KingSlider.strategies.MoveStrategy;
import KingSlider.strategies.PlayerHStrategy;
import KingSlider.strategies.PlayerVStrategy;
import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
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
	static Board testBoard; 
	

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
		
		Move nextMove;
		testBoard = new Board(gameBoard);	
		System.out.println("Player moviing:" + testBoard.getPlayertoMove());
		IterativeDeepeningAlphaBetaSearch<Board, Move, Character> searchFunction = new IterativeDeepeningAlphaBetaSearch<Board, Move, Character>(new SliderGame(), 0, 2,1);
			
		nextMove = searchFunction.makeDecision(testBoard);

		return nextMove;
		
			/*
			if(player == 'H'){
				
				for(HPiece piece : gameBoard.getInPlayH()){
					if(piece.getMovablePositions().size() != 0){
						nextMove = new Move(piece.getX(),piece.getY(),piece.getMovablePositions().get(0).getDirection());
						return nextMove;
					}
				}
			}else{
				for(VPiece piece : gameBoard.getInPlayV()){
					if(piece.getMovablePositions().size() != 0){
						nextMove = new Move(piece.getX(),piece.getY(),piece.getMovablePositions().get(0).getDirection());
						return nextMove;
					}
				}
			}
			
	
		return null;*/
	}
	
	



	public static Board getGameBoard() {
		return gameBoard;
	}


}
