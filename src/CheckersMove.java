import java.lang.Math;
public class CheckersMove {
		int fRow,fCol,toRow,toCol;
		//Constructor of CheckersMove class which initializes the values of the present location and the location where the checker has to move.   
		public CheckersMove(int Prow,int Pcol,int Drow,int Dcol){
			this.fRow=Prow;
			this.fCol=Pcol;
			this.toRow=Drow;
			this.toCol=Dcol;
		}
		public boolean isJump(){
			return(Math.abs(fRow-toRow)==2);
		}
	/*public static void main(String arg[]){
			CheckersMove Cm = new CheckersMove(4,2,1,4);
			System.out.println(Cm.isJump());
	}*/

}
