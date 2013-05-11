package hhu.propra2013.gruppe55;

public class GoalObject extends DungeonObject {

	 private String imgPath	=	"img/Goal.png";  // trap-image
		
	public GoalObject(int x, int y) {
		super(x,y);
		state[0].changeImg(imgPath);			  // Aussehen der Falle
		state[0].massive=false;					  // Falle wird begehbar
	}
	
	  public void onCollision(DungeonObject d){	 // Tod bei Kollision
			// hurt the player, yay!
	    	if(d instanceof	Player)
				((Player)d).getHit();
	}   

}
