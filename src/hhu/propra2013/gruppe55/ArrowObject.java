package hhu.propra2013.gruppe55;

public class ArrowObject extends DungeonObject {
	

	
	public ArrowObject(double x, double y) {
		super(x,y);
		// States des Schatzes definieren
		state	=	new State[2];
		state[0]	=	new State(Data_Img.lootarrow,false, false, true);			// Aufsammelbarer Pfeil
		state[1]	=	new State(Data_Img.lootarrow, false, false, false);			// Aufgesammelter Pfeil
	}
	
	public void onCollision(DungeonObject d){	 // Pfeilanzahl erhoehen bei Kollision
		// der Spieler erhaelt Gold
    	if(d instanceof	Player)
			((Player)d).giveStatInventoryObject(4, +5);
    	
    	// Wechsel auf Status "eingesammelter Pfeil"
    	switchState(1);
	}
}