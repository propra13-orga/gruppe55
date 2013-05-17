package hhu.propra2013.gruppe55;



public class TrapObject extends DungeonObject {

	public TrapObject(int x, int y) {
		super(x,y);
		// set state
		state	=	new State[2];
		state[0]	=	new State("trap",false, false, true);	// trap itself
		state[0].defineOffset(7,0,0,0);							// Hitbox der eingefahrenen Falle
		state[1]	=	new State("trapact", false, false, true);	// the activated trap
		
	}
	
	public void onCollision(DungeonObject d){	 // Tod bei Kollision
			// hurt the player, yay!
	    	if(d instanceof	Player)
				((Player)d).getHit();
	    	// set to activated
	    	switchState(1);
	} 
}
