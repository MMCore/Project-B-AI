package KingSlider1337;
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
		ArrayList<Point> movableBoardPositions = new ArrayList<Point>();
		this.movableBoardPositions = movableBoardPositions;
		return 0; 
	}

}
