package KingSlider1337;
import java.util.ArrayList;

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948)
 * This is the piece class. A piece exists on the Board. 
 */
public abstract class Piece {
	
	private int positionX;
	private int positionY;
	public ArrayList<Point> movableBoardPositions;
	
	
	public Piece(int positionX, int positionY){
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	
	/**
	 * Abstract function that gets implemented by children of this class.
	 * The function updates the movableBoardPositions for the piece that
	 * calls this function.
	 * @param boardContents
	 * @param boardSize
	 * @return number of legal moves that a piece can make.
	 */
	public abstract int updateLegalMoves(Piece[][] boardContents, int boardSize);
		
		
	/**
	 * Getter for X position.
	 * @return int
	 */
	public int getX(){
		return positionX;
	}
		
	/**
	 * Getter for Y position.
	 * @return
	 */
	public int getY(){
		return positionY;
	}
	
	
	/**
	 * Setter for X position.
	 * @param positionX
	 */
	public void setX(int positionX){
		this.positionX = positionX;
	}
	
	/**
	 * Setter for X position.
	 * @param positionY
	 */
	public void sexY(int positionY){
		this.positionY = positionY;
	}
	

}
