package KingSlider1337;

import aiproj.slider.Move;

public class PlayerVStrategy implements MoveStrategy {

	@Override
	public Move makeMove(Board gameBoard) {
		
		Move nextMove;
		
		if (gameBoard.getNumLegalVMoves() == 0){
			return null;
		}else{
			
			for(VPiece piece : gameBoard.getInPlayV()){
				if(piece.getMovablePositions().size() != 0){
					
					nextMove = new Move(piece.getX(),piece.getY(),piece.getMovablePositions().get(0).getDirection());
					return nextMove;
				}
			}
			
		}
		
		return null;
		
	}


	

}
