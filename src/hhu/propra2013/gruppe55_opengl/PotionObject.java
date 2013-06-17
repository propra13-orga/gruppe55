package hhu.propra2013.gruppe55_opengl;

public class PotionObject extends DungeonObject {
// Attribute
	protected int heal	=	2;	// zu heilender Wert
	
	public PotionObject(double x, double y) {
		super(x,y);
		// States der Potion definieren
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.potion,false, false, true);			// Die Potion
		state[1]	=	new State(Data_Textures.potionused, false, false, false);	// Verschwundene Potion
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
