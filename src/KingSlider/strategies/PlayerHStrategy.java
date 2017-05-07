package KingSlider.strategies;

import java.util.ArrayList;
import java.util.HashMap;

import KingSlider.board.BPiece;
import KingSlider.board.Board;
import KingSlider.board.HPiece;
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
		
		
		HashMap<HPiece,Integer> possibleMoves = new HashMap<HPiece,Integer>();
		ArrayList<HPiece> HPieceNoBlock= new ArrayList<HPiece>(); //H pieces with no block in their row
		int n; //number of moves for a H piece to reach diagonal
		int m; //number moves for a V piece to reach diagonal
		HPiece movePiece;
		boolean blocked;
		
		//No blocking pieces
		if(boardState.getBPieces().isEmpty()){
			
			HPieceNoBlock = boardState.getInPlayH();
			
		//Finding all H pieces that do not have block in their way
		}else{
			for(HPiece hpiece : boardState.getInPlayH()){
				blocked = false;
				for(BPiece block: boardState.getBPieces()){
					if(block.getY() == hpiece.getY()) {
						blocked = true;
					}				
				}
				if(!blocked){
					HPieceNoBlock.add(hpiece);
				}
			}
			
		}

		
		
		//Comparing V block indices to H piece indexes that are not blocked.  
		for(HPiece hpiece : HPieceNoBlock){
			
			n = hpiece.getY() - hpiece.getX();
			
			if(n > 0){
		
				for(VPiece vpiece : boardState.getInPlayV()){
					
					//Situation where a V piece has a corresponding index with an H piece
					if(vpiece.getX() == hpiece.getY()){
						
						m = vpiece.getX() - vpiece.getY();
						
						//If H piece can move forward and can reach diagonal in same or less moves than V piece it is a good move
						if(n<=m && boardState.getBoardContents()[hpiece.getX()+1][hpiece.getY()]== null){
							possibleMoves.put(hpiece,m-n);
						}
						
					}
					
				}
			}
		
		}
		
		movePiece = optimalMove(possibleMoves);
		
		//A null here, marks the end of the start game strategy and the beginning of the mid game strategy.
		if(movePiece == null){
			return null;
			
		//Otherwise we keep making moves
		}else{
			
			return new Move(movePiece.getX(),movePiece.getY(),Move.Direction.RIGHT);
			
		}
		
	}
	
	
	/**
	 *This function searches our hashmap for the H piece that has the lowest m-n value.
	 *This is higher priority As the opponent can reach the diagonal position sooner.
	 *We break m-n ties by selecting values with a higher index (more optimal) as
	 *we are preventing the opponent from being close to their goal state 
	 * @return HPiece
	 */
	private HPiece optimalMove(HashMap<HPiece,Integer> possibleMoves){
		HPiece bestPiece = null;
		int minValue = Integer.MAX_VALUE;
		int bestpieceIndex = Integer.MIN_VALUE;

		
		//No possible moves
		if(possibleMoves.isEmpty()){
			return bestPiece;
		}
		
		for(HashMap.Entry<HPiece,Integer> hashEntry : possibleMoves.entrySet()){
			//Finding pieces with minimum m-n value
			if(hashEntry.getValue() < minValue){
			
				bestPiece = hashEntry.getKey();
				minValue = hashEntry.getValue();
				bestpieceIndex = hashEntry.getKey().getY();
			
			//Prioritising higher indices in tie-breaks
			}else if(hashEntry.getValue() == minValue){
				
				if(hashEntry.getKey().getY() >bestpieceIndex){
					bestPiece = hashEntry.getKey();
					bestpieceIndex = hashEntry.getKey().getY();
				}
				
			}
			
			
		}
	
		return bestPiece;
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


	@Override
	public int calculateSpecialBoardValue(Board boardState) {
		if (boardState.getBoardContents()[boardState.getBoardSize()-2][boardState.getBoardSize()-2] instanceof BPiece){
			if (boardState.getBoardContents()[boardState.getBoardSize()-3][boardState.getBoardSize()-3] instanceof BPiece){
				return calculateSpecialTwoBlock(boardState);
			}
			else{
				return calculateSpecialOneBlock(boardState);
			}
		}
		return 0;
	}


	private int calculateSpecialOneBlock(Board boardState) {
		int positionScore = 0;
		Piece[][] boardContents = boardState.getBoardContents();
		int boardSize = boardState.getBoardSize();
		
		if (boardContents[boardSize-1][boardSize-1] == null){
			return 0;
		}
		else if ((boardContents[boardSize-1][boardSize-1] instanceof HPiece)&&
					(boardContents[boardSize-1][boardSize-2] instanceof VPiece)){
			positionScore += 1;
		}

		if (boardContents[boardSize-2][boardSize-1] instanceof HPiece){
			positionScore -= 2;
		}
		
		return positionScore;
	}


	private int calculateSpecialTwoBlock(Board boardState) {
		int positionScore = 0;
		Piece[][] boardContents = boardState.getBoardContents();
		int boardSize = boardState.getBoardSize();
		
		if (boardContents[boardSize-1][boardSize-1] == null){
			return 0;
		}
		else if ((boardContents[boardSize-1][boardSize-1] instanceof HPiece) || (boardContents[boardSize-1][boardSize-2] instanceof HPiece)){
			for (int y=boardSize-2; y>=boardSize-3; y--){
				for (int x=boardSize-1; x>y; x--){
					if (boardContents[x][y] instanceof VPiece){
						positionScore += 1;
					}
				}
			}
		}

		for (int x=boardSize-2; x>=boardSize-3; x--){
			for (int y=boardSize-1; y>x; y--){
				if (boardContents[x][y] instanceof HPiece){
					positionScore -= 2;
				}
			}
		}
		
		return positionScore;
	}

	
}
