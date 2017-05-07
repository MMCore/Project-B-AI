package KingSlider.strategies;

import java.util.ArrayList;
import java.util.HashMap;

import KingSlider.board.BPiece;
import KingSlider.board.Board;
import KingSlider.board.HPiece;
import KingSlider.board.VPiece;
import KingSlider.board.Piece;
import aiproj.slider.Move;

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948) 
 * This is V-player's MoveStrategy implementation. It contains the functions needed to determine V-player's next move.
 *  
 */
public class PlayerVStrategy implements MoveStrategy {

	@Override
	public Move startGameStrategy(Board boardState) {
		
		HashMap<VPiece,Integer> possibleMoves = new HashMap<VPiece,Integer>();
		ArrayList<VPiece> VPieceNoBlock= new ArrayList<VPiece>(); //V pieces with no block in their row
		int n; //number of moves for a V piece to reach diagonal
		int m; //number moves for a H piece to reach diagonal
		VPiece movePiece;
		boolean blocked;
		
		if(boardState.getBPieces().isEmpty()){
			VPieceNoBlock = boardState.getInPlayV();
		//Finding all V pieces that do not have block in their way
		}else{
			for(VPiece vpiece : boardState.getInPlayV()){
				blocked = false;
				for(BPiece block: boardState.getBPieces()){
					if(block.getX() == vpiece.getX()) {
						blocked = true;
					}				
				}
				
				if(!blocked){
					VPieceNoBlock.add(vpiece);
				}
			}
		}
		
		
		
		//Comparing H block indices to V piece indexes that are not blocked.  
		for(VPiece vpiece : VPieceNoBlock){
			n = vpiece.getX() - vpiece.getY();
			
			if(n > 0){
			
				for(HPiece hpiece : boardState.getInPlayH()){
					
					//Situation where a H piece has a corresponding index with an V piece
					if(vpiece.getX() == hpiece.getY()){
						
						m = hpiece.getY() - hpiece.getX();
						
						//If V piece can move forward and can reach diagonal in same or less moves than H piece it is a good move
						if(n<=m && boardState.getBoardContents()[vpiece.getX()][vpiece.getY()+1]== null){
							possibleMoves.put(vpiece,m-n);
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
			
			return new Move(movePiece.getX(),movePiece.getY(),Move.Direction.UP);
			
		}
		
	}
	
	
	/**
	 *This function searches our hashmap for the V piece that has the lowest m-n value.
	 *This is higher priority As the opponent can reach the diagonal position sooner.
	 *We break m-n ties by selecting values with a higher index (more optimal) as
	 *we are preventing the opponent from being close to their goal state 
	 * @return VPiece
	 */
	private VPiece optimalMove(HashMap<VPiece,Integer> possibleMoves){
		VPiece bestPiece = null;
		int minValue = Integer.MAX_VALUE;
		int bestpieceIndex = Integer.MIN_VALUE;

		
		//No possible moves
		if(possibleMoves.isEmpty()){
			return bestPiece;
		}
		
		for(HashMap.Entry<VPiece,Integer> hashEntry : possibleMoves.entrySet()){
			//Finding pieces with minimum m-n value
			if(hashEntry.getValue() < minValue){
			
				bestPiece = hashEntry.getKey();
				minValue = hashEntry.getValue();
				bestpieceIndex = hashEntry.getKey().getX();
			
			//Prioritising higher indices in tie-breaks
			}else if(hashEntry.getValue() == minValue){
				
				if(hashEntry.getKey().getX() >bestpieceIndex){
					bestPiece = hashEntry.getKey();
					bestpieceIndex = hashEntry.getKey().getX();
				}
				
			}
			
			
		}
	
		return bestPiece;
	}
	
	@Override
	public int countEndlinePieces(Board boardState) {
		int count = 0;
		for (Piece piece: boardState.getInPlayV()){
			if (piece.getY() == boardState.getBoardSize() - 1){
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
			// checks if a block piece is in front
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
	public int totalBlocks(Board boardState) {
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

	@Override
	public int calculateSpecialBoardValue(Board boardState) {
		
		// checks if board is special and if so, runs the appropriate calculation function
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

	// calculates the value state of a board with a block piece on the second last top-right diagonal
	private int calculateSpecialOneBlock(Board boardState) {
		int positionScore = 0;
		Piece[][] boardContents = boardState.getBoardContents();
		int boardSize = boardState.getBoardSize();

		// does nothing if there is no bottleneck caused by the top-right corner being occupied
		if (boardContents[boardSize-1][boardSize-1] == null){
			return 0;
		}
		// positively weights trapping the opponent to the left of the top-right corner
		else if ((boardContents[boardSize-1][boardSize-1] instanceof VPiece) &&
					(boardContents[boardSize-2][boardSize-1] instanceof HPiece)){
			positionScore += 1;
		}

		// negatively weights being trapped by the opponent under the top-right corner
		if (boardContents[boardSize-1][boardSize-2] instanceof VPiece){
			positionScore -= 2;
		}
		
		return positionScore;
	}


	// calculates the value state of a board with 2 block pieces before the top-right diagonal
	private int calculateSpecialTwoBlock(Board boardState) {
		int positionScore = 0;
		Piece[][] boardContents = boardState.getBoardContents();
		int boardSize = boardState.getBoardSize();

		// positively weights trapping the opponent to the left of the top-right corner
		if ((boardContents[boardSize-1][boardSize-1] instanceof VPiece) || (boardContents[boardSize-2][boardSize-1] instanceof VPiece) ){
			for (int x=boardSize-2; x>=boardSize-3; x--){
				for (int y=boardSize-1; y>x; y--){
					if (boardContents[x][y] instanceof HPiece){
						positionScore += 1;
					}
				}
			}
		}

		// negatively weights being trapped by the opponent under the top-right corner
		for (int y=boardSize-2; y>=boardSize-3; y--){
			for (int x=boardSize-1; x>y; x--){
				if (boardContents[x][y] instanceof VPiece){
					for (int x2=boardSize-2; x2>=boardSize-3; x2--){
						for (int y2=boardSize-1; y2>x2; y2--){
							if (boardContents[x2][y2] instanceof HPiece){
								positionScore -= 1;
							}
						}
					}
				}
			}
		}
		
		// ensures pieces avoid & vacate the right column if start-game did not bring them to the top
		for (Piece vpiece : boardState.getInPlayV()){
			if (vpiece.getX() == boardSize-1){
				positionScore -= 1;
			}
		}
		
		return positionScore;
		
	}


	@Override
	public int trapCount(Board boardState) {
		int count = 0;
		Piece[][] boardContents = boardState.getBoardContents();
		int boardSize = boardState.getBoardSize();
		
		for (Piece hpiece : boardState.getInPlayH()){
			if ((hpiece.getY() == boardSize-1) && (hpiece.getX() < boardSize-2)){
				if ((boardContents[hpiece.getX()][boardSize-2] instanceof VPiece) && 
						(boardContents[hpiece.getX()+1][boardSize-1] instanceof VPiece)){
					count++;
				}
			}
		}
		
		return count;
	}


	@Override
	public int trappedCount(Board boardState) {
		int count = 0;
		Piece[][] boardContents = boardState.getBoardContents();
		int boardSize = boardState.getBoardSize();
		
		for (Piece vpiece : boardState.getInPlayV()){
			if ((vpiece.getX() == boardSize-1) && (vpiece.getY() < boardSize-2)){
				if ((boardContents[boardSize-1][vpiece.getY()+1] instanceof HPiece) && 
						(boardContents[boardSize-2][vpiece.getY()] instanceof HPiece)){
					count++;
				}
			}
		}
		
		return count;
	}


	
	


	

}
