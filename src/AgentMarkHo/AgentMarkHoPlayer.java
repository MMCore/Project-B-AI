package AgentMarkHo;

import aiproj.slider.SliderPlayer;
import AgentMarkHo.board.Board;
import AgentMarkHo.strategies.MoveStrategy;
import AgentMarkHo.strategies.PlayerHStrategy;
import AgentMarkHo.strategies.PlayerVStrategy;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aiproj.slider.Move;

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948) 
 * This is the Game class. The main function of the program is run from here.
 *  
 */

/*
 * In order to run this agent against itself on a 5-by-5 board:
 * java -cp bin aiproj.slider.Referee 5 KingSlider.KingSliderPlayer KingSlider.KingSliderPlayer
 *
 */

public class AgentMarkHoPlayer implements SliderPlayer  {
	
	final static int ITERATIVE_TIME_OUT = 500;
	
	private char player;
	private Board gameBoard;
	private Board testBoard; 
	private int dimension;
	private MoveStrategy strategy;
	private boolean isStartGame;
	
	@Override
	public void init(int dimension, String board, char player) {
		gameBoard = new Board(dimension, board, player);
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
		
		// Start-game strategy implementation
		if(isStartGame){
			nextMove = strategy.startGameStrategy(gameBoard);		
			if(nextMove == null){
				isStartGame = false;
			}
			
		}
		
		// Mid-game strategy implementation
		if(!isStartGame){
			testBoard = new Board(dimension, gameBoard.getBoardString(), player);	
			testBoard.updateAllPieces();
			IterativeDeepeningAlphaBetaSearch<Board, Move, Character> searchFunction = 
					new IterativeDeepeningAlphaBetaSearch<Board, Move, Character>
						(new SliderGame(), strategy, ITERATIVE_TIME_OUT);
			nextMove = searchFunction.makeDecision(testBoard);
		}
		update(nextMove);
		
		return nextMove;
	}


}
