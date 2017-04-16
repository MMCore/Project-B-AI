/* * * * * *
 * Helper class for representing and communicating a move in the game of Slider.
 * 
 * This class is used to represent moves by the opponent player in the update()
 * method of the SliderPlayer interface.
 * It's also used by the SliderPlayer to return a decision for what move to make 
 * next, through the interface's move() method.
 * method is called.
 *
 * created for COMP30024 Artificial Intelligence 2017
 * by Matt Farrugia <matt.farrugia@unimelb.edu.au>
 */
package aiproj.slider;

/**
 * Helper class for representing and communicating a move in the game of Slider.
 */
public class Move {

	/** Enumeration of the possible move directions. Access like: 
	 * Move.Direction.UP, Move.Direction.LEFT, etc. */
	public enum Direction { UP, DOWN, LEFT, RIGHT }

	/** The board position to which this move applies. For detailed information 
	 * about the coordinate system, see the Rules of the Game specification */
	public final int i, j;
	/** The direction in which the piece is to be moved by this move. */
	public final Move.Direction d;

	/** Create a new move for a given position (i, j) in a given direction d */
	public Move(int i, int j, Move.Direction d) {
		this.i = i;
		this.j = j;
		this.d = d;
	}

	@Override
	public String toString() {
		return "("+i+","+j+"): " + d;
	}
}

