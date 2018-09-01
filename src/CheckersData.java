import java.util.Vector;

public class CheckersData {
	public static final int EMPTY = 0, GREY = 1, GREY_KING = 2, YELLOW = 3,
			YELLOW_KING = 4;

	private int[][] board; // board[r][c] stores the content of the row r,col c

	public CheckersData() {
		// Constructor to create the board and set it up for a new game.
		board = new int[8][8];
		setUpGame();
	}
	public void setUpGame() {
		//Sets up the board with checkers in their initial positions.
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (row % 2 == col % 2) {
					if (row < 3)
						board[row][col] = YELLOW;
					else if (row > 4)
						board[row][col] = GREY;
					else
						board[row][col] = EMPTY;
				} else {
					board[row][col] = EMPTY;
				}
			}
		}
	} 

	public int pieceAt(int row, int col) {
		// Return the contents of the square in the specified row and column.
		return board[row][col];
	}

	public void setPieceAt(int row, int col, int piece) {
		//sets the specified piece at the specified location
		board[row][col] = piece;
	}

	public void makeMove(CheckersMove move) {
		makeMove(move.fRow, move.fCol, move.toRow, move.toCol);
	}

	/*Moves the checker from (fromRow,fromCol) to (toRow,toCol).If it is a jump,then the checker piece of other player which is present in middle is removed from the board.
	 If the checker reaches the other end,then it is made king
	*/
	public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
		board[toRow][toCol] = board[fromRow][fromCol];
		board[fromRow][fromCol] = EMPTY;
		if (fromRow - toRow == 2 || fromRow - toRow == -2) {
			// The move is a jump. Remove the jumped piece from the board.
			int jumpRow = (fromRow + toRow) / 2; // Row of the jumped piece.
			int jumpCol = (fromCol + toCol) / 2; // Column of the jumped piece.
			board[jumpRow][jumpCol] = EMPTY;
		}
		if (toRow == 0 && board[toRow][toCol] == GREY)
			board[toRow][toCol] = GREY_KING;
		if (toRow == 7 && board[toRow][toCol] == YELLOW)
			board[toRow][toCol] = YELLOW_KING;
	}

	/*Returns an array of all legal moves for the current player.If there are no legal moves then null is returned.If the player have moves which are jumps,then only jumps are
	  returned because jump should be compulsorily done if a jump is available.
	 */
	public CheckersMove[] getLegalMoves(int player) {
		if (player != GREY && player != YELLOW)
			return null;

		int playerKing; // The constant representing a King belonging to player.
		if (player == GREY)
			playerKing = GREY_KING;
		else
			playerKing = YELLOW_KING;

		Vector<CheckersMove> moves = new Vector<CheckersMove>(); // Moves will be stored in this vector.

		//Checking for legal moves and if found then an array contaning these jumps is returned.

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == player || board[row][col] == playerKing) {
					if (canJump(player, row, col, row + 1, col + 1, row + 2,
							col + 2))
						moves.addElement(new CheckersMove(row, col, row + 2,
								col + 2));
					if (canJump(player, row, col, row - 1, col + 1, row - 2,
							col + 2))
						moves.addElement(new CheckersMove(row, col, row - 2,
								col + 2));
					if (canJump(player, row, col, row + 1, col - 1, row + 2,
							col - 2))
						moves.addElement(new CheckersMove(row, col, row + 2,
								col - 2));
					if (canJump(player, row, col, row - 1, col - 1, row - 2,
							col - 2))
						moves.addElement(new CheckersMove(row, col, row - 2,
								col - 2));
				}
			}
		}

		//If no jumps are found,then other legal moves are stored in the array and returned.

		if (moves.size() == 0) {
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					if (board[row][col] == player
							|| board[row][col] == playerKing) {
						if (canMove(player, row, col, row + 1, col + 1))
							moves.addElement(new CheckersMove(row, col,
									row + 1, col + 1));
						if (canMove(player, row, col, row - 1, col + 1))
							moves.addElement(new CheckersMove(row, col,
									row - 1, col + 1));
						if (canMove(player, row, col, row + 1, col - 1))
							moves.addElement(new CheckersMove(row, col,
									row + 1, col - 1));
						if (canMove(player, row, col, row - 1, col - 1))
							moves.addElement(new CheckersMove(row, col,
									row - 1, col - 1));
					}
				}
			}
		}

		
		if (moves.size() == 0)
			return null;
		else {
			CheckersMove[] moveArray = new CheckersMove[moves.size()];
			for (int i = 0; i < moves.size(); i++)
				moveArray[i] = (CheckersMove) moves.elementAt(i);
			return moveArray;
		}

	} 

	//Returns an array of list of legal moves for a specified checker of a specified player.If no legal moves are found,then returns null
	
	public CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {
		if (player != GREY && player != YELLOW)
			return null;
		int playerKing; // The constant representing a King belonging to player.
		if (player == GREY)
			playerKing = GREY_KING;
		else
			playerKing = YELLOW_KING;
		Vector<CheckersMove> moves = new Vector<CheckersMove>(); // The legal jumps will be stored in this
										// vector.
		if (board[row][col] == player || board[row][col] == playerKing) {
			if (canJump(player, row, col, row + 1, col + 1, row + 2, col + 2))
				moves.addElement(new CheckersMove(row, col, row + 2, col + 2));
			if (canJump(player, row, col, row - 1, col + 1, row - 2, col + 2))
				moves.addElement(new CheckersMove(row, col, row - 2, col + 2));
			if (canJump(player, row, col, row + 1, col - 1, row + 2, col - 2))
				moves.addElement(new CheckersMove(row, col, row + 2, col - 2));
			if (canJump(player, row, col, row - 1, col - 1, row - 2, col - 2))
				moves.addElement(new CheckersMove(row, col, row - 2, col - 2));
		}
		if (moves.size() == 0)
			return null;
		else {
			CheckersMove[] moveArray = new CheckersMove[moves.size()];
			for (int i = 0; i < moves.size(); i++)
				moveArray[i] = (CheckersMove) moves.elementAt(i);
			return moveArray;
		}
	}

	//It returns whether a player's checker can jump from (r1,c1) to (r3,c3) having (r2,c2) in the between.
	private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3,
			int c3) {
		if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
			return false; // (r3,c3) is off the board.

		if (board[r3][c3] != EMPTY)
			return false; // (r3,c3) already contains a piece.

		if (player == GREY) {
			if (board[r1][c1] == GREY && r3 > r1)
				return false; // Regular red piece can only move up.
			if (board[r2][c2] != YELLOW && board[r2][c2] != YELLOW_KING)
				return false; // There is no black piece to jump.
			return true; // The jump is legal.
		} else {
			if (board[r1][c1] == YELLOW && r3 < r1)
				return false; // Regular black piece can only move down.
			if (board[r2][c2] != GREY && board[r2][c2] != GREY_KING)
				return false; // There is no red piece to jump.
			return true; // The jump is legal.
		}

	}

	//It returns whether the specified player's checker can move from (r1,c1) to (r2,c2)
	private boolean canMove(int player, int r1, int c1, int r2, int c2) {
		if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
			return false; // (r2,c2) is off the board.

		if (board[r2][c2] != EMPTY)
			return false; // (r2,c2) already contains a piece.

		if (player == GREY) {
			if (board[r1][c1] == GREY && r2 > r1)
				return false; // Regular grey piece can only move down.
			return true; // The move is legal.
		} else {
			if (board[r1][c1] == YELLOW && r2 < r1)
				return false; // Regular yellow piece can only move up.
			return true; // The move is legal.
		}

	} 



}
