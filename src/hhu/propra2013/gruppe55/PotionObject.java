package hhu.propra2013.gruppe55;

public class PotionObject extends DungeonObject {
// Attribute
	protected int heal	=	2;	// zu heilender Wert
	
	public PotionObject(int x, int y) {
		super(x,y);
		// States der Potion definieren
		state	=	new State[2];
		state[0]	=	new State(Data_Img.potion,false, false, true);			// Die Potion
		state[1]	=	new State(Data_Img.potionused, false, false, false);	// Verschwundene Potion
		state[1].defineOffset(0,0,32,32,0);
	}
	
	public void onCollision(DungeonObject d){	 // Heilung bei Kollision!
			// der Spieler wird geheilt
	    	if(d instanceof	Player){
				((Player)d).getHealed(heal);
				// Wechsel des Status auf "verschwundene" Potion
				switchState(1);
	    	}
	} 

}
