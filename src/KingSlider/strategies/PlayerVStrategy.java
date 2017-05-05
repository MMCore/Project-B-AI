package KingSlider.strategies;

import java.util.ArrayList;
import java.util.HashMap;

import KingSlider.board.BPiece;
import KingSlider.board.Board;
import KingSlider.board.HPiece;
import KingSlider.board.VPiece;
import aiproj.slider.Move;

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
			System.out.println("EMPTYY");
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


	
	


	

}
