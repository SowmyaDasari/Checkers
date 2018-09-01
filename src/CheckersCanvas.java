import acm.graphics.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import sun.audio.*;
import javax.swing.*;
import java.util.Vector;

public class CheckersCanvas extends Canvas implements MouseListener,ActionListener{
	private static final long serialVersionUID = 1L;
	 	//Creating of buttons ,labels,images
	    JButton nGame = new JButton("PLAY");
		GOval turno = new GOval(70,70);
		JButton Menu=new JButton("BACK");
		GImage imageBack = new GImage("bgnd.png");
		GImage Board = new GImage("Board.jpg");
		Vector<GOval> checkrs = new Vector<GOval>();
		Vector<JLabel> kings= new Vector<JLabel>();
		JButton rules = new JButton("RULES");
		JButton quit = new JButton("QUIT");
		JButton exit = new JButton("EXIT");
		JButton playAgain = new JButton("PLAY AGAIN");
		GLabel turn = new GLabel(" TURN",50 ,60);
		JLabel king =new JLabel("K");
		GLabel Winner = new GLabel("");
		Font font1 = new Font("Algerian", Font.BOLD, 50);
//		Font font2 = new Font("Comic Sans MS", Font.BOLD, 50);
		GImage image = new GImage("check.png");
		GImage image2 = new GImage("RULES.jpg");
		JButton back = new JButton("BACK");
		boolean gameInProgress;//variable to keep track of game is in progress or not
		CheckersData board ;
		int currentPlayer;//to check the present player
		int  selectedR,selectedC;//keeps track of the selected row and column by the player
		Checkers cB;
		CheckersMove[] legalMoves;//array which stores the legal moves that a player can move
		public CheckersCanvas(Checkers c){
			board = new CheckersData();//object creation of CheckerBoard
			cB=c;
			cB.add(imageBack,0,0);//adding of some images for background
			cB.add(Board,165,10);
			cB.addMouseListeners(this);
			//to set font
			Winner.setFont(font1);
			turn.setFont(font1);
			turno.setVisible(false);
			
		}
		//method to respond to a button pressed
		public void actionPerformed(ActionEvent e){
			//selection of methods according to button selected
			Object src = e.getSource();
			if(src==nGame)
				newGame();//
			else if(src==rules)
				displayRules();
			else if(src == quit)
				quitGame();
			else if(src == exit)
				exitGame();
			else if(src == back) 
				back();
			else if(src == Menu){
				Menu();
			}
			else if(src==playAgain)
				newGame();
		}
		public void newGame(){
			//to set buttons and images of the first main menu to visibility false
			//and to make visible the play window and its buttons
			turn.setFont(font1);
			imageBack.setVisible(true);
			Board.setVisible(true);
			nGame.setVisible(false);
			quit.setEnabled(true);
			exit.setVisible(false);
			rules.setVisible(false);
			Winner.setLabel("");
			Winner.setVisible(true);
			image.setVisible(false);
			quit.setVisible(true);
			Menu.setEnabled(false);
			playAgain.setEnabled(false);
			gameInProgress=true;
			Menu.setVisible(true);
			playAgain.setVisible(true);
			turn.setVisible(true);
			turn(CheckersData.GREY);
			turno.setVisible(true);
			selectedR = -1;
			board.setUpGame();
			currentPlayer = CheckersData.GREY;
			legalMoves = board.getLegalMoves(CheckersData.GREY);//to call the get legal moves method and store into array
			setUpBoard();//to call the setup board method to make board and coins  visible
		}
		
		//to display rules that is to display image in this case
		//to make main menu buttons invisible
		public void displayRules(){
			nGame.setVisible(false);
			quit.setVisible(false);
			rules.setVisible(false);
			image.setVisible(false);
			image2.setVisible(true);
			back.setVisible(true);
			exit.setVisible(false);
		}
		
		//to make the buttons of the play window invisible
		//and go back to the main menu(window)
		//to make the board also invisible
		public void Menu(){
			for(GOval p : checkrs){
				p.setVisible(false);
			}
			for(JLabel k : kings){
				k.setVisible(false);
			}
			Menu.setVisible(false);
			quit.setVisible(false);
			playAgain.setVisible(false);
			nGame.setVisible(true);
			exit.setVisible(true);
			rules.setVisible(true);
			image.setVisible(true);
			turn.setVisible(false);
			turno.setVisible(false);
			Winner.setVisible(false);
			imageBack.setVisible(false);
			Board.setVisible(false);
		}
		
