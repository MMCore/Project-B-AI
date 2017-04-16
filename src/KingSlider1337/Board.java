package KingSlider1337;
import java.util.ArrayList;
import java.util.Scanner;

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
	private ArrayList<HPiece> inPlayH;
	private ArrayList<VPiece> inPlayV;
	
	
	public Board(int dimensions, String board_input) {
		inPlayH = new ArrayList<HPiece>();
		inPlayV = new ArrayList<VPiece>();
		boardSize = dimensions;
		fillBoard(board_input);
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
	
	public void movePiece(int i, int j, Move.Direction d){
		if (d==Move.Direction.UP){
			if ((j+1)==boardSize){
				if (inPlayH.contains(boardContents[i][j])){
					inPlayH.remove(boardContents[i][j]);
				}
				else if (inPlayV.contains(boardContents[i][j])){
					inPlayV.remove(boardContents[i][j]);
				}
			}
			else{
				boardContents[i][j+1] = boardContents[i][j];
			}
			boardContents[i][j] = null;
		}
		else if (d==Move.Direction.DOWN){
			boardContents[i][j-1] = boardContents[i][j];
			boardContents[i][j] = null;
		}
		else if (d==Move.Direction.LEFT){
			boardContents[i-1][j] = boardContents[i][j];
			boardContents[i][j] = null;
		}
		else if (d==Move.Direction.RIGHT){
			if ((i+1)==boardSize){
				if (inPlayH.contains(boardContents[i][j])){
					inPlayH.remove(boardContents[i][j]);
				}
				else if (inPlayV.contains(boardContents[i][j])){
					inPlayV.remove(boardContents[i][j]);
				}
			}
			else{
				boardContents[i+1][j] = boardContents[i][j];
			}
			boardContents[i][j] = null;
		}
	}



}
