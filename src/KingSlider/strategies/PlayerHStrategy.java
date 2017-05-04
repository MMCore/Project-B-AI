package KingSlider.strategies;

import KingSlider.board.BPiece;
import KingSlider.board.Board;
import KingSlider.board.Piece;
import KingSlider.board.VPiece;
import aiproj.slider.Move;

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948) 
 * This is V-player's MoveStrategy implementation. It contains the functions needed to determine V-player's next move.
 *  
 */
public class PlayerHStrategy implements MoveStrategy {

	@Override
	public Move startGameStrategy(Board boardState) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public int countEndlinePieces(Board boardState) {
		int count = 0;
		for (Piece piece: boardState.getInPlayH()){
			if (piece.getX() == boardState.getBoardSize() - 1){
				count++;
			}
		}
		return count;
	}

	@Override
	public int minimumMovesToWin(Board boardState) {
		int count = 0;
		for (Piece piece: boardState.getInPlayH()){
			count += boardState.getBoardSize() - piece.getX();
			// checks if a block piece is in front
			for (int i=piece.getX()+1; i<boardState.getBoardSize(); i++){
				if (boardState.getBoardContents()[i][piece.getY()] instanceof BPiece){
					count++;
					break;
				}
			}
		}
		return count;
	}

	@Override
	public int totalBlocks(Board boardState) {
		int count = 0;
		for (Piece piece: boardState.getInPlayH()){
			if ((piece.getY()!=0) && (boardState.getBoardContents()[piece.getX()][piece.getY()-1] instanceof VPiece)){
				count++;
				}
		}
		return count;
	}




	@Override
	public int totalDiagonal(Board boardState) {
		int count = 0;
		for (Piece piece: boardState.getInPlayH()){
			if (piece.getX() == piece.getY()){
				count++;
				}
		}
		return count;
	}


	@Override
	public int totalBeyondDiagonal(Board boardState) {
		int count = 0;
		for (Piece piece: boardState.getInPlayH()){
			if (piece.getX() > piece.getY()){
				count++;
				}
		}
		return count;
	}

	
}
