package hhu.propra2013.gruppe55;

public class GoalObject extends DungeonObject {

	 private String imgPath	=	"img/goal.png";  // Goal-image
		
	public GoalObject(int x, int y) {
		super(x,y);
		state[0].changeImg(imgPath);			  // Aussehen des Ziels
		state[0].massive=false;					  // Ziel wird begehbar
	}
	
	public void onCollision(DungeonObject d){	 // Spieler trifft auf das Ziel
    	if(d instanceof	Player)
			((Player)d).reachgoal();
	}
}
