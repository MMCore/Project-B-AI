package KingSlider.board;
import java.util.ArrayList;

import aiproj.slider.Move;

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948)
 * This is the VPiece class. This piece is a subclass of the
 * Piece class. The V piece can only travel UP,RIGHT and LEFT.
 *
 */
public class VPiece extends Piece {


	
	public VPiece(int positionX, int positionY) {
		super(positionX, positionY);
		
	}

	@Override
	public int updateLegalMoves(Piece[][] boardContents, int boardSize) {
		
		movablePositions= new ArrayList<DestinationPoint>();
		
		
		int currentPositionX = this.getX();
		int currentPositionY = this.getY();
		int totalLegalMoves = 0;

		
		//Checking if the V piece can move UP
		if(currentPositionY + 1 != boardSize){
			if(boardContents[currentPositionX][currentPositionY + 1] == null){
				DestinationPoint legalPoint = new DestinationPoint(currentPositionX,currentPositionY + 1,Move.Direction.UP,false);
				movablePositions.add(legalPoint);
				totalLegalMoves++;

			}
		}else if(currentPositionY + 1 == boardSize){
				DestinationPoint legalPoint = new DestinationPoint(currentPositionX,currentPositionY + 1,Move.Direction.UP,true);
				movablePositions.add(legalPoint);
				totalLegalMoves++;
		}
			
		//Checking if the V piece can move RIGHT
		if(currentPositionX + 1 != boardSize){
			if(boardContents[currentPositionX + 1][currentPositionY] == null){
				DestinationPoint legalPoint = new DestinationPoint(currentPositionX + 1,currentPositionY,Move.Direction.RIGHT,false);
				movablePositions.add(legalPoint);
				totalLegalMoves++;

			}
		}
		
		//Checking if the V piece can move LEFT
		if(currentPositionX - 1 != -1){
			if(boardContents[currentPositionX - 1][currentPositionY] == null){
				DestinationPoint legalPoint = new DestinationPoint(currentPositionX - 1,currentPositionY,Move.Direction.LEFT,false);
				movablePositions.add(legalPoint);
				totalLegalMoves++;

			}
		}
		
	
		
		return totalLegalMoves;

		
	}

}
