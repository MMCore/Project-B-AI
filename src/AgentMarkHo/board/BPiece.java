package AgentMarkHo.board;
import java.util.ArrayList;

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948)
 * This is the BPiece class. This piece is a subclass of the
 * piece class. The B piece is essentially a block that
 * cannot be moved or move onto.
 *
 */
public class BPiece extends Piece{

	public BPiece(int positionX, int positionY) {
		super(positionX, positionY);
	}

	@Override
	public int updateLegalMoves(Piece[][] boardContents, int boardSize) {
		ArrayList<DestinationPoint> movableBoardPositions = new ArrayList<DestinationPoint>();
		this.movablePositions = movableBoardPositions;
		return 0; 
	}

}
