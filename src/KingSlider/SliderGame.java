package KingSlider;

import java.util.ArrayList;
import java.util.List;

import KingSlider.board.Board;
import KingSlider.board.DestinationPoint;
import KingSlider.board.HPiece;
import KingSlider.board.Piece;
import KingSlider.board.VPiece;
import aima.core.search.adversarial.Game;
import aiproj.slider.Move;

public class SliderGame implements Game<Board, Move, Character> {

	private static Board testBoard;
	
	

	@Override
	public Board getInitialState() {
		// TODO Auto-generated method stub
		return testBoard = new Board(KingSliderPlayer.getGameBoard());
	}

	@Override
	public Character getPlayer(Board state) {
		// TODO Auto-generated method stub
		return state.getPlayertoMove();
	}

	@Override
	public List<Move> getActions(Board state) {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		
		if(testBoard.getPlayertoMove() == 'H'){
			for(HPiece piece : state.getInPlayH()){
				for(DestinationPoint point : piece.getMovablePositions()){
					Move newMove = new Move(point.getX(),point.getY(),point.getDirection());
					moves.add(newMove);
				}
			}
		}else{
			for(VPiece piece : state.getInPlayV()){
				for(DestinationPoint point : piece.getMovablePositions()){
					Move newMove = new Move(point.getX(),point.getY(),point.getDirection());
					moves.add(newMove);
				}
			}
		}
		
		return moves;
	}

	@Override
	public Board getResult(Board state, Move action) {
		testBoard.movePiece(action.i, action.j, action.d);
		if(testBoard.getPlayertoMove() == 'H'){
			testBoard.setPlayertoMove('V');
		}else{
			testBoard.setPlayertoMove('H');
		}
		return testBoard;
	}

	@Override
	public boolean isTerminal(Board state) {
		if(testBoard.getInPlayH().isEmpty() || testBoard.getInPlayV().isEmpty()){
			return true;
		}
		
		return false;
	}

	@Override
	public double getUtility(Board state, Character player) {
		// TODO Auto-generated method stub
		return 1;
	}



}
