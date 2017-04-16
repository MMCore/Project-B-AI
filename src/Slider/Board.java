package Slider;
import java.util.ArrayList;
import java.util.Scanner;


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
	private ArrayList<Piece> inPlayH;
	private ArrayList<Piece> inPlayV;
	
	
	public Board() {
		inPlayH = new ArrayList<Piece>();
		inPlayV = new ArrayList<Piece>();
		fillBoard();
	}
	
	/**
	 *  This function reads input from the standard inputstream and creates a 2D array representation of the boardstate.
	 *  All game pieces are initialised and added to the board. If the piece is a player piece it is also added to the appropriate arraylist.
	 */
	public void fillBoard(){
		Scanner input = new Scanner(System.in);
		boardSize = input.nextInt();
		boardContents = new Piece[boardSize][boardSize];
		String pieceType;
		for(int j=boardSize-1; j>=0; j--){
			for(int i=0; i<boardSize; i++){
				pieceType = input.next();
				if (pieceType.equals("H")){
					Piece piece = new HPiece(i,j);
					boardContents[i][j] = piece;
					inPlayH.add(piece);	
				}
				else if (pieceType.equals("V")){
					Piece piece = new VPiece(i,j);
					boardContents[i][j] = piece;
					inPlayV.add(piece);
				}
				else if (pieceType.equals("B")){
					boardContents[i][j]  = new BPiece(i,j);
				}
				else if (pieceType.equals("+")){
					boardContents[i][j] = null;
				}
			}
		}
		input.close();
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



}
