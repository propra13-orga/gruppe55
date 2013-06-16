package hhu.propra2013.gruppe55_opengl;

public class MPotionObject extends DungeonObject {
	
	public MPotionObject(int x, int y) {
		super(x,y);
		// States der Potion definieren
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.mpotion,false, false, true);			// Die Potion
		state[1]	=	new State(Data_Textures.mpotion, false, false, false);		// Verschwundene Potion
		state[1].defineOffset(0,0,32,32, 0);
		
	}
	
	public void onCollision(DungeonObject d){	 // Aufnahme des Manatrankes bei Kollision
			// der Spieler bekommt Mana zurueck
	    	if(d instanceof	Player){
				((Player)d).fillmana();
				// Wechsel des Status auf "verschwundene" Potion
				switchState(1);
	    		}
	} 

}