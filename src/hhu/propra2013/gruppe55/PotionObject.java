package hhu.propra2013.gruppe55;

public class PotionObject extends DungeonObject {

	 private String imgPath	=	"img/potion.png";  			// Potion
	 private String usePath	=	"img/potionused.png";	// Benutzte Potion
	
	public PotionObject(int x, int y) {
		super(x,y);
		// set state
		state	=	new State[2];
		state[0]	=	new State(imgPath,false, false, true);	// Die Potion
		state[1]	=	new State(usePath, false, false, true);	// Verschwundene Potion
		state[1].defineOffset(0,0,32,32);
		
	}
	
	public void onCollision(DungeonObject d){	 // Heilung bei Kollision!
			// heal the player, yay!
	    	if(d instanceof	Player)
				((Player)d).getHealed();
	    	// set to used
	    	switchState(1);
	} 

}