		//to quit the game 
		//menu button is enabled and the winner is declared
		public void quitGame(){
			Menu.setEnabled(true);
			quit.setEnabled(false);
			if(currentPlayer==CheckersData.GREY){
				gameOver(CheckersData.YELLOW);
			}
			else gameOver(CheckersData.GREY);
		}
		
		//to exit out of the game completely
		public void exitGame(){
			System.exit(0);//system command
		}
		
		//to print which players turn to play
		//in this we created a coin which represents which player should play
		public void turn(int i){
			if(i==CheckersData.GREY){
				turno.setFilled(true);
				turno.setFillColor(new Color(96,96,96));
			}
			else{
				turno.setFilled(true);
				turno.setFillColor(new Color(255,255,153));
	
		}
		}
		
		//this button is used in rules to go back to main menu after reading the rules
		//mainly we are making the rules page invisible and displaying the main window
		public void back(){
			back.setVisible(false);
			nGame.setVisible(true);
			exit.setVisible(true);
			rules.setVisible(true);
			image.setVisible(true);
			image2.setVisible(false);
		}

		//we declare the winner here and game in Progress variable is set false
		//here we enable the menu and the  play again buttons
		void gameOver(int i) {
			// The game ends. The parameter, str, is displayed as a message
			// to the user. The states of the buttons are adjusted so playes
			// can start a new game.
			//playAgain.setEnabled(true);
			//label.setVisible(true);
			if(i==CheckersData.GREY)
			Winner.setLabel("GREY WINS\n");
			else Winner.setLabel("YELLOW WINS\n");
			gameInProgress = false;
			playAgain.setEnabled(true);
			quit.setEnabled(false);
			Menu.setEnabled(true);
		}
		
		void selectPiece(int row, int col) {
			// This is called by mousePressed() when a player clicks on the
			// square in the specified row and col. It has already been checked
			// that a game is, in fact, in progress.

			/*
			 * If the player clicked on one of the pieces that the player can move,
			 * mark this row and col as selected and return. (This might change a
			 * previous selection.) Reset the message, in case it was previously
			 * displaying an error message.
			 */

			for (int i = 0; i < legalMoves.length; i++)
				if (legalMoves[i].fRow == row && legalMoves[i].fCol == col) {
					selectedR = row;
					selectedC = col;
					if (currentPlayer == CheckersData.GREY)
						turn(CheckersData.GREY);
					else
						turn(CheckersData.YELLOW);
					//setUpBoard();
					return;
				}

			/*
			 * If no piece has been selected to be moved, the user must first select
			 * a piece. Show an error message and return.
			 */

			/*
			 * If the user clicked on a squre where the selected piece can be
			 * legally moved, then make the move and return.
			 */

			for (int i = 0; i < legalMoves.length; i++)
				if (legalMoves[i].fRow == selectedR
						&& legalMoves[i].fCol == selectedC
						&& legalMoves[i].toRow == row && legalMoves[i].toCol == col) {
					doMakeMove(legalMoves[i]);
					return;
				}

			/*
			 * If we get to this point, there is a piece selected, and the square
			 * where the user just clicked is not one where that piece can be
			 * legally moved. Show an error message.
			 */

		} // end doClickSquare()

