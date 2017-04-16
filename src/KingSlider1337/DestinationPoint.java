package KingSlider1337;

import aiproj.slider.Move;

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948)
 * This is the Point class. It stores the x and y 
 * co-ordinates of some point.
 */
public class DestinationPoint {
	
	private boolean offBoard;
	private Move.Direction direction;
	private int X;
	private int Y;
	
	DestinationPoint(int X,int Y,Move.Direction direction, boolean offBoard){
		this.X = X;
		this.Y = Y;
		this.direction = direction;
		this.offBoard = offBoard;
	}
	
	/**
	 * Getter for X position.
	 * @return int
	 */
	public int getX(){
		return X;
	}
	
	/**
	 * Getter for Y position.
	 * @return int
	 */
	public int getY(){
		return Y;
	}
	
	public Move.Direction getDirection() {
		return direction;
	}
	
	
	public boolean isOffBoard() {
		return offBoard;
	}
	

}