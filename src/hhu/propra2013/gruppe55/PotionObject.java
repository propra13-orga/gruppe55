package hhu.propra2013.gruppe55;

public class PotionObject extends DungeonObject {
	
	public PotionObject(int x, int y) {
		super(x,y);
		// set state
		state	=	new State[2];
		state[0]	=	new State("potion",false, false, true);	// Die Potion
		state[1]	=	new State("potionused", false, false, true);	// Verschwundene Potion
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
