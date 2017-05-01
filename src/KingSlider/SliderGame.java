package KingSlider;

import java.util.ArrayList;
import java.util.List;

import KingSlider.board.Board;
import KingSlider.board.DestinationPoint;
import KingSlider.board.HPiece;
import KingSlider.board.VPiece;
import KingSlider.strategies.MoveStrategy;
import KingSlider.strategies.PlayerHStrategy;
import KingSlider.strategies.PlayerVStrategy;
import aima.core.search.adversarial.Game;
import aiproj.slider.Move;

public class SliderGame implements Game<Board, Move, Character> {
	
	final int ENDLINE_PIECES_WEIGHT = 20;
	final int MINIMUM_MOVES_TO_WIN_WEIGHT = -10;
	final int TOTAL_BASIC_BLOCKS_WEIGHT = 20;
	final int TOTAL_DIAGONAL_BLOCKS_WEIGHT = 10;

	@Override
	public Character getPlayer(Board state) {
		// TODO Auto-generated method stub
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
		if(player == 'H' ){
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
		int totalBasicBlocks = strategy.totalBasicBlocks(state);
		int totalDiagonal = strategy.totalDiagonalBlocks(state);
		
		return ENDLINE_PIECES_WEIGHT*endLinePieces + MINIMUM_MOVES_TO_WIN_WEIGHT*minimumMovesToWin + 
				TOTAL_BASIC_BLOCKS_WEIGHT* totalBasicBlocks + TOTAL_DIAGONAL_BLOCKS_WEIGHT*totalDiagonal;
	}


	
	
	



}
