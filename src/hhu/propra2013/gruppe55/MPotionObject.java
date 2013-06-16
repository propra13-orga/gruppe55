package hhu.propra2013.gruppe55;

public class MPotionObject extends DungeonObject {
	
	protected int ma	=	1;
	
	public MPotionObject(double x, double y) {
		super(x,y);
		// States der Potion definieren
		state	=	new State[2];
		state[0]	=	new State(Data_Img.mpotion,false, false, true);			// Die Potion
		state[1]	=	new State(Data_Img.mpotion, false, false, false);		// Verschwundene Potion
	}
	
	public void onCollision(DungeonObject d){	 // Aufnahme des Manatrankes bei Kollision
			// der Spieler bekommt Mana zurueck
	    	if(d instanceof	Player){
				((Player)d).fillmana(ma);
				// Wechsel des Status auf "verschwundene" Potion
				switchState(1);
	    		}
	} 

}