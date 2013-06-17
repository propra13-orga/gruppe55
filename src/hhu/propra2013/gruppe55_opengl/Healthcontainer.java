package hhu.propra2013.gruppe55_opengl;

public class Healthcontainer extends DungeonObject {
// Attribute
	
	public Healthcontainer(double x, double y) {
		super(x,y);
		// Bild des Containers laden
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.healthcontainer,false, false, true);	
		state[1]	=	new State(Data_Textures.healthcontainer, false, false, false);	// der Eingesammelte Container

	}
	
	public void onCollision(DungeonObject d){	 // Mehr MaxHp bei Kollision!
			// der Spieler erhaelt mehr MaxHp
	    	if(d instanceof	Player){
				((Player)d).raisehp();
				// Wechsel des Status auf "verschwundener" container
				switchState(1);
	    	}
	} 

}