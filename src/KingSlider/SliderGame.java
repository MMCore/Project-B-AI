package KingSlider;

import java.util.List;

import KingSlider.board.Board;
import KingSlider.board.Piece;
import aima.core.search.adversarial.Game;
import aiproj.slider.Move;

public class SliderGame implements Game<Board, Move, Character> {

	private char currentPlayer;
	private static Board testBoard;
	
	
	
	public SliderGame(char currentPlayer) {
		
		this.currentPlayer = currentPlayer;
	
	}


	@Override
	public Board getInitialState() {
		// TODO Auto-generated method stub
		return KingSliderPlayer.getGameBoard();
	}

	@Override
	public Character getPlayer(Board state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Move> getActions(Board state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Board getResult(Board state, Move action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTerminal(Board state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getUtility(Board state, Character player) {
		// TODO Auto-generated method stub
		return 0;
	}



}
