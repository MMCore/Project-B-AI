package KingSlider;

import aiproj.slider.SliderPlayer;
import KingSlider.board.Board;
import KingSlider.strategies.MoveStrategy;
import KingSlider.strategies.PlayerHStrategy;
import KingSlider.strategies.PlayerVStrategy;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aiproj.slider.Move;

// java -cp bin aiproj.slider.Referee 5 KingSlider.KingSliderPlayer KingSlider.KingSliderPlayer

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948) 
 * This is the Game class. The main function of the program is run from here.
 *  
 */

public class KingSliderPlayer implements SliderPlayer  {
	
	final static int ITERATIVE_TIME_OUT = 500;
	
	private char player;
	public static Board gameBoard;
	private Board testBoard; 
	private int dimension;
	private MoveStrategy strategy;
	private boolean isStartGame;
	
	@Override
	public void init(int dimension, String board, char player) {
		KingSliderPlayer.gameBoard = new Board(dimension, board, player);
		this.player = player;
		this.dimension = dimension;
		gameBoard.updateAllPieces();
		isStartGame = true;
		if(player == 'H'){
			strategy = new PlayerHStrategy();
		}else{
			strategy = new PlayerVStrategy();
		}
		
	}

	@Override
	public void update(Move move) {
		if (move == null){
			System.out.println("Turn passed.");
			
		}
		else{
			System.out.println(move.toString());
			gameBoard.movePiece(move.i, move.j, move.d);
		}
		
		
		
	}

	@Override
	public Move move() {
		
		Move nextMove = null;
		
		//While we decide to make a start game strategy
		if(isStartGame){
			nextMove = strategy.startGameStrategy(gameBoard);		
			if(nextMove == null){
				isStartGame = false;
			}
			
		}
		if(!isStartGame){
			testBoard = new Board(dimension, gameBoard.getBoardString(), player);	
			testBoard.updateAllPieces();
			IterativeDeepeningAlphaBetaSearch<Board, Move, Character> searchFunction = 
					new IterativeDeepeningAlphaBetaSearch<Board, Move, Character>
						(new SliderGame(), strategy, ITERATIVE_TIME_OUT);
			nextMove = searchFunction.makeDecision(testBoard);
		}
		return nextMove;
	}


}
