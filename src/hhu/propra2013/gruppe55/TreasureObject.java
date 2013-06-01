package hhu.propra2013.gruppe55;

public class TreasureObject extends DungeonObject {
	

	
	public TreasureObject(int x, int y) {
		super(x,y);
		// States des Schatzes definieren
		state	=	new State[2];
		state[0]	=	new State(Data.treasure,false, false, true);			// Der Schatz (Derzeit ein Fillerbild - generic gem)
		state[1]	=	new State(Data.treasure, false, false, false);			// Aufgesammelter Schatz
		state[1].defineOffset(0,0,32,32);
		
	}
	
	public void onCollision(DungeonObject d){	 // Goldstand erhoehen bei Kollision
		// der Spieler erhaelt Gold
    	if(d instanceof	Player)
			((Player)d).getmoney();
    	// Wechsel des Status auf eingesammeltes Gold
    	switchState(1);
	}
}