		void doMakeMove(CheckersMove move) {
			// Thins is called when the current player has chosen the specified
			// move. Make the move, and then either end or continue the game
			// appropriately.

			board.makeMove(move);

			/*
			 * If the move was a jump, it's possible that the player has another
			 * jump. Check for legal jumps starting from the square that the player
			 * just moved to. If there are any, the player must jump. The same
			 * player continues moving.
			 */

			if (move.isJump()) {
				legalMoves = board.getLegalJumpsFrom(currentPlayer, move.toRow,
						move.toCol);
				if (legalMoves != null) {
					if (currentPlayer == CheckersData.GREY)
						turn(CheckersData.GREY);
					else
						turn(CheckersData.YELLOW);
					selectedR = move.toRow; // Since only one piece can be moved,
												// select it.
					selectedC = move.toCol;
					setUpBoard();
					try{
						play("caughtball.wav");
					}
					catch(Exception e){
						
					}
					return;
				}
			}
			

			/*
			 * The current player's turn is ended, so change to the other player.
			 * Get that player's legal moves. If the player has no legal moves, then
			 * the game ends.
			 */

			if (currentPlayer == CheckersData.GREY) {
				currentPlayer = CheckersData.YELLOW;
				legalMoves = board.getLegalMoves(currentPlayer);
				if (legalMoves == null)
					gameOver(CheckersData.GREY);
				else if (legalMoves[0].isJump())
					turn(CheckersData.YELLOW);
				else
					turn(CheckersData.YELLOW);
			} else {
				currentPlayer = CheckersData.GREY;
				legalMoves = board.getLegalMoves(currentPlayer);
				if (legalMoves == null)
					gameOver(CheckersData.YELLOW);
				else if (legalMoves[0].isJump())
					turn(CheckersData.GREY);
				else
					turn(CheckersData.GREY);
			}

			/*
			 * Set selectedRow = -1 to record that the player has not yet selected a
			 * piece to move.
			 */

			selectedR = -1;

			/*
			 * As a courtesy to the user, if all legal moves use the same piece,
			 * then select that piece automatically so the use won't have to click
			 * on it to select it.
			 */

			if (legalMoves != null) {
				boolean sameStartSquare = true;
				for (int i = 1; i < legalMoves.length; i++)
					if (legalMoves[i].fRow != legalMoves[0].fRow
							|| legalMoves[i].fCol != legalMoves[0].fCol) {
						sameStartSquare = false;
						break;
					}
				if (sameStartSquare) {
					selectedR = legalMoves[0].fRow;
					selectedC = legalMoves[0].fCol;
				}
			}

			/* Make sure the board is redrawn in its new state. */

			setUpBoard();
			try{
			play("caughtball.wav");
			}
			catch(Exception e1)
			{
				System.out.println("Exception");
			}
		} // end doMakeMove();
		public void play(String file) throws Exception{
			InputStream in = new FileInputStream(file);
			AudioStream audioStream = new AudioStream(in);
			AudioPlayer.player.start(audioStream);
		}
	    public void mousePressed(MouseEvent evt) {
			// Respond to a user click on the board. If no game is
			// in progress, show an error message. Otherwise, find
			// the row and column that the user clicked and call
			// doClickSquare() to handle it.
			if (gameInProgress == false){
				
			}
			else {
				int col = (evt.getX()-160)/ 80;
				int row = (evt.getY()-10)/ 80;
				//System.out.println("X - "+col+" Y - "+row);
				if (col >= 0 && col < 8 && row >= 0 && row < 8){
					//System.out.println("Row -  "+row +"Col - "+ col);
					selectPiece(row, col);
				}
			}
		}
		
		//Sets up the position of the checkers each time and update their positions.
		public void setUpBoard(){
				for(GOval v: checkrs){
					v.setVisible(false);
				}
				for(JLabel p : kings){
					p.setVisible(false);
				}
				kings.clear();
				checkrs.clear();
				int i,j;int checker;
					for (i = 0; i < 8; i++) {
						for (j = 0; j < 8; j++){
							//arr[i][j].setColor(Color.BLACK);
							double x = 160+j*80;		//setting the X-location of the checker. 
							double y= 10+i*80;			//setting the Y-location of the checker.
							checker = board.pieceAt(i, j); 	//gets the piece which is present on the given row,col and sets its position
							if(checker!=CheckersData.EMPTY){
								GOval c = new GOval(x,y,60,60);
								JLabel king = new JLabel("K");
								king.setFont(font1);
								if(checker==CheckersData.GREY || checker==CheckersData.GREY_KING){
									c.setFillColor(new Color(96,96,96));
									cB.add(c,x+10,y+10);
								}
								if(checker==CheckersData.GREY_KING){		//If the piece is a king then a label containing "K" is added to the checker.
									king.setBackground(new Color(96,96,96));
									kings.addElement(king);
									cB.add(king,x+22,y+5);
								}
								if(checker==CheckersData.YELLOW || checker==CheckersData.YELLOW_KING){
								c.setFillColor(new Color(255,255,153));
								cB.add(c,x+10,y+10);
								}
								if(checker==CheckersData.YELLOW_KING){
									king.setBackground(new Color(255,255,153));
									kings.addElement(king);
									cB.add(king,x+22,y+5);
								}
								c.setFilled(true);
								checkrs.addElement(c);
							}
						}
					}	
					
		}
		public void mouseReleased(MouseEvent evt) {
		}

		public void mouseClicked(MouseEvent evt) {
		}

		public void mouseEntered(MouseEvent evt) {
		}

		public void mouseExited(MouseEvent evt) {
		}



}




