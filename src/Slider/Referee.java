/* * * * * * * * *
 * Slider game driver class 'Referee' along with some internal helper classes
 * Note: you should build your own Board representation classes; you will not
 * have access to the Referee.Board class when we test your project after 
 * submission
 *
 * created for COMP30024 Artificial Intelligence 2017
 * by Matt Farrugia <matt.farrugia@unimelb.edu.au>
 */
package aiproj.slider;

/** 
 * Referee class: Driver for a game of Slider
 * Run this class on the command line using a command like:
 * java aiproj.slider.Referee 6 your.package.PlayerName your.package.PlayerName
 * to play your program PlayerName against itself on a board of size N=6
 * See the specification for more detialed instructions
 */
public class Referee {

	/** Load provided classes, and play a game of Slider */
	public static void main(String[] args) {

		/* * * *
		 * first, read and validate command line options
		 */
		Options options = new Options(args);
		

		/* * * *
		 * then, set up the board and initialise the players
		 */

		// create a new board
		Board board = new Board(options.dimension);
		
		// set up timer and time array for profiling
		CPUTimer timer = new CPUTimer(); // nanosecond CPU usage timer
		long[] times = new long[]{0, 0}; // cumulative time spent by each player
		
		// and initialise the players
		SliderPlayer[] players = new SliderPlayer[2];
		try {
			timer.start();
			players[Player.H] = (SliderPlayer)options.playerH.newInstance();
			players[Player.H].init(options.dimension, board.toString(), 'H');
			times[Player.H] += timer.clock();

			timer.start();
			players[Player.V] = (SliderPlayer)options.playerV.newInstance();
			players[Player.V].init(options.dimension, board.toString(), 'V');
			times[Player.V] += timer.clock();	
		} catch (IllegalAccessException | InstantiationException e) {
			System.err.println("player instantiation error: " + e.getMessage());
			System.exit(1);
		}
		

		/* * * *
		 * now, play the game!
		 */

		int turn = Player.H;
		Move previousMove = null;
		String message = null;
		
		render(board);

		// game loop
		while (!board.finished()) {

			// delay
			sleep(options.delay);

			// calculate and time move
			timer.start();
			players[turn].update(previousMove);
			previousMove = players[turn].move();
			times[turn] += timer.clock();

			// validate and perform move
			try {
				board.move(previousMove, Player.pieces[turn]);
			} catch (IllegalMoveException e) {
				// exit game due to violation, leading to loss for players[turn]
				message = e.getMessage();
				break;
			}

			// other player's turn next
			turn = Player.other(turn);
			
			render(board);
		}
		

		/* * * *
		 * game over! finally, display the results
		 */

		if(board.finished()) {
			System.out.println("winner: " + board.winner());
			System.out.println("times:");
			System.out.println(" horizontal ~"+ times[Player.H]/1000000 +"ms");
			System.out.println(" vertical   ~"+ times[Player.V]/1000000 +"ms");
		} else {
			System.out.println("illegal move: "
				+ (turn==Player.H ? "horizontal" : "vertical"));
			System.out.println(" " + message);
			System.out.println(" (move: " + previousMove + ")");
		}
	}

	/** Helper function for rendering a board */
	private static void render(Board board) {
		System.out.println(board);
	}

	/** Helper function for delaying time miliseconds between turns */
	private static void sleep(int time) {
		if (time > 0) {
			try {
				Thread.sleep(time);
			} catch(InterruptedException e) {
				// if interrupted, not much we can do. sleep time is over
			}
		}
	}

	/** Helper class for storing and validating command-line arguments */
	private static class Options {

		public final int delay;				 // time in ms to delay rendering
		public final int dimension;			 // dimension of board to play on
		public final Class playerH, playerV; // class names of players to play

