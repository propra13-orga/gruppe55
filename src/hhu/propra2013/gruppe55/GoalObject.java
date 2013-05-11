package hhu.propra2013.gruppe55;

public class GoalObject extends DungeonObject {

	 private String imgPath	=	"img/Goal.png";  // Goal-image
		
	public GoalObject(int x, int y) {
		super(x,y);
		state[0].changeImg(imgPath);			  // Aussehen des Ziels
		state[0].massive=false;					  // Ziel wird begehbar
	}
	
}