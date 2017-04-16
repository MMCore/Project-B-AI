package Slider;

/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948)
 * This is the Point class. It stores the x and y 
 * co-ordinates of some point.
 */
public class Point {
	
	private int X;
	private int Y;
	
	Point(int X,int Y){
		this.X = X;
		this.Y = Y;
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
	

}
