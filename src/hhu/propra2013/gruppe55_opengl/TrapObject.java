package hhu.propra2013.gruppe55_opengl;

public class TrapObject extends DungeonObject {
// Attribute
	protected int trapDmg	=	1;	// Schaden, den diese Falle ausl�st

	public TrapObject(double x, double y) {
		super(x,y);
		// States definieren
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.trap,false, false, true);		// Die nicht ausgeloeste Falle
		state[0].defineOffset(7,0,0,0,0);								// Hitbox der nicht ausgeloesten Falle
		state[1]	=	new State(Data_Textures.trapact, false, false, true);	// Die ausgeloeste Falle
		
	}
	
	public void onCollision(DungeonObject d){	 // Tod bei Kollision
			// Dem Spieler oder Monstern Schaden zufuegen
	    	if(d instanceof	LivingObject)
				((LivingObject)d).getHit(trapDmg,element);
	    	// Statewechsel zur ausgeloesten Falle
	    	switchState(1);
	} 
}