		public Options(String[] args) {

			// are there enough arguments?
			if (args.length < 3) {
				printUsageInfoAndExit();
			}

			// check if we also have an optional delay
			if (args.length > 3) {
				this.delay = Integer.parseInt(args[3]);
			} else {
				this.delay = 0; // default to zero (which will be ignored)
			}

			// check dimension of board
			this.dimension = Integer.parseInt(args[0]);
			if (! (dimension > 3) ) {
				System.err.println("invalid dimension: should be > 3");
				System.exit(1);
			}

			// attempt to locate classes provided by name
			String playerHClassName = args[1];
			String playerVClassName = args[2];
			Class playerH = null;
			Class playerV = null;
			try {
				playerH = Class.forName(playerHClassName);
				playerV = Class.forName(playerVClassName);
			} catch (ClassNotFoundException e) {
				System.err.println("invalid class name: "+ e.getMessage());
				System.exit(1);
			}
			this.playerH = playerH;
			this.playerV = playerV;
		}

		static void printUsageInfoAndExit() {
			System.err.println("usage: java Referee N playerH playerV [delay]");
			System.err.println("       N - dimension of board to use (N > 3)");
			System.err.println(" playerH - fully qualified name of H player");
			System.err.println(" playerV - fully qualified name of V player");
			System.err.println("   delay - (optional) ms delay between turns");
			System.exit(1);
		}
	}

	/** Collection of game helper functions and constants */
	private static interface Player {
		static final int H = 0, V = 1;
		static final Piece[] pieces = new Piece[]{Piece.HSLIDER, Piece.VSLIDER};
		static int other(int player) { return 1 - player; }
		// 1 - 0 is 1, and 1 - 1 is 0, so 1 - H is V, and 1 - V is H!
	}

	/**
	 * Referee's (simplified) internal representation of the board,
	 * handles validation and rendering
	 */
	private static class Board {
		
		private static java.util.Random rng = new java.util.Random();

		private Piece[][] grid;
		private int hsliders = 0, vsliders = 0, passes = 0;
		private final int n;

		public Board(int n) {
			this.n = n;
			this.grid = new Piece[n][n];

			// fill grid blank
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					grid[i][j] = Piece.BLANK;
				}
			}

			// add H sliders
			for (int j = 1; j < n; j++) {
				grid[0][j] = Piece.HSLIDER;
				hsliders++;
			}

			// add V sliders
			for (int i = 1; i < n; i++) {
				grid[i][0] = Piece.VSLIDER;
				vsliders++;
			}

