package KingSlider.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import KingSlider.board.BPiece;
import KingSlider.board.Board;
import KingSlider.board.HPiece;
import KingSlider.board.VPiece;
import aiproj.slider.Move;

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int minimumMovesToWin(Board boardState) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int totalBasicBlocks(Board boardState) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int totalDiagonalBlocks(Board boardState) {
		// TODO Auto-generated method stub
		return 0;
	}


////////////////////NOOB//////////////////////

	
}
