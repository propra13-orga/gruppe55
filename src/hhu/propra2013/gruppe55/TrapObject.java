package hhu.propra2013.gruppe55;



public class TrapObject extends DungeonObject {

	public TrapObject(int x, int y) {
		super(x,y);
		// States definieren
		state	=	new State[2];
		state[0]	=	new State("trap",false, false, true);		// Die nicht ausgeloeste Falle
		state[0].defineOffset(7,0,0,0);								// Hitbox der nicht ausgeloesten Falle
		state[1]	=	new State("trapact", false, false, true);	// Die ausgeloeste Falle
		
	}
	
	public void onCollision(DungeonObject d){	 // Tod bei Kollision
			// Dem Spieler Schaden zufuegen
	    	if(d instanceof	Player)
				((Player)d).getHit();
	    	// Statewechsel zur ausgeloesten Falle
	    	switchState(1);
	} 
}
