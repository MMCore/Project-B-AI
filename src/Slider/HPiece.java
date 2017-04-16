package Slider;
import java.util.ArrayList;

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
		
		ArrayList<Point> movableBoardPositions = new ArrayList<Point>();
		int currentPositionX = this.getX();
		int currentPositionY = this.getY();
		int totalLegalMoves = 0;
		
		
		//Checking if the H piece can move UP
		if(currentPositionY + 1 != boardSize){
			if(boardContents[currentPositionX][currentPositionY + 1] == null){
				Point legalPoint = new Point(currentPositionX,currentPositionY + 1); 
				movableBoardPositions.add(legalPoint);
				totalLegalMoves++;
			}
		}
		
			
		//Checking if the H piece can move RIGHT
		if(currentPositionX + 1 != boardSize){
			if(boardContents[currentPositionX + 1][currentPositionY] == null){
				Point legalPoint = new Point(currentPositionX + 1,currentPositionY); 
				movableBoardPositions.add(legalPoint);
				totalLegalMoves++;				
			}
		}else if(currentPositionX + 1 == boardSize){
				totalLegalMoves++;
		}
		
		
		//Checking if the H piece can move DOWN
		if(currentPositionY - 1 != -1){
			if(boardContents[currentPositionX][currentPositionY - 1] == null){
				Point legalPoint = new Point(currentPositionX,currentPositionY - 1); 
				movableBoardPositions.add(legalPoint);
				totalLegalMoves++;
			}
		}
		
		this.movableBoardPositions = movableBoardPositions;
		
		return totalLegalMoves;
		
	}
	

}
