package KingSlider.strategies;

import KingSlider.board.BPiece;
import KingSlider.board.Board;
import KingSlider.board.HPiece;
import KingSlider.board.Piece;
import KingSlider.board.VPiece;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aiproj.slider.Move;

public class PlayerVStrategy implements MoveStrategy {

	@Override
	public Move startGameStrategy(Board boardState) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public int countEndlinePieces(Board boardState) {
		int count = 0;
		for (Piece piece: boardState.getInPlayV()){
			if (piece.getX() == boardState.getBoardSize() - 1){
				count++;
			}
		}
		return count;
	}

	@Override
	public int minimumMovesToWin(Board boardState) {
		int count = 0;
		for (Piece piece: boardState.getInPlayV()){
			count += boardState.getBoardSize() - piece.getY();
			for (int j=piece.getY()+1; j<boardState.getBoardSize(); j++){
				if (boardState.getBoardContents()[piece.getX()][j] instanceof BPiece){
					count++;
					break;
				}
			}
		}
		return count;
	}

	@Override
	public int totalBasicBlocks(Board boardState) {
		int count = 0;
		for (Piece piece: boardState.getInPlayV()){
			if ((piece.getX()!=0) && (boardState.getBoardContents()[piece.getX()-1][piece.getY()] instanceof HPiece)){
				count++;
				}
		}
		return count;
	}

	@Override
	public int totalDiagonal(Board boardState) {
		int count = 0;
		for (Piece piece: boardState.getInPlayV()){
			if (piece.getX() == piece.getY()){
				count++;
				}
		}
		return count;
	}


	@Override
	public int totalBeyondDiagonal(Board boardState) {
		int count = 0;
		for (Piece piece: boardState.getInPlayV()){
			if (piece.getX() < piece.getY()){
				count++;
				}
		}
		return count;
	}


	
	


	

}
