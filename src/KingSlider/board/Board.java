package KingSlider.board;
import java.util.ArrayList;
import aiproj.slider.Move;


/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948)
 * This is the Board class. It contains the current state of the game. 
 *
 */
public class Board {
	
	private int boardSize;
	private int numLegalHMoves;
	private int numLegalVMoves;
	private Piece[][] boardContents;
	private char playertoMove;
	private ArrayList<HPiece> inPlayH;
	private ArrayList<VPiece> inPlayV;
	
	
	public Board(int dimensions, String board_input, char playertoMove) {
		inPlayH = new ArrayList<HPiece>();
		inPlayV = new ArrayList<VPiece>();
		boardSize = dimensions;
		fillBoard(board_input);
		this.playertoMove = playertoMove;

	}
	
	
	
	/**
	 *  This function reads input from the standard inputstream and creates a 2D array representation of the boardstate.
	 *  All game pieces are initialised and added to the board and added to the appropriate arraylist.
	 */
	public void fillBoard(String board_input){
		boardContents = new Piece[boardSize][boardSize];
		char pieceType;
		
		for(int j=boardSize-1; j>=0; j--){
			for(int i=0; i<boardSize; i++){
				// finds next board-cell value
				pieceType = board_input.charAt((boardSize-j-1)*(boardSize*2) + i*2);
				
				// checks what piece occupies the cell
				if (pieceType == 'H'){
					HPiece piece = new HPiece(i,j);
					boardContents[i][j] = piece;
					inPlayH.add(piece);	
				}
				else if (pieceType  == 'V'){
					VPiece piece = new VPiece(i,j);
					boardContents[i][j] = piece;
					inPlayV.add(piece);
				}
				else if (pieceType == 'B'){
					boardContents[i][j]  = new BPiece(i,j);
				}
				else if (pieceType == '+'){
					boardContents[i][j] = null;
				}
			}
		}
	}

	/**
	 *  This function recalculates the number of legal moves each player can make on the current board state.
	 */
	public void updateAllPieces() {
		numLegalHMoves=0;
		numLegalVMoves=0;
		
		for (Piece piece: inPlayH){
			numLegalHMoves += piece.updateLegalMoves(boardContents, boardSize);
		}
		
		for (Piece piece: inPlayV){
			numLegalVMoves += piece.updateLegalMoves(boardContents, boardSize);
		}
	}
	
	/**
	 * Getter for numLegalMoves
	 * @return numLegalHMoves
	 */
	public int getNumLegalHMoves() {
		return numLegalHMoves;
	}

	/**
	 * Getter for numLegalVMoves
	 * @return numLegalVMoves
	 */
	public int getNumLegalVMoves() {
		return numLegalVMoves;
	}
	

	
	/**
	 * Updates the board to reflect a new piece movement
	 * @param x  x-coordinate of the piece
	 * @param y  y-coordinate of the piece
	 * @param d  direction of movement
	 */
	public void movePiece(int x, int y, Move.Direction d){
		//identifies the piece being moved
		Piece currPiece = boardContents[x][y];
		
		// moves the piece inside the 2D array
		if (d==Move.Direction.UP){
			if ((y+1)==boardSize){
				inPlayV.remove(currPiece);
			}
			else{
				currPiece.setY(y+1);
				boardContents[x][y+1] = currPiece;
			}
		}
		else if (d==Move.Direction.DOWN){
			currPiece.setY(y-1);
			boardContents[x][y-1] = currPiece;
		}
		else if (d==Move.Direction.LEFT){
			currPiece.setX(x-1);
			boardContents[x-1][y] = currPiece;
		}
		else if (d==Move.Direction.RIGHT){
			if ((x+1)==boardSize){
				inPlayH.remove(currPiece);
			}
			else{
				currPiece.setX(x+1);
				boardContents[x+1][y] = currPiece;
			}
		}
		boardContents[x][y] = null;
		
		// updates the arraylists
		updateAllPieces();
		
	}
	
	public void printBoard(){
		for(int y=boardSize-1; y>=0; y--){
			for(int x=0; x<boardSize; x++){
				if (boardContents[x][y] instanceof HPiece){
					System.out.print("H ");
				}
				else if (boardContents[x][y] instanceof VPiece){
					System.out.print("V ");
				}
				else if (boardContents[x][y] instanceof BPiece){
					System.out.print("B ");
				}
				else {
					System.out.print("+ ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * Getter for inPlayH
	 * @return an arraylist of in-play H-Pieces
	 */
	public ArrayList<HPiece> getInPlayH() {
		return inPlayH;
	}

	/**
	 * Getter for inPlayV
	 * @return an arraylist of in-play V-Pieces
	 */
	public ArrayList<VPiece> getInPlayV() {
		return inPlayV;
	}
	
	/**
	 * Getter for boardContents
	 * @return a 2D array of Pieces  representing the board's contents
	 */
	public Piece[][] getBoardContents() {
		return boardContents;
	}
	
	
	/**
	 * Getter for playerToMove
	 * @return a char showing which player's turn it is
	 */
	public char getPlayertoMove() {
		return playertoMove;
	}

	/**
	 * Setter for playerToMove
	 * @param playertoMove  a char representing the player being set
	 */
	public void setPlayertoMove(char playertoMove) {
		this.playertoMove = playertoMove;
	}
	
	
	/**
	 * Returns a string representation of the boardContents 2D array
	 * @return a string representing the current board state
	 */
	public String getBoardString(){
		String board_string = "";
		for(int j=boardSize-1; j>=0; j--){
			for(int i=0; i<boardSize; i++){
				if (boardContents[i][j] instanceof HPiece){
					board_string += "H ";
				}
				else if (boardContents[i][j] instanceof VPiece){
					board_string += "V ";
				}
				else if (boardContents[i][j] instanceof BPiece){
					board_string += "B ";
				}
				else{
					board_string += "+ ";
				}
			}
		}
		return board_string;
	}

	/**
	 * A getter for boardSize
	 * @return the size of the board
	 */
	public int getBoardSize() {
		return boardSize;
	}
	
	

}
