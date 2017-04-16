package KingSlider1337;

import aiproj.slider.Move;

public class PlayerHStrategy implements MoveStrategy {

	@Override
	public Move makeMove(Board gameBoard) {
		
		Move nextMove;
		
		if (gameBoard.getNumLegalHMoves() == 0){
			System.out.println("No H Moves");
			return null;
		}else{
			
			for(HPiece piece : gameBoard.getInPlayH()){
				if(piece.getMovablePositions().size() != 0){
					System.out.println("Inside");
					nextMove = new Move(piece.getX(),piece.getY(),piece.getMovablePositions().get(0).getDirection());
					return nextMove;
				}
			}
			
		}
		System.out.println("Outside");
		return null;
		
	}

	
}
