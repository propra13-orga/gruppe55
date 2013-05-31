package hhu.propra2013.gruppe55;

public class PotionObject extends DungeonObject {
	
	public PotionObject(int x, int y) {
		super(x,y);
		// States der Potion definieren
		state	=	new State[2];
		state[0]	=	new State(Data.potionused,false, false, true);			// Die Potion
		state[1]	=	new State(Data.potionused, false, false, true);	// Verschwundene Potion
		state[1].defineOffset(0,0,32,32);
		
	}
	
	public void onCollision(DungeonObject d){	 // Heilung bei Kollision!
			// der Spieler wird geheilt
	    	if(d instanceof	Player)
				((Player)d).getHealed();
	    	// Wechsel des Status auf "verschwundene" Potion
	    	switchState(1);
	} 

}
