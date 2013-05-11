package hhu.propra2013.gruppe55;



public class TrapObject extends DungeonObject {

	 private String imgPath	=	"img/Falle.png";  // trap-image
	
	public TrapObject(int x, int y) {
		super(x,y);
		state[0].changeImg(imgPath);
		state[0].massive=false;
	}
	
	  public void onCollision(DungeonObject d){
			// hurt the player, yay!
	    	if(d instanceof	Player)
				((Player)d).getHit();
	}   	
}
