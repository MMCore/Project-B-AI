package AgentMarkHo.board;
import java.util.ArrayList;
import aiproj.slider.Move;


/**
 * @author Nguyen Ho (760259) and Marko Mihic (762948)
 * This is the Board class. It contains the current state of the game. 
 *
 */

public class Board {
	
	private int boardSize;
	private Piece[][] boardContents;
	private char playertoMove;
	private ArrayList<HPiece> inPlayH;
	private ArrayList<VPiece> inPlayV;
	private ArrayList<BPiece> BPieces;
	



	public Board(int dimensions, String board_input,char playertoMove) {
		inPlayH = new ArrayList<HPiece>();
		inPlayV = new ArrayList<VPiece>();
		BPieces = new ArrayList<BPiece>();
		boardSize = dimensions;
		fillBoard(board_input);
		this.playertoMove = playertoMove;

	}
	

	
	
	
	/**
	 *  This function reads input from the standard inputstream and creates a 2D array representation of the boardstate.
	 *  All game pieces are initialised and added to the board. If the piece is a player piece it is also added to the appropriate arraylist.
	 */
	public void fillBoard(String board_input){
		boardContents = new Piece[boardSize][boardSize];
		char pieceType;
		for(int j=boardSize-1; j>=0; j--){
			for(int i=0; i<boardSize; i++){
				pieceType = board_input.charAt((boardSize-j-1)*(boardSize*2) + i*2); //TO-DO clean up magic numbers
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
					BPiece piece = new BPiece(i,j);
					boardContents[i][j]  = new BPiece(i,j);
					BPieces.add(piece);
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
		for (Piece piece: inPlayH){
			piece.updateLegalMoves(boardContents, boardSize);
		}
		for (Piece piece: inPlayV){
			piece.updateLegalMoves(boardContents, boardSize);
		}
	}

	/**
	 * Moves a piece in the gameContents array and updates the in-play arraylists.
	 * 
	 * @param x  x-coordinate of piece being moved
	 * @param y  y-coordinate of piece being moved
	 * @param d  direction of movement
	 */
	public void movePiece(int x, int y, Move.Direction d){
		Piece currPiece = boardContents[x][y];
		if (d==Move.Direction.UP){
			if ((y+1)==boardSize){
				if (inPlayH.contains(currPiece)){
					inPlayH.remove(currPiece);
				}
				else if (inPlayV.contains(currPiece)){
					inPlayV.remove(currPiece);
				}
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
				if (inPlayH.contains(currPiece)){
					inPlayH.remove(currPiece);
				}
				else if (inPlayV.contains(currPiece)){
					inPlayV.remove(currPiece);
				}
			}
			else{
				currPiece.setX(x+1);
				boardContents[x+1][y] = currPiece;
			}
		}
		boardContents[x][y] = null;
		updateAllPieces();

		
		
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
	
	public ArrayList<BPiece> getBPieces() {
		return BPieces;
	}


	/**
	 * Getter for boardContents
	 * @return 2D array of pieces representing the board state
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
