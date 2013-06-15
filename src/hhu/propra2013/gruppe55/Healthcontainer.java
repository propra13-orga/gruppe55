package hhu.propra2013.gruppe55;

public class Healthcontainer extends DungeonObject {
// Attribute
	
	public Healthcontainer(int x, int y) {
		super(x,y);
		// Bild des Containers laden
		state	=	new State[2];
		state[0]	=	new State(Data_Img.healthcontainer,false, false, true);	
		state[1]	=	new State(Data_Img.healthcontainer, false, false, false);	// der Eingesammelte Container
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