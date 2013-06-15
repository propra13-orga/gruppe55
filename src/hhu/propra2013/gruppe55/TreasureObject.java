package hhu.propra2013.gruppe55;

public class TreasureObject extends DungeonObject {
	

	
	public TreasureObject(int x, int y) {
		super(x,y);
		// States des Schatzes definieren
		state	=	new State[2];
		state[0]	=	new State(Data_Img.treasure,false, false, true);			// Der Schatz (Derzeit ein Fillerbild - generic gem)
		state[1]	=	new State(Data_Img.treasure, false, false, false);			// Aufgesammelter Schatz
		
	}
	
	public void onCollision(DungeonObject d){	 // Goldstand erhoehen bei Kollision
		// der Spieler erhaelt Gold
    	if(d instanceof	Player)
			((Player)d).giveStatInventoryObject(1, 10);
    	
    	// Wechsel des Status auf eingesammeltes Gold
    	switchState(1);
	}
}