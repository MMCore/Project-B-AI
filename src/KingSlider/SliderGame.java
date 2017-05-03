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

public class SliderGame implements Game<Board, Move, Character> {
	
	final int ENDLINE_PIECES_WEIGHT = 2;
	final int MINIMUM_MOVES_TO_WIN_WEIGHT = -9;
	final int TOTAL_BLOCKS_WEIGHT = 8;
	final int TOTAL_DIAGONAL_WEIGHT = 5;
	final int TOTAL_BEYOND_DIAGONAL_WEIGHT = 6;
	
	private boolean debug = false;

	@Override
	public Character getPlayer(Board state) {
		return state.getPlayertoMove();
	}

	@Override
	public List<Move> getActions(Board state) {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		
		if(state.getPlayertoMove() == 'H'){
			for(HPiece piece : state.getInPlayH()){
				for(DestinationPoint point : piece.getMovablePositions()){
					Move newMove = new Move(piece.getX(),piece.getY(),point.getDirection());
					moves.add(newMove);
				}
			}
		}else{
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
		
		//System.out.println("We are trying to do " + action);
		Board newState = new Board(state.getBoardSize(), state.getBoardString(), state.getPlayertoMove());
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
		
		//Returns the utility of the terminal state
		if(KingSliderPlayer.getGameBoard().getPlayertoMove() == 'H' ){
			if(state.getInPlayH().isEmpty()){
				return Double.POSITIVE_INFINITY;
			}
		}else{
			if(state.getInPlayV().isEmpty()){
				return Double.POSITIVE_INFINITY;
			}
			
		}
		return Double.NEGATIVE_INFINITY;
	}
	
	
	

	@Override
	public void printGame(Board state){
		state.printBoard();
	}

	@Override
	public int evaluateState(Board state, MoveStrategy strategy) {
		
		int endLinePieces = strategy.countEndlinePieces(state);
		int minimumMovesToWin = strategy.minimumMovesToWin(state);
		int totalBlocks = strategy.totalBlocks(state);
		int totalDiagonal = strategy.totalDiagonal(state);
		int totalBeyondDiagonal = strategy.totalBeyondDiagonal(state);
		
		
		if (debug){
			System.out.println("------------DEBUG EVAL------------");
			System.out.println("ENDLINE_PIECES: " + endLinePieces);
			System.out.println("MINIMUM_MOVES_TO_WIN: " + minimumMovesToWin);
			System.out.println("TOTAL_BLOCKS: " + totalBlocks);
			System.out.println("TOTAL_DIAGONAL: " + totalDiagonal);
			System.out.println("TOTAL_BEYOND_DIAGONAL: " + totalBeyondDiagonal);
			System.out.println("------------END - DEBUG------------");
		}
		
		return ENDLINE_PIECES_WEIGHT*endLinePieces + MINIMUM_MOVES_TO_WIN_WEIGHT*minimumMovesToWin + 
				TOTAL_BLOCKS_WEIGHT* totalBlocks + TOTAL_DIAGONAL_WEIGHT*totalDiagonal + TOTAL_BEYOND_DIAGONAL_WEIGHT*totalBeyondDiagonal;
	}
	
	public void setEvalDebug(boolean debugOn){
		debug = debugOn;
	}


	
	
	



}
