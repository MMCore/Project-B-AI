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
	

	public void movePiece(int i, int j, Move.Direction d){
		Piece currPiece = boardContents[i][j];
		if (d==Move.Direction.UP){
			if ((j+1)==boardSize){
				if (inPlayH.contains(currPiece)){
					inPlayH.remove(currPiece);
				}
				else if (inPlayV.contains(currPiece)){
					inPlayV.remove(currPiece);
				}
			}
			else{
				currPiece.setY(j+1);
				boardContents[i][j+1] = currPiece;
			}
		}
		else if (d==Move.Direction.DOWN){
			currPiece.setY(j-1);
			boardContents[i][j-1] = currPiece;
		}
		else if (d==Move.Direction.LEFT){
			currPiece.setX(i-1);
			boardContents[i-1][j] = currPiece;
		}
		else if (d==Move.Direction.RIGHT){
			if ((i+1)==boardSize){
				if (inPlayH.contains(currPiece)){
					inPlayH.remove(currPiece);
				}
				else if (inPlayV.contains(currPiece)){
					inPlayV.remove(currPiece);
				}
			}
			else{
				currPiece.setX(i+1);
				boardContents[i+1][j] = currPiece;
			}
		}
		boardContents[i][j] = null;
		updateAllPieces();
		//printBoard();

		
		
	}
	
	public void printBoard(){
		System.out.println("printing our board below:");
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
		System.out.println("============================================================");
	}
	
	public ArrayList<HPiece> getInPlayH() {
		return inPlayH;
	}

	public ArrayList<VPiece> getInPlayV() {
		return inPlayV;
	}
	
	
	public ArrayList<BPiece> getBPieces() {
		return BPieces;
	}


	
	public Piece[][] getBoardContents() {
		return boardContents;
	}
	
	public char getPlayertoMove() {
		return playertoMove;
	}

	public void setPlayertoMove(char playertoMove) {
		this.playertoMove = playertoMove;
	}
	
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

	public int getBoardSize() {
		return boardSize;
	}
	
	

}
