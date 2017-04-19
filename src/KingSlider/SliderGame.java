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


	@Override
	public Character getPlayer(Board state) {
		// TODO Auto-generated method stub
		return state.getPlayertoMove();
	}

	@Override
	public List<Move> getActions(Board state) {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		
		if(state.getPlayertoMove() == 'H'){
			state.setPlayertoMove('V');
		}else{
			state.setPlayertoMove('H');
		}
		
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
		
		System.out.println("We are trying to do " + action);
		state.movePiece(action.i,action.j, action.d);
		
		return state;
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
		
		MoveStrategy strategy;
		
		if(player == 'H'){
			strategy = new PlayerHStrategy();
		}else{
			strategy = new PlayerVStrategy();
		}
		
		return 1;
	}
	
	@Override
	public void printGame(Board state){
		state.printBoard();
	}



}
