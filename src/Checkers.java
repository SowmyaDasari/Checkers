//import acm.graphics.*;
import java.awt.Color;
import java.awt.Font;
import acm.program.*;
public class Checkers extends GraphicsProgram{
	private static final long serialVersionUID = 1L;
		public static void main(String args[]){
				new Checkers().start();
		}
		//we create an object of CheckerCanvas
		
		CheckersCanvas checkerBoard = new CheckersCanvas(this);
		public void run(){
			//add(checkerBoard);
			initialWindow(checkerBoard);//call to initialWindow method
			//adding of buttons,labels,images,backgrounds,and to set bounds for buttons
			Font fontn=new Font("Algerian",Font.PLAIN,16);
			add(checkerBoard.turno,1055,100);
			checkerBoard.quit.setBounds(0,0,130,35);
			checkerBoard.playAgain.setBounds(0,0,130,35);
			add(checkerBoard.playAgain,1035,380);
			checkerBoard.playAgain.setVisible(false);
			add(checkerBoard.quit,1035,460);
			checkerBoard.quit.setVisible(false);
			add(checkerBoard.image2,0,0);
			add(checkerBoard.back,1200,650);
			add(checkerBoard.image,0,0);
			checkerBoard.back.setVisible(false);
			checkerBoard.image2.setVisible(false);			
			add(checkerBoard.turn,1020,60);
			checkerBoard.turn.setVisible(false);
			checkerBoard.Menu.setBounds(0,0,130,35);
			add(checkerBoard.Menu,1035,300);
			checkerBoard.Menu.setVisible(false);
			add(checkerBoard.Winner,950,250);
			checkerBoard.imageBack.setVisible(false);
			checkerBoard.Board.setVisible(false);
			checkerBoard.playAgain.setFont(fontn);
			checkerBoard.quit.setFont(fontn);
			checkerBoard.Menu.setFont(fontn);
			addMouseListeners(checkerBoard);//adding mouse and action listeners
			addActionListeners(checkerBoard);
			
		}
		

public void initialWindow(CheckersCanvas can){
	//to set all buttons in main image
	can.nGame.setBounds(0, 0,100,30);
	can.rules.setBounds(0, 0, 100, 30);
	can.exit.setBounds(0,0,100,30);
	can.nGame.setBackground(new Color(255, 255, 204));
	can.rules.setBackground(new Color(255, 255, 204));
	can.exit.setBackground(new Color(255, 255, 204));
	//setting the buttons transparent
	can.nGame.setOpaque(false);
	can.rules.setOpaque(false);
	can.exit.setOpaque(false);
	Font fontn=new Font("Algerian",Font.PLAIN,16);
	can.nGame.setFont(fontn);
	can.rules.setFont(fontn);
	can.exit.setFont(fontn);
	add(can.nGame,420,305);
	add(can.rules,705,305);
	add(can.exit,560,395);
	}
	
}