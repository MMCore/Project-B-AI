package KingSlider;

import java.util.ArrayList;
import java.util.List;

import KingSlider.board.Board;
import KingSlider.board.DestinationPoint;
import KingSlider.board.HPiece;
import KingSlider.board.VPiece;
import KingSlider.strategies.MoveStrategy;
import aima.core.search.adversarial.Game;
import aiproj.slider.Move;


/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948)
 * This is the SliderGame class. It is an implementation of an adapted version of the aima library Game class. 
 *
 */
public class SliderGame implements Game<Board, Move, Character> {
	

	// the set of weights used in evaluateState()
	private static final int SPECIAL_BOARD_WEIGHT = 50;
	private static final int ENDLINE_PIECES_WEIGHT = 5;
	private static final int MINIMUM_MOVES_TO_WIN_WEIGHT = -9;
	private static final int TOTAL_BLOCKS_WEIGHT = 2;
	private static final int TOTAL_DIAGONAL_WEIGHT = 3;
	private static final int TOTAL_BEYOND_DIAGONAL_WEIGHT = 3;
	private static final int TRAP_WEIGHT = 10;
	private static final int IN_TRAP_WEIGHT = -500;
	
	
	// the utility of losing and winning terminal states
	final int MIN_UTIL = -1000;
	final int MAX_UTIL = 1000;

	@Override
	public Character getPlayer(Board state) {
		return state.getPlayertoMove();
	}

	@Override
	public List<Move> getActions(Board state) {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		// finds valid H-moves
		if(state.getPlayertoMove() == 'H'){
			for(HPiece piece : state.getInPlayH()){
				for(DestinationPoint point : piece.getMovablePositions()){
					Move newMove = new Move(piece.getX(),piece.getY(),point.getDirection());
					moves.add(newMove);
				}
			}
		}
		// finds valid V-moves
		else{
			for(VPiece piece : state.getInPlayV()){
				for(DestinationPoint point : piece.getMovablePositions()){
					Move newMove = new Move(piece.getX(),piece.getY(),point.getDirection());
					moves.add(newMove);
				}
			}
		}
		
		return moves;
	}

	@Override
	public Board getResult(Board state, Move action) {
		Board newState;
		if (state.getPlayertoMove() == 'H'){
			newState = new Board(state.getBoardSize(), state.getBoardString(), 'V');
		}
		else{
			newState = new Board(state.getBoardSize(), state.getBoardString(), 'H');
		}
		
		newState.updateAllPieces();
		newState.movePiece(action.i,action.j, action.d);
		
		return newState;
	}

	@Override
	public boolean isTerminal(Board state) {
		if(state.getInPlayH().isEmpty() || state.getInPlayV().isEmpty()){
			return true;
		}
		
		return false;
	}

	@Override
	public double getUtility(Board state, Character player) {
		
		// checks if winning state
		if(player == 'H'){
			if(state.getInPlayH().isEmpty()){
				return MAX_UTIL;
			}
		}
		else{
			if(state.getInPlayV().isEmpty()){
				return MAX_UTIL;
			}
			
		}
		return MIN_UTIL;
	}
	

	@Override
	public void printGame(Board state){
		state.printBoard();
	}

	@Override
	public int evaluateState(Board state, MoveStrategy strategy) {
		
		int specialBoardOffset = strategy.calculateSpecialBoardValue(state);
		int endLinePieces = strategy.countEndlinePieces(state);
		int minimumMovesToWin = strategy.minimumMovesToWin(state);
		int totalBlocks = strategy.totalBlocks(state);
		int totalDiagonal = strategy.totalDiagonal(state);
		int totalBeyondDiagonal = strategy.totalBeyondDiagonal(state);
		int trapCount = strategy.trapCount(state);
		int trappedCount = strategy.trappedCount(state);
		
		int stateValue = SPECIAL_BOARD_WEIGHT*specialBoardOffset + ENDLINE_PIECES_WEIGHT*endLinePieces + 
				MINIMUM_MOVES_TO_WIN_WEIGHT*minimumMovesToWin + TOTAL_BLOCKS_WEIGHT* totalBlocks + TOTAL_DIAGONAL_WEIGHT*totalDiagonal + 
				TOTAL_BEYOND_DIAGONAL_WEIGHT*totalBeyondDiagonal + TRAP_WEIGHT*trapCount + IN_TRAP_WEIGHT*trappedCount;
		
		return stateValue;
	}
	


	
	
	



}
