package KingSlider1337;

import aiproj.slider.Move;

public class PlayerVStrategy implements MoveStrategy {

	@Override
	public Move makeMove(Board gameBoard) {
		

		System.out.println("calculating a move");
		Move nextMove;
		
		if (gameBoard.getNumLegalVMoves() == 0){
			System.out.println("No V Moves");
			return null;
		}else{
			
			for(VPiece piece : gameBoard.getInPlayV()){
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
