package KingSlider.board;
import java.util.ArrayList;

import aiproj.slider.Move;

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948)
 * This is the HPiece class. This piece is a subclass of the
 * Piece class. The H piece can only travel UP,RIGHT and DOWN.
 *
 */
public class HPiece extends Piece {
	
	
	public HPiece(int positionX, int positionY){
		super(positionX,positionY);
		
	}


	@Override
	public int updateLegalMoves(Piece[][] boardContents, int boardSize) {
		
		movablePositions = new ArrayList<DestinationPoint>();
		
		
		int currentPositionX = this.getX();
		int currentPositionY = this.getY();
		int totalLegalMoves = 0;
		
		
		//Checking if the H piece can move UP
		if(currentPositionY + 1 != boardSize){
			if(boardContents[currentPositionX][currentPositionY + 1] == null){
				DestinationPoint legalPoint = new DestinationPoint(currentPositionX,currentPositionY + 1,Move.Direction.UP,false); 
				movablePositions.add(legalPoint);
				totalLegalMoves++;
			}
		}
		
			
		//Checking if the H piece can move RIGHT
		if(currentPositionX + 1 != boardSize){
			if(boardContents[currentPositionX + 1][currentPositionY] == null){
				DestinationPoint legalPoint = new DestinationPoint(currentPositionX + 1,currentPositionY,Move.Direction.RIGHT,false); 
				movablePositions.add(legalPoint);
				totalLegalMoves++;				
			}
		}else if(currentPositionX + 1 == boardSize){
				DestinationPoint legalPoint = new DestinationPoint(currentPositionX + 1,currentPositionY,Move.Direction.RIGHT,true); 
				movablePositions.add(legalPoint);
				totalLegalMoves++;
		}
		
		
		//Checking if the H piece can move DOWN
		if(currentPositionY - 1 != -1){
			if(boardContents[currentPositionX][currentPositionY - 1] == null){
				DestinationPoint legalPoint = new DestinationPoint(currentPositionX,currentPositionY - 1,Move.Direction.DOWN,false); 
				movablePositions.add(legalPoint);
				totalLegalMoves++;
			}
		}
		
		return totalLegalMoves;
		
	}
	
	

}