			// add blocked positions
			int nblocked = rng.nextInt(3);
			if (nblocked == 0) {
				// no blocked positions
			} else {
				// one or two blocked positions:
				int i = 1 + rng.nextInt(n-2);
				int j = 1 + rng.nextInt(n-2);
				if (nblocked == 1) {
					grid[i][j] = Piece.BLOCK;
				} else if (nblocked == 2) {
					if (rng.nextBoolean()) {
						grid[i][i] = Piece.BLOCK;
						grid[j][j] = Piece.BLOCK;
					} else {
						grid[i][j] = Piece.BLOCK;
						grid[j][i] = Piece.BLOCK;
					}
				}
			}
		}

		/** represent a board as text for rendering */
		private static final char[] SYMBOLS = {'+', 'B', 'H', 'V'};
		public String toString(){
			StringBuilder s = new StringBuilder(2 * n * n);
			for (int j = n-1; j >= 0; j--) {
				s.append(SYMBOLS[grid[0][j].ordinal()]);
				for (int i = 1; i < n; i++) {
					s.append(' ');
					s.append(SYMBOLS[grid[i][j].ordinal()]);
				}
				s.append('\n');
			}
			return s.toString();
		}

		/** validate a move and change the board state */
		public void move(Move move, Piece turn) throws IllegalMoveException {
			// detect null move (pass)
			if (move == null) {
				// we better just check that there are really no legal moves
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						if (grid[i][j] == turn && canMove(i, j)) {
							throw new IllegalMoveException(
									"can't pass, moves remain!");
						}
					}
				}

				// if we make it here, there were no legal moves: pass is legal
				passes++;
				return;
			} else {
				// we haven't seen a pass, so reset pass counter
				passes = 0;
			}

			// where's the piece?
			Piece piece = grid[move.i][move.j];

			// is it the right type of piece?
			if (piece != turn) {
				throw new IllegalMoveException("not your piece!");
			}
			if (piece == Piece.BLANK || piece == Piece.BLOCK) {
				throw new IllegalMoveException("no piece here!");
			}

			// is the direction allowed?
			if ((piece == Piece.HSLIDER && move.d == Move.Direction.LEFT)
				|| (piece == Piece.VSLIDER && move.d == Move.Direction.DOWN)) {
				throw new IllegalMoveException("can't move that direction!");
			}
			
			// where's the next space?
			int toi = move.i, toj = move.j;
			switch(move.d){
				case UP:	toj++; break;
				case DOWN:	toj--; break;
				case RIGHT:	toi++; break;
				case LEFT:	toi--; break;
			}

			// are we advancing a piece off the board?
			if (piece == Piece.HSLIDER && toi == n) {
				grid[move.i][move.j] = Piece.BLANK;
				hsliders--;
				return;

			} else if (piece == Piece.VSLIDER && toj == n){
				grid[move.i][move.j] = Piece.BLANK;
				vsliders--;
				return;
			}

			// if not, is the position we are moving to on the board?
			if (toj < 0 || toj >= n || toi < 0 || toi >= n) {
				throw new IllegalMoveException("can't move off the board!");
			}

			// is the position we are moving to already occupied?
			if (grid[toi][toj] != Piece.BLANK) {
				throw new IllegalMoveException("that position is occupied!");
			}

			// no? all good? alright, let's make the move!
			grid[move.i][move.j] = Piece.BLANK;
			grid[toi][toj] = piece;
			return;
		}

		private boolean canMove(int i, int j) {
			if (grid[i][j] == Piece.HSLIDER) {
				// for HSLIDERs, check right, up, and down
				return (i+1 == n) || (grid[i+1][j] == Piece.BLANK)
					|| (j+1 < n && grid[i][j+1] == Piece.BLANK)
					|| (j-1 >= 0 && grid[i][j-1] == Piece.BLANK);
			
			} else if (grid[i][j] == Piece.VSLIDER) {
				// for VSLIDERs, check up, right, and left
				return (j+1 == n) || (grid[i][j+1] == Piece.BLANK)
					|| (i+1 < n && grid[i+1][j] == Piece.BLANK)
					|| (i-1 >= 0 && grid[i-1][j] == Piece.BLANK);
			
			} else {
				// any other square can't be moved
				return false;
			}
		}

		public boolean finished() {
			return (hsliders == 0) || (vsliders == 0) || (passes > 1);
		}

		public String winner() {
			if (hsliders == 0) {
				return "horizontal!";
			} else if (vsliders == 0) {
				return "vertical!";
			} else if (passes > 1) {
				return "nobody! (tie)";
			} else {
				return "everybody!";
			}
		}
	}

	/** Enumeration of all of the possible states of a board position */
	private static enum Piece { BLANK, BLOCK, HSLIDER, VSLIDER, }

	/** Simple exception describing a move that fails validation */
	private static class IllegalMoveException extends Exception {
		public IllegalMoveException(String message) {
			super(message);
		}
	}
}

/** Small helper class for nanosecond CPU usage timing */
class CPUTimer {
	long start = 0; // nanosecond time we started timing
	java.lang.management.ThreadMXBean thread =
		java.lang.management.ManagementFactory.getThreadMXBean();
	
	public CPUTimer() {
		thread.setThreadCpuTimeEnabled(true);
	}

	/** Restart the clock to start timing again from now */
	public void start() {
		start = thread.getCurrentThreadCpuTime();
	}

	/**
	 * Return time since started (clock() can be called multiple times per 
	 * start(), but start() should be called at least once before clock())
	 */
	public long clock() {
		return thread.getCurrentThreadCpuTime() - start;
	}
}
